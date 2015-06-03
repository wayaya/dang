package com.dangdang.digital.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.dangdang.digital.constant.DigitalCmsConstants;
import com.dangdang.digital.model.Usercms;
import com.dangdang.digital.service.IUsercmsService;
import com.dangdang.digital.utils.AppUtil;
import com.dangdang.digital.view.UsercmsSessionInfo;

public class SessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent se) {

	}

	public void sessionDestroyed(HttpSessionEvent se) {
		
		ServletContext context = se.getSession().getServletContext();
		UsercmsSessionInfo usercmsSessionInfo = (UsercmsSessionInfo) se.getSession().getAttribute(DigitalCmsConstants.CMS_USER_INFO_STORED_IN_SESSION);
        
        if (!(usercmsSessionInfo == null || usercmsSessionInfo.getUserInfo() == null||
        		usercmsSessionInfo.getUserInfo().getUsercmsId() ==null || 
        		usercmsSessionInfo.getUserInfo().getUsercmsId() == 0L)) {
        	
        	IUsercmsService usercmsService = (IUsercmsService) AppUtil.getObjectFromApplication(context, "usercmsService");
    		Usercms usercmsInDB = usercmsService.findMasterById(usercmsSessionInfo.getUserInfo().getUsercmsId());
    		
    		Usercms usercms = new Usercms();
    		usercms.setUsercmsId(usercmsInDB.getUsercmsId());
    		usercms.setOnlineStatus((byte)0);
    		usercmsService.update(usercms);
        }
		
	}
	
}
