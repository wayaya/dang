package com.dangdang.digital.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICloudPushPlanStatusApi;
import com.dangdang.digital.model.CloudPushPlanStatus;
import com.dangdang.digital.service.ICloudPushPlanStatusService;

/**
 * Description: 更新推送计划状态表状态实现类
 * All Rights Reserved.
 * @version 1.0  2015年2月2日 下午2:55:30  by 于楠（yunan@dangdang.com）创建
 */
@Component("cloudPushPlanStatusApi")
public class CloudPushPlanStatusApiImpl implements ICloudPushPlanStatusApi {
	
	@Resource
	ICloudPushPlanStatusService cloudPushPlanStatusService;
	
	Logger logger = LoggerFactory.getLogger(CloudPushPlanStatusApiImpl.class);

	@Override
	public void updateCloudPushPlanStatus(Long planId, Integer jobStatus) {
		logger.info("updateCloudPushPlanStatus planId:"+planId+" status:"+jobStatus);
		List<CloudPushPlanStatus> cloudPushPlanStatusList=cloudPushPlanStatusService.findByCreationDateAndPlanId(planId);
		if(cloudPushPlanStatusList!=null && cloudPushPlanStatusList.size()>0){
			CloudPushPlanStatus cloudPushPlanStatus = cloudPushPlanStatusList.get(0);
			cloudPushPlanStatus.setPlanJobStatus(jobStatus);
			cloudPushPlanStatusService.update(cloudPushPlanStatus);
		}
	}

}
