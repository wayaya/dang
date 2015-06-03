package com.dangdang.digital.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dangdang.digital.api.ICloudPushDataApi;
import com.dangdang.digital.job.push.worker.AutoPushDataWorker;
import com.dangdang.digital.model.CloudPushData;
import com.dangdang.digital.model.CloudPushPlan;
import com.dangdang.digital.model.NotificationApikey;
import com.dangdang.digital.model.PushParam;
import com.dangdang.digital.service.ICloudPushDataService;
import com.dangdang.digital.service.ICloudPushPlanService;
import com.dangdang.digital.service.INotificationApikeyService;
import com.dangdang.digital.utils.BaiduPushUtil;
import com.dangdang.digital.utils.DateUtil;

/**
 * Description: 批量插入推送内容数据表实现类
 * All Rights Reserved.
 * @version 1.0  2015年2月2日 下午2:52:18  by 于楠（yunan@dangdang.com）创建
 */
@Component("cloudPushDataApi")
public class CloudPushDataApiImpl implements ICloudPushDataApi {
	
	@Resource
	INotificationApikeyService notificationApikeyService;
	
	@Resource
	ICloudPushPlanService cloudPushPlanService;
	
	@Resource
	ICloudPushDataService cloudPushDataService;
	
	Logger logger = LoggerFactory.getLogger(CloudPushDataApiImpl.class);

	@Override
	@SuppressWarnings("unchecked")
	public void saveCloudPushData(List<Map<String, Object>> dataList) {
		//参数messageTitle messageDescription cloudPushPlanId extUserId extChannelId appId custId deviceNo deviceType
		NotificationApikey apiKey = notificationApikeyService.findByAppId(1);
		CloudPushPlan cloudPushPlan = null;
		List<CloudPushData> dataListToSave = new ArrayList<CloudPushData>();
		for(Map<String, Object> dataMap : dataList){
			String messageTitle = (String)dataMap.get("messageTitle");
			String messageDescription = (String)dataMap.get("messageDescription");
			Integer appId = (Integer)dataMap.get("appId");
			Long custId = (Long)dataMap.get("custId");
			String deviceNo = (String)dataMap.get("deviceNo");
			Integer deviceType = (Integer)dataMap.get("deviceType");
			String extUserId = (String)dataMap.get("extUserId");
			String extChannelId = (String)dataMap.get("extChannelId");
			Long cloudPushPlanId = Long.parseLong(dataMap.get("cloudPushPlanId").toString());
			
			if(deviceType==null){
				continue;
			}
			
			if(cloudPushPlan==null){
				cloudPushPlan = cloudPushPlanService.get(cloudPushPlanId);
			}
			PushParam param = new PushParam();
	    	param.setApiKey(apiKey.getApiKey());
	    	param.setSecret(apiKey.getSecret());
	    	param.setPushType(1);
	    	param.setUserId(extUserId);
	    	param.setChannelId(extChannelId);
	    	
	    	int messageType=1;
	    	if(cloudPushPlan.getMessageType()!=null){
	    		messageType=cloudPushPlan.getMessageType();
	    	}
	    	
	    	param.setMessageType(messageType);
	    	
	    	Map<String, Object> pushMsg = new HashMap<String, Object>();
	    	
	    	Map<String, String> androidCustomContentMap = new HashMap<String, String>();
	    	if(StringUtils.isNotEmpty(cloudPushPlan.getCustomContent())){
	    		androidCustomContentMap = JSON.parseObject(cloudPushPlan.getCustomContent(), Map.class);
	    	}
	    	
	    	Map<String, String> iosCustomContentMap = new HashMap<String, String>();
	    	if(StringUtils.isNotEmpty(cloudPushPlan.getCustomContentIos())){
	    		iosCustomContentMap = JSON.parseObject(cloudPushPlan.getCustomContentIos(), Map.class);
	    	}
	    	
	    	if(cloudPushPlan.getDeviceTypeAndroid()!=null && cloudPushPlan.getDeviceTypeAndroid().booleanValue() && deviceType==3 ){
	    		
	    		pushMsg.put("title", messageTitle);
	    		pushMsg.put("description", messageDescription);
	    		if(messageType==1){
	    			pushMsg.put("open_type", cloudPushPlan.getOpenType());
			    	pushMsg.put("url", cloudPushPlan.getOpenUrl());
		        	if(StringUtils.isNotEmpty(cloudPushPlan.getCustomContent())){
		    	    	if(androidCustomContentMap!=null){
		    	    		pushMsg.put("custom_content", androidCustomContentMap);
		    	    	}
		        	}
	    		}else{
	    			pushMsg.putAll(androidCustomContentMap);
	    		}
	    		
	    		param.setDeviceType(3);
	    		String unsigned = BaiduPushUtil.getUnsignedParameterMapString(param, pushMsg );
	    		
	    		CloudPushData cloudPushData = new CloudPushData();
	    		cloudPushData.setPlanId(cloudPushPlan.getCloudPushPlanId());
	    		cloudPushData.setCreationDate(DateUtil.getOnlyDay(new Date()));
	    		cloudPushData.setPlanParam(unsigned);
	    		cloudPushData.setAppId(appId);
	    		cloudPushData.setCustId(custId);
	    		cloudPushData.setDeviceNo(deviceNo);
	    		dataListToSave.add(cloudPushData);
			}
	    	
	    	if(cloudPushPlan.getDeviceTypeIos()!=null && cloudPushPlan.getDeviceTypeIos().booleanValue() && deviceType==4 ){
	    		
	    		if(messageType==1){
	    			Map<String, Object> aps = new HashMap<String, Object>();
			    	aps.put("alert", cloudPushPlan.getMessageDescription());
			    	aps.put("badge", 1);
			    	pushMsg.put("aps", aps);
	    		}else{
	    			pushMsg.put("description", cloudPushPlan.getMessageDescription());
	    		}
	    		
	    		if(iosCustomContentMap!=null){
		    		pushMsg.putAll(iosCustomContentMap);
		    	}
	    		
	    		param.setDeviceType(4);
	    		String unsigned = BaiduPushUtil.getUnsignedParameterMapString(param, pushMsg );
	    		
	    		CloudPushData cloudPushData = new CloudPushData();
	    		cloudPushData.setPlanId(cloudPushPlan.getCloudPushPlanId());
	    		cloudPushData.setCreationDate(DateUtil.getOnlyDay(new Date()));
	    		cloudPushData.setPlanParam(unsigned);
	    		cloudPushData.setAppId(appId);
	    		cloudPushData.setCustId(custId);
	    		cloudPushData.setDeviceNo(deviceNo);
	    		dataListToSave.add(cloudPushData);
	    	}
		}
		if(dataListToSave.size()>0){
    		cloudPushDataService.save(dataListToSave);
		}
	}

	@Override
	public void saveCloudPushData(Long planId, List<Long> custIdList) {
		
		if( custIdList==null || custIdList.size()==0 ){
			return;
		}
		logger.info("saveCloudPushData planId:"+planId+" size:"+custIdList.size());
		CloudPushPlan cloudPushPlan = cloudPushPlanService.get(planId);
		AutoPushDataWorker worker = new AutoPushDataWorker(cloudPushPlan, custIdList);
		worker.work();
	}

}
