package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.Notification;
import com.dangdang.digital.service.INotificationService;
import com.dangdang.digital.utils.DDClickUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.SafeConvert;

/**
 * 
 * Description: 用户推送通知开关
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 上午10:36:02  by 魏嵩（weisong@dangdang.com）创建
 */

@Component("hapiupdateNotificationSwitchprocessor")
public class GetUpdateNotificationSwitchProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetUpdateNotificationSwitchProcessor.class);

	@Resource
	INotificationService notificationService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		initNotification(request, response, sender);
	}

	/**
	 * 
	 * Description: 
	 * @Version1.0 2014年12月11日 下午4:23:33 by 魏嵩（weisong@dangdang.com）创建
	 * @param request
	 * @param response
	 * @param sender
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void initNotification(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		
		String appIdStr = request.getParameter("appId");
		String deviceSerialNo = DDClickUtils.get(request, "deviceSerialNo", "");
		String status = request.getParameter("notificationStatus");
		
		if(StringUtils.isEmpty(appIdStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_21001.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21001.getErrorMessage(), response);
			return;
		}
		
		if(StringUtils.isEmpty(deviceSerialNo)){
			
			sender.fail(ErrorCodeEnum.ERROR_CODE_21004.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21004.getErrorMessage(), response);
			return;
		}
		
		if(StringUtils.isEmpty(status)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_21017.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21017.getErrorMessage(), response);
			return;
		}
		int nStatus = SafeConvert.convertStringToInteger(status, -1);
		if(nStatus<0){
			sender.fail(ErrorCodeEnum.ERROR_CODE_21018.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21018.getErrorMessage(), response);
			return;
		}
		
		Integer appId = SafeConvert.convertStringToInteger(appIdStr, -1);
		//去数据库查询是否有该appId下此deviceSerialNo的用户，如果有，那么更新，如果没有，则返回错误
		List<Notification> existList = notificationService.findListByParams("deviceNo",deviceSerialNo, "appId", appId);
		if(existList!=null && existList.size()>0){
			
			Notification notificationInDB = existList.get(0);
			notificationInDB.setStatus(nStatus);
			notificationService.update(notificationInDB);
			
		}else{
			
			LOGGER.error("appId is:"+appId+", and deviceNo is:"+deviceSerialNo+" does not exist");
			sender.fail(ErrorCodeEnum.ERROR_CODE_21019.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21019.getErrorMessage(), response);
			return;
		}
		
		sender.put("updateResult", "OK");
		sender.success(response);
	}

}
