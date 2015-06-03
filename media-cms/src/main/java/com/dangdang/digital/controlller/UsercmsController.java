package com.dangdang.digital.controlller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dangdang.digital.constant.DigitalCmsConstants;
import com.dangdang.digital.model.Role;
import com.dangdang.digital.model.Usercms;
import com.dangdang.digital.model.UsercmsRole;
import com.dangdang.digital.service.IRoleFunctionalityService;
import com.dangdang.digital.service.IRoleService;
import com.dangdang.digital.service.IUsercmsRoleService;
import com.dangdang.digital.service.IUsercmsService;
import com.dangdang.digital.utils.AppUtil;
import com.dangdang.digital.utils.EmailManager;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.MD5Utils;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.utils.UsercmsUtils;
import com.dangdang.digital.view.AjaxResult;

@Controller
@Scope("prototype")
@RequestMapping("usercms")
public class UsercmsController extends BaseController{
	
	Logger LOGGER = LoggerFactory.getLogger(UsercmsController.class);
	
	@Resource
	IUsercmsService usercmsService;

	@Resource
	IRoleService roleService;
	
	@Resource
	IUsercmsRoleService usercmsRoleService;
	
	@Resource
	IRoleFunctionalityService roleFunctionalityService;
	
	@RequestMapping("list")
	public String list(Model model, String page, Usercms usercms, Long roleId){
		
		model.addAttribute("lefttab", AppUtil.getRequest().getParameter("lefttab"));
		Query query = new Query();
		if(StringUtils.isNotBlank(page)){
			query.setPage(Integer.parseInt(page));
		}else{
			query.setPage(1);
		}
		
		if(StringUtils.isEmpty(usercms.getName())){
			usercms.setName(null);
		}
		
		PageFinder<Usercms> pageFinder = null;
		
		List<Role> allRoles = roleService.findMasterListByParamsObjs(null);
		
		if(roleId!=null && roleId>0){
			
			Role role = getRoleById(allRoles, roleId);
			
			if(role == null ){
				pageFinder = new PageFinder<Usercms>(0, 0);
//				pageFinder = usercmsService.findMasterPageFinderObjs(usercms, query);
			}else{
				
				List<UsercmsRole> usercmsRoleList = usercmsRoleService.findMasterListByParams("roleId", roleId);
				List<Long> userIds = new ArrayList<Long>();
				for(UsercmsRole userRole : usercmsRoleList){
					userIds.add(userRole.getUsercmsId());
				}
				pageFinder = usercmsService.findMasterPageFinderObjs(usercms, query, userIds);
			}
			
		}else{
			pageFinder = usercmsService.findMasterPageFinderObjs(usercms, query);
		}
		
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		
		Map<String, List<UsercmsRole>> userRoleMapping = new HashMap<String, List<UsercmsRole>>();
		if(pageFinder.getData()!=null){
			for(Usercms user : pageFinder.getData()){
				List<UsercmsRole> usercmsRoles = usercmsRoleService.findMasterListByParams("usercmsId", user.getUsercmsId());
				userRoleMapping.put(user.getUsercmsId()+"", usercmsRoles);
			}
		}
		
		if(allRoles == null){
			allRoles = new ArrayList<Role>();
		}
		model.addAttribute("userRoleMapping", userRoleMapping);
		model.addAttribute("usercms", usercms);
		model.addAttribute("allRoles", allRoles);
		model.addAttribute("roleId", roleId);
		return "usercms/list";
	}
	
	private Role getRoleById(List<Role> allRoles, Long roleId) {
		
		for(Role role:allRoles){
			if(role.getRoleId().equals(roleId)){
				return role;
			}
		}
		return null;
	}

	@RequestMapping("create")
	public String create(Usercms usercms, String[] roleIds){
		List<Role> roles = roleService.findMasterListByParamsObjs(null);
		if(roleIds!=null && roleIds.length>0 ){
			List<Long> roleIdsLong = getLongIds(roleIds);
			put("existedRoles", roleIdsLong);
		}
		put("roles",roles);
		put("usercmsForEdit", usercms);
		return "usercms/create";
	}
	
	@RequestMapping("edit")
	public String edit(Usercms user){
		
		Usercms usercms = usercmsService.findMasterById(user.getUsercmsId());
		
		put("usercmsForEdit", usercms);
		
		List<Role> roles = roleService.findMasterListByParamsObjs(null);
		
		List<UsercmsRole> usercmsRoles = usercmsRoleService.findMasterListByParams("usercmsId", user.getUsercmsId());
		List<Long> existedRoles = new ArrayList<Long>();
		for(UsercmsRole usercmsRole: usercmsRoles){
			existedRoles.add(usercmsRole.getRoleId());
		}
		put("existedRoles", existedRoles);
		put("roles",roles);
		return "usercms/create";
	}
	
	@RequestMapping("addSave")
	public String addSave(Model model, Usercms usercms, String[] roleIds) throws Exception{
		
		Usercms currentUser = UsercmsUtils.getCurrentUser();
		// 不允许非管理员创建管理员
		if( currentUser.getPreviledge()==null || currentUser.getPreviledge() == 1 ){
			usercms.setPreviledge((byte)1);
		}
		
		boolean isEdit = (usercms.getUsercmsId() != null && usercms.getUsercmsId() !=0L);
		List<Long> roleIdsLong = getLongIds(roleIds);
		List<Long> userIds = new ArrayList<Long>();
		
		int sameNameLimit = 0;
		
		if(!isEdit){
		
			if(StringUtils.isEmpty(usercms.getLoginName())){
				put("errorMessage", "请填写登录名重新提交");
				return create(usercms, roleIds);
			}
			
			List<Usercms> sameNameSiblingList = usercmsService.findMasterListByParams("loginName", usercms.getLoginName());
			
			if( sameNameSiblingList.size() > sameNameLimit ){
				
				put("errorMessage", "重复的登录名: "+usercms.getLoginName()+" ,请修改登录名重新提交");
				return create(usercms, roleIds);
			}
		}
		
		if(usercms.getPreviledge()!=0 && roleIdsLong.size()==0){
			put("errorMessage", "请分配角色后重新提交");
			if(isEdit){
				return edit(usercms);
			}else{
				return create(usercms, roleIds);
			}
		}
		
		if(!isEdit){
			
			usercms.setPassword(DigitalCmsConstants.DEFAULT_PASSWORD); 
			String[] passwordEncryption = MD5Utils.getInstance().salt32(usercms.getPassword());
			usercms.setPassword(passwordEncryption[0]);
			usercms.setPasswordEncryptionKey(passwordEncryption[1]);
			usercms.setCreationDate(new Date());
			usercms.setCreator(currentUser.getLoginName());
			usercms.setStatus((byte)2);
			usercms.setOnlineStatus((byte)0);
			usercmsService.save(usercms);
			
			userIds.add(usercms.getUsercmsId());
			usercmsService.saveOrUpdateUsercms(usercms, roleIdsLong, currentUser.getLoginName());
			roleFunctionalityService.updateUserFunctionalities(userIds, currentUser.getLoginName());
			
			//自动发邮件
			String email = usercms.getEmail();
			if(StringUtils.isNotEmpty(email)){
				try{
					HttpServletRequest request = AppUtil.getRequest();
					String emailContent = usercms.getName()+" 您好，您的数据中心后台账号已经创建，初始密码为dang@dang，首次登录需要修改密码。"
							+ "地址："+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
					EmailManager.getInstance().sendMail(null, null, "数据中心后台账号已创建", emailContent,
						new String[]{email}, null, null);
				}catch(Exception e){
//					result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
//					result.setErrorMessage("重置密码成功，但邮件发送失败");
//					return JSON.toJSONString(result);
				}
			}
			
			LogUtil.info(LOGGER, "管理员:{0} 添加了用户  {1}", UsercmsUtils.getCurrentUser().getLoginName(), usercms.getLoginName());
			return list(model, "1", new Usercms(), null);
//			return show(model, usercms);
		}else{
			Usercms userCmsEditInDB = usercmsService.findMasterById(usercms.getUsercmsId());
			if( currentUser.getPreviledge() !=0 && userCmsEditInDB.getPreviledge() ==0){
				put("errorMessage","非管理员不允许修改管理员信息");
				return edit(usercms);
			}
			// 不允许update密码，有专门的接口修改密码
			usercms.setPassword(null);
			usercms.setPasswordEncryptionKey(null);
			usercms.setModifier(currentUser.getLoginName());
			usercms.setLastChangedDate(new Date());
			usercmsService.update(usercms);
			usercmsService.saveOrUpdateUsercms(usercms, roleIdsLong, currentUser.getLoginName());
			userIds.add(usercms.getUsercmsId());
			//更新用户功能关联
			roleFunctionalityService.updateUserFunctionalities(userIds, currentUser.getLoginName());
			
			LogUtil.info(LOGGER, "管理员:{0} 编辑了用户  {1}", UsercmsUtils.getCurrentUser().getLoginName(), usercms.getLoginName());
			
			return show(model, usercms);
		}
	}

	private List<Long> getLongIds(String[] roleIds) {
		List<Long> roleIdsLong = new ArrayList<Long>();
		if(roleIds==null){
			return roleIdsLong;
		}
		for(String id: roleIds){
			Long roleId = SafeConvert.convertStringToLong(id, 0L);
			if(roleId != 0L){
				roleIdsLong.add(roleId);
			}
		}
		return roleIdsLong;
	}
	
	@RequestMapping("editSave")
	public String editSave(Model model, Usercms usercms, String[] roleIds) throws Exception {
		
		return addSave(model, usercms, roleIds);
	}
	
	@RequestMapping("resetPage")
	public String resetPage(){
		return "usercms/reset";
	}
	
	@RequestMapping("resetOwnPassword")
	@ResponseBody
	public String resetOwnPassword(Usercms usercms, String newPassword, String fromUserList){
		LogUtil.info(LOGGER, "管理员:{0} 首次登录正在修改密码", UsercmsUtils.getCurrentUser().getLoginName());
		return resetPassword(usercms, newPassword, fromUserList);
	}
	
	@RequestMapping("resetPassword")
	@ResponseBody
	public String resetPassword(Usercms usercms, String newPassword, String fromUserList){
		AjaxResult result = new AjaxResult();
		try{
			Usercms currentUser = UsercmsUtils.getCurrentUser();
			//需求，只有管理员能更改别人的密码; 用户第一次登录必须要修改自己的密码
			if(currentUser == null || !currentUser.getUsercmsId().equals(usercms.getUsercmsId()) && currentUser.getPreviledge()!=0){
				result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
				result.setErrorMessage("权限不够，请联系管理员");
				return JSON.toJSONString(result);
			}
			
			if(usercms.getUsercmsId()==25){
				
				LogUtil.info(LOGGER, "管理员:{0} 正在尝试重置默认管理员的密码", UsercmsUtils.getCurrentUser().getLoginName());
				result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
				result.setErrorMessage("不能重置默认管理员的密码");
				return JSON.toJSONString(result);
			}
			
			//管理员在用户管理界面重置别人或者自己的密码
			if(StringUtils.isEmpty(newPassword) && StringUtils.isNotEmpty(fromUserList)){
				newPassword = DigitalCmsConstants.DEFAULT_PASSWORD;
				
				if(currentUser.getPreviledge()!=0){
					result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
					result.setErrorMessage("权限不够，请联系管理员");
					return JSON.toJSONString(result);
				}
			}
			
			String[] passwordEncryption = MD5Utils.getInstance().salt32(newPassword);
			usercms.setPassword(passwordEncryption[0]);
			usercms.setPasswordEncryptionKey(passwordEncryption[1]);
			usercms.setModifier(currentUser.getLoginName());
			usercms.setLastChangedDate(new Date());
			//普通的首次登录重置密码
			if(currentUser.getUsercmsId() == usercms.getUsercmsId() && currentUser.getStatus()==2){
				usercms.setStatus((byte)1);
			}
			if(currentUser.getUsercmsId() != usercms.getUsercmsId()){
				
				Usercms editingUserInfoInDB = usercmsService.findMasterById(usercms.getUsercmsId());
				if(editingUserInfoInDB.getStatus()==0){
					usercms.setStatus((byte)3);
				}else if(editingUserInfoInDB.getStatus()==1){
					usercms.setStatus((byte)2);
				}
			}
			usercmsService.update(usercms);
			//发邮件
			Usercms updatedUsercms = usercmsService.findMasterById(usercms.getUsercmsId());
			String email = updatedUsercms.getEmail();
			if(StringUtils.isNotEmpty(email)){
				try{
					String emailContent = "";
					if(StringUtils.isEmpty(fromUserList)){
						emailContent = "您好，您的原创后台密码已重置成功";
					}else{
						emailContent = "您好，您的原创后台密码已重置，初始密码为dang@dang，请登录后台修改密码，谢谢";
					}
					
					EmailManager.getInstance().sendMail(null, null, "原创后台密码已重置", updatedUsercms.getName()+emailContent,
						new String[]{email}, null, null);
				}catch(Exception e){
//					result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
//					result.setErrorMessage("重置密码成功，但邮件发送失败");
//					return JSON.toJSONString(result);
				}
			}
			if(StringUtils.isNotEmpty(fromUserList)){
				LogUtil.info(LOGGER, "管理员:{0} 重置了用户  {1}的密码", UsercmsUtils.getCurrentUser().getLoginName(), updatedUsercms.getLoginName());
			}else{
				LogUtil.info(LOGGER, "管理员:{0} 首次登录成功修改了自己的密码", UsercmsUtils.getCurrentUser().getLoginName());
			}
			
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_SUCCESS);
			return JSON.toJSONString(result);
			
		}catch(Exception e){
			if(StringUtils.isNotEmpty(fromUserList)){
				Usercms updatedUsercms = usercmsService.findMasterById(usercms.getUsercmsId());
				LogUtil.error(LOGGER, e, "管理员:{0}在重置{1}的密码时出现异常", UsercmsUtils.getCurrentUser().getLoginName(), updatedUsercms.getLoginName());
			}else{
				LogUtil.error(LOGGER, e, "管理员:{0}在首次登录修改密码时出现异常", UsercmsUtils.getCurrentUser().getLoginName());
			}
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
			result.setErrorMessage("系统错误，请稍后再试");
			return JSON.toJSONString(result);
		}
		
	}
	
	@RequestMapping("forbid")
	@ResponseBody
	public String forbid(Usercms usercms){
		AjaxResult result = new AjaxResult();
		Usercms userFromDB = usercmsService.findMasterById(usercms.getUsercmsId());
		try{
			Usercms currentUser = UsercmsUtils.getCurrentUser();
			if( currentUser.getPreviledge() !=0 && userFromDB.getPreviledge() ==0){
				result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
				result.setErrorMessage("非管理员不允许修改管理员信息");
				return JSON.toJSONString(result);
			}
			
			if(userFromDB.getStatus() == 0){
				usercms.setStatus((byte)1);
				result.setErrorMessage("正常");
			}
			if(userFromDB.getStatus() == 1){
				usercms.setStatus((byte)0);
				result.setErrorMessage("已禁用");
			}
			if(userFromDB.getStatus() == 2){
				usercms.setStatus((byte)3);
				result.setErrorMessage("已禁用（没有修改密码）");
			}
			if(userFromDB.getStatus() == 3){
				usercms.setStatus((byte)2);
				result.setErrorMessage("已启用（没有修改密码）");
			}
			
			usercms.setModifier(currentUser.getModifier());
			usercms.setLastChangedDate(new Date());
			usercmsService.update(usercms);
			
			LogUtil.info(LOGGER, "管理员:{0} 设置了用户  {1}的状态为："+result.getErrorMessage(), UsercmsUtils.getCurrentUser().getLoginName(), userFromDB.getLoginName());
			
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_SUCCESS);
			return JSON.toJSONString(result);
			
		}catch(Exception e){
			LogUtil.error(LOGGER, e, "管理员:{0}在设置{1}的状态时出现异常", UsercmsUtils.getCurrentUser().getLoginName(), userFromDB.getLoginName());
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
			result.setErrorMessage("系统错误，请稍后再试");
			return JSON.toJSONString(result);
		}
	}
	
	
	@RequestMapping("show")
	public String show( Model model, Usercms user ){
		
		model.addAttribute("usercms", usercmsService.findMasterById(user.getUsercmsId()));
		List<UsercmsRole> usercmsRoles = usercmsRoleService.findMasterListByParams("usercmsId", user.getUsercmsId());
		put("existedRoles", usercmsRoles);
		
		return "usercms/show";
	}
	
	/**
	 * 删除对象.
	 **/
	@RequestMapping("delete")
	@ResponseBody
	public String delete(final String[] ids) {
		AjaxResult result = new AjaxResult();
		List<Long> idsToRemove = new ArrayList<Long>();
		StringBuffer sb = new StringBuffer();
		for(String id: ids){
			idsToRemove.add(SafeConvert.convertStringToLong(id, 0L));
			sb.append(id);
			sb.append(",");
		}
		
		Usercms currentUser = UsercmsUtils.getCurrentUser();
		
		//不允许删除管理员,即使当前用户是管理员， 不能删除自己
		List<Usercms> listToRemove = usercmsService.findMasterByIds(idsToRemove);
		
		for(Usercms userInList: listToRemove){
			
			if( userInList.getPreviledge() ==0){
				result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
				result.setErrorMessage("不允许删除管理员");
				return JSON.toJSONString(result);
			}
			
			if( userInList.getUsercmsId().equals(currentUser.getUsercmsId()) ){
				result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
				result.setErrorMessage("不能删除当前正在使用的账号");
				return JSON.toJSONString(result);
			}
			
		}
	
		try {
			usercmsService.deleteUsercmsByIds(idsToRemove);
			LogUtil.info(LOGGER, "管理员:{0} 删除了用户ids {1}："+result.getErrorMessage(), UsercmsUtils.getCurrentUser().getLoginName(), sb.toString());
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_SUCCESS);
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "管理员:{0}删除用户ids {1} 时出现异常", UsercmsUtils.getCurrentUser().getLoginName(), sb.toString());
			result.setResult(DigitalCmsConstants.AJAX_REQUEST_RESULT_FAILURE);
			result.setErrorMessage("内部错误");
		}
		return JSON.toJSONString(result);
	}
	
}
