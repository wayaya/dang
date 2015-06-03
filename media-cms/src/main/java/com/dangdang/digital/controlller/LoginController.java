package com.dangdang.digital.controlller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.DigitalCmsConstants;
import com.dangdang.digital.interceptor.PreviledgeInterceptor;
import com.dangdang.digital.model.Functionality;
import com.dangdang.digital.model.Usercms;
import com.dangdang.digital.model.UsercmsFunctionality;
import com.dangdang.digital.service.IFunctionalityService;
import com.dangdang.digital.service.IUsercmsFunctionalityService;
import com.dangdang.digital.service.IUsercmsService;
import com.dangdang.digital.utils.AppUtil;
import com.dangdang.digital.utils.MD5Utils;
import com.dangdang.digital.utils.UsercmsUtils;
import com.dangdang.digital.view.AjaxResult;
import com.dangdang.digital.view.UsercmsSessionInfo;

@Controller
@Scope("prototype")
@RequestMapping("login")
public class LoginController extends BaseController{
	@Resource
	IUsercmsService usercmsService;
	
	@Resource
	IFunctionalityService functionalityService;
	
	@Resource 
	IUsercmsFunctionalityService usercmsFunctionalityService;
	
	@RequestMapping("index")
	public String login(Usercms usercms) {
		return "login/login";
	}
	
	@Autowired  
	private  HttpServletRequest request; 
	
	@RequestMapping("login")
	public String loginSubmit(Model model, Usercms usercms, String captcha) {
		
		AjaxResult result = new AjaxResult();
		boolean needChangePassword = false;
		Usercms cmsUserInDB = null;
		
		//如果用户已经登录，那么直接到主页面
		Usercms currentUser = UsercmsUtils.getCurrentUser();
		if(currentUser!=null){
			return "main";
		}
		
		
		try{
			if(StringUtils.isEmpty(usercms.getLoginName()) || StringUtils.isEmpty(usercms.getPassword())){
				result.setErrorCode("1000");
				result.setErrorMessage("登陆失败，登录名或密码不能为空");
			}else if(StringUtils.isEmpty(captcha)){
				result.setErrorCode("1001");
				result.setErrorMessage("登陆失败，验证码不能为空");
			}else{
				String captchaInSession = (String)AppUtil.getSession().getAttribute(Constans.XS_SESSIONID);
				
				if(captchaInSession==null || !StringUtils.isEquals((captcha+"_"+AppUtil.getSession().getId()).toLowerCase(), captchaInSession.toLowerCase())){
					result.setErrorCode("1002");
					result.setErrorMessage("登陆失败，验证码不正确");
				}else{
					cmsUserInDB = usercmsService.findMasterUniqueByParams("loginName",usercms.getLoginName());
					if(cmsUserInDB == null){
						result.setErrorCode("1003");
						result.setErrorMessage("登陆失败，该用户不存在");
					}else if(cmsUserInDB.getStatus() ==0 || cmsUserInDB.getStatus()==3 ){
						result.setErrorCode("1004");
						result.setErrorMessage("登录失败，该账户已被禁用");
					}else if(!StringUtils.isEquals(MD5Utils.getInstance().getSalt32WithKey(usercms.getPassword(), cmsUserInDB.getPasswordEncryptionKey()),
											cmsUserInDB.getPassword())){
						result.setErrorCode("1006");
						result.setErrorMessage("登陆失败，密码不正确");
					}else{
						//保护用户密码信息，
						cmsUserInDB.setPassword(null);
						cmsUserInDB.setPasswordEncryptionKey(null);
						
						UsercmsSessionInfo userSessionInfo = new UsercmsSessionInfo();
						userSessionInfo.setUserInfo(cmsUserInDB);
						userSessionInfo.setAdmin(cmsUserInDB.getPreviledge()==0);
						
						List<UsercmsFunctionality> userFuncs = new ArrayList<UsercmsFunctionality>();
						
						List<Functionality> allFuncs = functionalityService.findMasterListByParamsObjs(null);
						
						AppUtil.getSession().setAttribute(DigitalCmsConstants.ALL_FUNCTIONALITIES_SYSTEM, allFuncs);
						
						if(!PreviledgeInterceptor.interceptPreviledgeEnabled || cmsUserInDB.getPreviledge()==0){
							
							//若没有权限控制或者是系统管理员，赋予所有权限
							for(Functionality func: allFuncs){
								UsercmsFunctionality uf = new UsercmsFunctionality();
								uf.setFuncId(func.getFunctionalityId());
								userFuncs.add(uf);
							}
							
						}else{
							//权限信息
							userFuncs = usercmsFunctionalityService.findMasterListByParams("usercmsId",cmsUserInDB.getUsercmsId());
							
							List<Long> ids = new ArrayList<Long>();
							for(UsercmsFunctionality uf: userFuncs){
								ids.add(uf.getFuncId());
							}
							
							List<Functionality> allUserFuncs = functionalityService.findMasterByIds(ids);
							AppUtil.getSession().setAttribute(DigitalCmsConstants.ALL_FUNCTIONALITIES_USER, allUserFuncs);
						}
						
						Map<String, Boolean> userFuncsMap = new HashMap<String, Boolean>();
						for(UsercmsFunctionality userFunc: userFuncs){
							userFuncsMap.put(userFunc.getFuncId()+"",true);
						}
						userSessionInfo.setF(userFuncsMap);
						AppUtil.getSession().setAttribute(DigitalCmsConstants.CMS_USER_INFO_STORED_IN_SESSION, userSessionInfo);
						
						Usercms userCmsForUpdate = new Usercms();
						userCmsForUpdate.setUsercmsId(cmsUserInDB.getUsercmsId());
						userCmsForUpdate.setOnlineStatus((byte)1);
						usercmsService.update(userCmsForUpdate);
						if(cmsUserInDB.getStatus()==2){
							needChangePassword = true;
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setErrorCode("1005");
			result.setErrorMessage("登陆失败，系统内部错误，请联系管理员");
		}
		
		if(StringUtils.isNotEmpty(result.getErrorCode())){
			model.addAttribute("loginError", result);
			return login(usercms);
		}else{
//			result.setExtentionField1(needChangePassword+"");
			if(needChangePassword){
				model.addAttribute("usercmsForEdit", cmsUserInDB);
				return "login/changePassword";
			}
			return "main";
		}
	}
	
	@RequestMapping("securitySession")
	public String securitySession(){
		return "login/securitySession";
	}
	
	@RequestMapping("noPreviledge")
	public String noPreviledge(){
		return "login/noPreviledge";
	}
	
	
	
	@RequestMapping("logout")
	public String logout(){
		
		Usercms currentUser = UsercmsUtils.getCurrentUser();
		if(currentUser!=null){
			Usercms userCmsForUpdate = new Usercms();
			userCmsForUpdate.setUsercmsId(currentUser.getUsercmsId());
			//下线
			userCmsForUpdate.setOnlineStatus((byte)0);
			usercmsService.update(userCmsForUpdate);
		}
		//毁掉session
		request.getSession().invalidate();
		return "login/login";
	}
	
}
