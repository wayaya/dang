package com.dangdang.digital.controlller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dangdang.digital.constant.DigitalCmsConstants;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.Role;
import com.dangdang.digital.model.RoleFunctionality;
import com.dangdang.digital.model.Usercms;
import com.dangdang.digital.model.UsercmsRole;
import com.dangdang.digital.service.IRoleFunctionalityService;
import com.dangdang.digital.service.IRoleService;
import com.dangdang.digital.service.IUsercmsRoleService;
import com.dangdang.digital.utils.AppUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.utils.UsercmsUtils;
import com.dangdang.digital.view.AjaxResult;

@Controller
@Scope("prototype")
@RequestMapping("role")
public class RoleController extends BaseController{
	
	Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
	
	@Resource
	IRoleService roleService;
	
	@Resource
	IUsercmsRoleService usercmsRoleService;
	
	@Resource
	IRoleFunctionalityService roleFunctionalityService;
	
	/** 
	 * 执行搜索 .
	 **/
	@RequestMapping("list")
	public String list() {
		
		List<Role> list = roleService.findMasterListByParamsObjs(null);
		put("roleList",list);
		return "role/list";
	}
	
	/** 
	 * 查看对象.
	 **/
	@RequestMapping("show")
	public void show(final Long id) throws Exception {
		Role role = roleService.findMasterById(id);
		if(role!=null && role.getRoleId() != null){
			put("role", role);
		}
	}
	
	/** 
	 * 进入添加/编辑页面.
	 **/
	@RequestMapping("create")
	public String create(Role role) throws Exception {

		if(role != null && role.getRoleId()!=null){
			put( "isEdit", true );
			Role roleForEdit = roleService.findMasterById(role.getRoleId());
			put( "roleForEdit", roleForEdit);
			
			List<RoleFunctionality> roleFuncs = roleFunctionalityService.findMasterListByParams("roleId", role.getRoleId());
			String ids = "";
			if( roleFuncs.size() > 0 ) {
				for(int i = 0; i < roleFuncs.size(); i++ ) {
					ids+= roleFuncs.get(i).getFuncId().toString();
					if(i<roleFuncs.size()-1){
						ids+=",";
					}
				}
			}
			put( "ids", ids);
		}
		return "role/create";
	}
	
	@RequestMapping("edit")
	public String edit(Role role) throws Exception {
		return create(role);
	}
	
	@RequestMapping("saveEdit")
	@ResponseBody
	public String saveEdit(Role role) throws Exception {
		return save(role);
	}
	/** 
	 * 保存对象.
	 **/
	@RequestMapping("save")
	@ResponseBody
	public String save(Role role) throws Exception {
		
		try{
			HttpServletRequest request = AppUtil.getRequest();
			Usercms currentUser = UsercmsUtils.getCurrentUser();
			boolean isEdit = role.getRoleId()!=null;
			int sameNameLimit = isEdit?1:0;
			
			if(StringUtils.isEmpty(role.getName())){
				AjaxResult result = new AjaxResult();
				result.setResult("failure");
				result.setErrorMessage("请输入角色名重新提交");
				
				return JSON.toJSON(result).toString();
			}
			
			List<Role> sameNameSiblingList = roleService.findMasterListByParams("name", role.getName());
			
			if( sameNameSiblingList.size() > sameNameLimit ){
				AjaxResult result = new AjaxResult();
				result.setResult("failure");
				result.setErrorMessage("重复的角色名称，请修改角色名重新提交");
				
				return JSON.toJSON(result).toString();
			}
			
			String ids = request.getParameter("ids");
			if(StringUtils.isEmpty(ids)){
				AjaxResult result = new AjaxResult();
				result.setResult("failure");
				result.setErrorMessage("请赋予角色权限后重新提交");
				return JSON.toJSON(result).toString();
			}
			
			if(isEdit){
				role.setModifier(currentUser.getLoginName());
				role.setLastChangedDate(new Date());
				roleService.update(role);
				LogUtil.info(LOGGER, "管理员:{0} 编辑了角色  {1}", UsercmsUtils.getCurrentUser().getLoginName(), role.getName());
			}else{
				role.setCreationDate(new Date());
				role.setCreator(currentUser.getLoginName());
				LogUtil.info(LOGGER, "管理员:{0} 添加了角色  {1}", UsercmsUtils.getCurrentUser().getLoginName(), role.getName());
				roleService.save(role);
			}
			
			String[]functionIds = DigitalCmsConstants.commaSpliter.split(ids);
			
			List<RoleFunctionality> editedRoleFuncList = new ArrayList<RoleFunctionality>(); 
			
			for( String funcId:functionIds ){
				if(StringUtils.isEmpty(funcId)){
					continue;
				}
				RoleFunctionality roleFunctionality = new RoleFunctionality();
				roleFunctionality.setFuncId(Long.parseLong(funcId));
				roleFunctionality.setRoleId(role.getRoleId());
				roleFunctionality.setCreator(currentUser.getLoginName());
				roleFunctionality.setCreationDate(new Date());
				editedRoleFuncList.add(roleFunctionality);
			}
			
			if( isEdit ) {
				
				// 根据已有的functionality列表和编辑后的列表，然后分两步添加和删除 role-functionality
				
				List<RoleFunctionality> oldRoleFuncs = roleFunctionalityService.findMasterListByParams( "roleId", role.getRoleId() );
				List<RoleFunctionality> oldClone = new ArrayList<RoleFunctionality>(oldRoleFuncs);
				oldRoleFuncs.removeAll(editedRoleFuncList);
				List<Long> idsToRemove = new ArrayList<Long>();
				if( oldRoleFuncs.size() > 0 ) {
					for(RoleFunctionality rf : oldRoleFuncs ) {
						idsToRemove.add(rf.getRoleFunctionalityId() );
					}
				}
				editedRoleFuncList.removeAll(oldClone);
				
				// 判断是否有用户在用此role,如果有，那么update那些用户的 user-functionality关系
				List<UsercmsRole> userRoles = usercmsRoleService.findMasterListByParams("roleId", role.getRoleId());
				List<Long> userIds = new ArrayList<Long>();
				for( UsercmsRole ur : userRoles ){
					userIds.add(ur.getUsercmsId());
				}
				
				roleFunctionalityService.addAndRemove(editedRoleFuncList, idsToRemove );
				
				boolean needUpdateUsers = false;
				if(editedRoleFuncList.size()>0 || idsToRemove.size()>0 ){
					needUpdateUsers = true;
				}
				//更新用户功能关联
				if(needUpdateUsers && userIds!=null && userIds.size()>0){
					roleFunctionalityService.updateUserFunctionalities(userIds, currentUser.getLoginName());
				}
				
				
			}else{
				//添加
				roleFunctionalityService.insertList(editedRoleFuncList);
			}
			
			return JSON.toJSONString(role).toString();
		}catch(Exception e){
			return "{\"result\":\"failure\"}";
		}
	}
	
	
	/**
	 * 删除对象.
	 **/
	@RequestMapping("delete")
	@ResponseBody
	public String delete(final String[] ids) {
		AjaxResult result = new AjaxResult();
		List<Long> idsToRemove = new ArrayList<Long>();
		for(String id: ids){
			idsToRemove.add(SafeConvert.convertStringToLong(id, 0L));
		}
		
		try {
			roleService.deleteRoleByIds(idsToRemove);
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_SUCCESS);
		} catch (MediaException e) {
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
			result.setErrorMessage(e.getErrorMsg());
		}
		return JSON.toJSONString(result);
	}
	
	
}
