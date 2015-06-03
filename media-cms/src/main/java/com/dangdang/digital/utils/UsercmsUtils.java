package com.dangdang.digital.utils;

import java.util.HashSet;
import java.util.Set;

import com.dangdang.digital.constant.DigitalCmsConstants;
import com.dangdang.digital.model.Usercms;
import com.dangdang.digital.view.UsercmsSessionInfo;

public class UsercmsUtils {

	public static UsercmsSessionInfo getUsercmsSessionInfo(){
		UsercmsSessionInfo usercmsSessionInfo = (UsercmsSessionInfo)AppUtil.getSession().getAttribute(DigitalCmsConstants.CMS_USER_INFO_STORED_IN_SESSION);
		return usercmsSessionInfo;
	}
	
	public static Usercms getCurrentUser(){
		
		UsercmsSessionInfo usercmsSessionInfo = (UsercmsSessionInfo)AppUtil.getSession().getAttribute(DigitalCmsConstants.CMS_USER_INFO_STORED_IN_SESSION);
		if(usercmsSessionInfo == null){
			return null;
		}else{
			return usercmsSessionInfo.getUserInfo();
		}
	}
	
}
