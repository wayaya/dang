package com.dangdang.digital.processor;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Notification;
import com.dangdang.digital.service.INotificationService;
import com.dangdang.digital.utils.DDClickUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 客户端百度userid, channelid与deviceno, custId的对应关系建立
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 上午10:36:02  by 魏嵩（weisong@dangdang.com）创建
 */

@Component("hapigetUpdateNotificationprocessor")
public class GetUpdateNotificationProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetUpdateNotificationProcessor.class);

	@Resource
	private IAuthorityApiFacade authorityApiFacade;
	
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
		String extUserId = request.getParameter("extUserId");
		String extChannelId = request.getParameter("extChannelId");
		String clientVersionNo = request.getParameter("clientVersionNo");
		String deviceType = request.getParameter("deviceTypeNo");
		String channelId = request.getParameter("channelId");
		String deviceSerialNo = DDClickUtils.get(request, "deviceSerialNo", "");
		
		
		
		String tokenStr = request.getParameter("token");
		Long custId = -1L;
			
		if(!StringUtils.isEmpty(tokenStr)){
			UserTradeBaseVo vo =  authorityApiFacade.getUserInfoByToken(tokenStr);
			if(! (vo==null || vo.getCustId()==null) ){
				custId = vo.getCustId();
			}
		}
		
		if(StringUtils.isEmpty(appIdStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_21001.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21001.getErrorMessage(), response);
			return;
		}
		if(StringUtils.isEmpty(extUserId)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_21002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21002.getErrorMessage(), response);
			return;
		}
		if(StringUtils.isEmpty(extChannelId)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_21003.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21003.getErrorMessage(), response);
			return;
		}
		if(StringUtils.isEmpty(deviceSerialNo)){
			
			sender.fail(ErrorCodeEnum.ERROR_CODE_21004.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21004.getErrorMessage(), response);
			return;
		}
		if(StringUtils.isEmpty(clientVersionNo)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_21006.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21006.getErrorMessage(), response);
			return;
		}
		if(StringUtils.isEmpty(deviceType)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_21007.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21007.getErrorMessage(), response);
			return;
		}
		int deviceTypeNo = SafeConvert.convertStringToInteger(deviceType, -1);
		if(deviceTypeNo<0){
			sender.fail(ErrorCodeEnum.ERROR_CODE_21009.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21009.getErrorMessage(), response);
			return;
		}
		
		if(StringUtils.isEmpty(channelId)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_21008.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_21008.getErrorMessage(), response);
			return;
		}
		
		Integer appId = SafeConvert.convertStringToInteger(appIdStr, -1);
		
		//去数据库查询是否有此deviceSerialNo的用户，如果有，那么更新，如果没有，则插入
		Notification notification = new Notification();
		notification.setDeviceNo(deviceSerialNo);
		notification.setAppClientVersion(clientVersionNo);
		notification.setChannelId(channelId);
		notification.setAppId(appId);
		
		notification.setCustId(custId);
		notification.setDeviceType(deviceTypeNo);
		notification.setExtUserId(extUserId);
		notification.setExtChannelId(extChannelId);
		notification.setLastChangedDate(new Date());
		LOGGER.info("initNotification request:"+JSON.toJSONString(notification));
		
		notification.setCreationDate(new Date());
		List<Notification> existList = notificationService.findListByParams("deviceNo",deviceSerialNo, "appId", appId);
		if(existList!=null && existList.size()>0){
			
			Notification notificationInDB = existList.get(0);
			//若是本次请求没有custId但是DB里面数据有custId，那么不更新，其他情况下都更新为本次请求的参数。
			if(notificationInDB.getCustId()!=-1L && custId == -1L){
				notification.setCustId(notificationInDB.getCustId());
			}
			notification.setNotificationId(notificationInDB.getNotificationId());
			
			//不更新 creation date
			notification.setCreationDate(null);
			notificationService.update(notification);
			LOGGER.info("initNotification updated:"+JSON.toJSONString(notification));
			
		}else{
			//插入一条
			notificationService.save(notification);
			LOGGER.info("initNotification saved:"+JSON.toJSONString(notification));
		}
		
		sender.put("updateResult", "OK");
		sender.success(response);
	}

}
