package com.dangdang.digital.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dangdang.digital.constant.DigitalCmsConstants;
import com.dangdang.digital.exception.MediaSecurityException;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.view.UsercmsSessionInfo;

public class SecurityInterceptor implements HandlerInterceptor{

	private List<String> excludedUrls;
	public static boolean interceptLoginEnabled = false;
	
	static{
		interceptLoginEnabled = SafeConvert.convertToBoolean(ConfigPropertieUtils.getString("media.system.security.interceptor.login.enabled"), false);
	}

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
    	
    	if(request.getSession().getAttribute("interceptLoginEnabled") == null){
    		request.getSession().setAttribute("interceptLoginEnabled", interceptLoginEnabled);
    	}
    	
    	if(!interceptLoginEnabled){
    		return true;
    	}
    	
        String requestUri = request.getRequestURI();
        for (String url : excludedUrls) {
            if (requestUri.startsWith(url)) {
                return true;
            }
        }

        UsercmsSessionInfo usercmsSessionInfo = (UsercmsSessionInfo) request.getSession().getAttribute(DigitalCmsConstants.CMS_USER_INFO_STORED_IN_SESSION);
        
        if (usercmsSessionInfo == null || usercmsSessionInfo.getUserInfo() == null||
        		usercmsSessionInfo.getUserInfo().getUsercmsId() ==null || 
        		usercmsSessionInfo.getUserInfo().getUsercmsId() == 0L) {
        	
            throw new MediaSecurityException();
        } else {
            return true;
        }
    }

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}
	
}
