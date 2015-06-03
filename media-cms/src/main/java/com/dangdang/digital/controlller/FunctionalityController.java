package com.dangdang.digital.controlller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dangdang.digital.constant.TreeConstants;
import com.dangdang.digital.model.Functionality;
import com.dangdang.digital.model.RoleFunctionality;
import com.dangdang.digital.model.Usercms;
import com.dangdang.digital.model.UsercmsFunctionality;
import com.dangdang.digital.service.IFunctionalityService;
import com.dangdang.digital.service.IRoleFunctionalityService;
import com.dangdang.digital.service.IUsercmsFunctionalityService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.TreeUtils;
import com.dangdang.digital.utils.UsercmsUtils;
import com.dangdang.digital.view.AjaxResult;
import com.dangdang.digital.view.ZTreeNode;


@Controller
@Scope("prototype")
@RequestMapping("functionality")
public class FunctionalityController extends BaseController {
	
	Logger LOGGER = LoggerFactory.getLogger(FunctionalityController.class);
	
	@Resource 
	IFunctionalityService functionalityService;
	
	@Resource 
	IRoleFunctionalityService roleFunctionalityService;
	
	@Resource 
	IUsercmsFunctionalityService usercmsFunctionalityService;
	
	@RequestMapping("initTree")
	@ResponseBody
	public String initTree(Long roleId){
		
		List<ZTreeNode> ztreeNodesForShow = new ArrayList<ZTreeNode>();
		if(roleId == null){
			List<Functionality> allFuncs = functionalityService.findMasterListByParamsObjs(null);
			ztreeNodesForShow = TreeUtils.convertToZTreeNodeList(allFuncs);
			
		} else {
			
			List<Functionality> roleFuncs = getFunctionalityByRoleId(roleId);
			Map<Long, Functionality> roleFuncMap = new HashMap<Long, Functionality>();
			for(Functionality roleFunc: roleFuncs){
				roleFuncMap.put(roleFunc.getFunctionalityId(), roleFunc);
			}			
			
			List<Functionality> allFuncs = functionalityService.findMasterListByParamsObjs(null);
			ztreeNodesForShow = TreeUtils.convertToZTreeNodeList(allFuncs);
			for(ZTreeNode node: ztreeNodesForShow ){
				if(roleFuncMap.get(node.getId())!=null){
					node.setChecked(true);
				}
			}
		}
		
		return JSON.toJSONString(ztreeNodesForShow);
	}
	
	private List<Functionality> getFunctionalityByRoleId( Long roleId ) {
		
		if(roleId == null){
			return new ArrayList<Functionality>();
		}
		
		List<RoleFunctionality> roleFuncs = roleFunctionalityService.findMasterListByParams("roleId", roleId);
		List<Long> ids = new ArrayList<Long>();
		if( roleFuncs.size() > 0 ) {
			for(int i = 0; i < roleFuncs.size(); i++ ) {
				ids.add( roleFuncs.get(i).getFuncId() );
			}
		}
		
		if(ids.size()==0){
			return new ArrayList<Functionality>();
		}
		return  functionalityService.findByIds(ids);
	}
	
	@RequestMapping("showRoleTree")
	@ResponseBody
	public String showRoleTree(Long roleId){
		
		List<Functionality> funcs = getFunctionalityByRoleId(roleId);
		return JSON.toJSONString(TreeUtils.convertToZTreeNodeList(funcs));
	}
	
	private List<Functionality> getFunctionalityByUsercmsId(Long usercmsId) {
		if(usercmsId == null){
			return new ArrayList<Functionality>();
		}
		
		List<UsercmsFunctionality> userFuncs = usercmsFunctionalityService.findMasterListByParams("usercmsId", usercmsId);
		List<Long> ids = new ArrayList<Long>();
		if( userFuncs.size() > 0 ) {
			for(int i = 0; i < userFuncs.size(); i++ ) {
				ids.add( userFuncs.get(i).getFuncId() );
			}
		}
		return  functionalityService.findByIds(ids);
	}
	
	@RequestMapping("showUsercmsTree")
	@ResponseBody
	public String showUsercmsTree(Long usercmsId){
		
		List<Functionality> funcs = getFunctionalityByUsercmsId(usercmsId);
		return JSON.toJSONString(TreeUtils.convertToZTreeNodeList(funcs));
	}
	
	/** 
	 * 查看对象.
	 **/
	@RequestMapping("show")
	public String show(final Long id) throws Exception {
		Functionality functionality = functionalityService.findMasterById(id);
		
		put("functionality", functionality);
		return "functionality/show";
	}
	
	/** 
	 * 进入添加页面.
	 **/
	@RequestMapping("create")
	public String create(Long parentId) throws Exception {
		
		Functionality functionality = new Functionality();
		
		if(parentId == TreeConstants.TreeRootID ){
			functionality.setParentId(TreeConstants.TreeRootID);
			functionality.setLeaf(true);
			functionality.setLevel(1L);
			functionality.setPath("");
			
		}else{
			
			Functionality parent = functionalityService.findMasterById(parentId);
			functionality.setParentId(parentId);
			functionality.setLeaf(true);
			functionality.setLevel(parent.getLevel()+1);
			functionality.setPath(parent.getPath());
		}
		put("functionality",functionality);
		return "functionality/create";
	}
	
	/** 
	 * 保存对象.
	 **/
	@RequestMapping("save")
	@ResponseBody
	public String save(final Functionality functionality) {
		Usercms currentUser = UsercmsUtils.getCurrentUser();
		try{
			
			if(StringUtils.isEmpty(functionality.getName())){
				AjaxResult result = new AjaxResult();
				result.setResult("failure");
				result.setErrorMessage("权限名称不能为空");
				return JSON.toJSON(result).toString();
			}
			//判断一个parentID下面有没有重名的node，如果有，失败
			List<Functionality> sameNameSiblingList = functionalityService.findMasterListByParams("parentId", functionality.getParentId(), "name", functionality.getName());
			
			if(null != functionality.getFunctionalityId()){
				if(sameNameSiblingList.size()>0){
					
					for(Functionality func:sameNameSiblingList){
						
						if(!func.getFunctionalityId().equals(functionality.getFunctionalityId())){
							AjaxResult result = new AjaxResult();
							result.setResult("failure");
							result.setErrorMessage("同一父节点下存在重复的权限名称");
							return JSON.toJSON(result).toString();
						}
					}
				}
				
				// update 
				functionality.setModifier(currentUser.getLoginName());
				functionality.setLastChangedDate(new Date());
				functionalityService.update(functionality);
				LogUtil.info(LOGGER, "管理员:{0} 编辑了权限  {1}", currentUser.getLoginName(), functionality.getName());
				return JSON.toJSONString(TreeUtils.convertToZTreeNode(functionality));
			}else{
				// save
				if(sameNameSiblingList.size()>0){
					
					AjaxResult result = new AjaxResult();
					result.setResult("failure");
					result.setErrorMessage("同一父节点下存在重复的权限名称");
					return JSON.toJSON(result).toString();
					
				}else{
					functionality.setCreator(currentUser.getLoginName());
					functionality.setCreationDate(new Date());
					functionalityService.save(functionality);
					
					if(functionality.getParentId()!=TreeConstants.TreeRootID){
						//save成功之后一定要update path，以及如果parent原来是Leaf，也需要update parent;
						Functionality parent = functionalityService.get(functionality.getParentId());
						if(parent.getLeaf()){
							parent.setLeaf(false);
							functionalityService.update(parent);
						}
					}
					
					functionality.setPath(functionality.getPath()+"/"+functionality.getFunctionalityId());
					functionalityService.update(functionality);
					LogUtil.info(LOGGER, "管理员:{0} 添加了权限  {1}", currentUser.getLoginName(), functionality.getName());
					return JSON.toJSONString(TreeUtils.convertToZTreeNode(functionality));
				}
				
			}
		}catch(Exception e){
			
			LogUtil.error(LOGGER, e, "管理员:{0}在保存权限出现异常", currentUser.getLoginName());
			return "{\"result\":\"failure\"}";
		}
		
	}
	
	/** 
	 * 保存编辑对象.
	 **/
	@RequestMapping("saveEdit")
	@ResponseBody
	public String saveEdit(final Functionality functionality) {
		return save(functionality);
	}
	
	/**
	 * 进入更新页面.
	 **/
	@RequestMapping("edit")
	public String edit(final Long id) {
		Functionality functionality = functionalityService.get(id);
		put("functionality", functionality);
		return "functionality/edit";
	}
	
	/**
	 * 删除对象.
	 **/
	@RequestMapping("delete")
	@ResponseBody
	public String delete(Functionality functionality) {
		
		//若是非根节点
		if(functionality.getParentId() != TreeConstants.TreeRootID){
			
			List<Functionality> sameNameSiblingList = functionalityService.findMasterListByParams("parentId", functionality.getParentId());
			if(sameNameSiblingList.size()==1 && sameNameSiblingList.get(0).getFunctionalityId().equals(functionality.getFunctionalityId())){
				//父节点只有一个子节点，删除子节点后Update父节点的叶子属性
				Functionality parent = functionalityService.findMasterById(functionality.getParentId());
				parent.setLeaf(true);
				functionalityService.update(parent);
			}
			
		}
		functionalityService.deleteFunctionById(functionality.getFunctionalityId());
		LogUtil.info(LOGGER, "管理员:{0} 删除了权限  {1}", UsercmsUtils.getCurrentUser().getLoginName(), functionality.getName());
		return "{\"result\":\"success\"}";
	}
	
	@RequestMapping("showTree")
	public String showTree() {
		return "functionality/showTree";
	}
}