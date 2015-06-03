package com.dangdang.digital.processor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.ICustomerSubscriptionService;
import com.dangdang.digital.utils.DDClickUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.SafeConvert;

/**
 * 
 * Description: 重装客户端，需要调用此接口去清空追更
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 上午10:36:02  by 魏嵩（weisong@dangdang.com）创建
 */

@Component("hapiclearCustomerSubscriptionprocessor")
public class ClearCustomerSubscriptionProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClearCustomerSubscriptionProcessor.class);

	@Resource
	ICustomerSubscriptionService customerSubscriptionService;
	
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		clearCustomerSubscription(request, response, sender);
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
	private void clearCustomerSubscription(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		
		String appIdStr = request.getParameter("appId");
		String deviceSerialNo = DDClickUtils.get(request, "deviceSerialNo", "");
		
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
		
		Integer appId = SafeConvert.convertStringToInteger(appIdStr, -1);
		
		customerSubscriptionService.clearCustomerSusbscription(appId, deviceSerialNo);
		
		sender.put("updateResult", "OK");
		sender.success(response);
	}

}
