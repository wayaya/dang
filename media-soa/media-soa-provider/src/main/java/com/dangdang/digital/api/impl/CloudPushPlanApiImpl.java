package com.dangdang.digital.api.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICloudPushPlanApi;
import com.dangdang.digital.model.CloudPushPlan;
import com.dangdang.digital.service.ICloudPushPlanService;
import com.dangdang.digital.vo.CloudPushPlanVo;

/**
 * Description: 查询推送计划表的数据实现类
 * All Rights Reserved.
 * @version 1.0  2015年2月2日 下午2:45:46  by 于楠（yunan@dangdang.com）创建
 */
@Component("cloudPushPlanApi")
public class CloudPushPlanApiImpl implements ICloudPushPlanApi{
	@Resource
	ICloudPushPlanService cloudPushPlanService;
	
	public CloudPushPlanVo getCloudPushPlan(Long planId){
		CloudPushPlan cloudPushPlan = cloudPushPlanService.get(planId);
		CloudPushPlanVo cloudPushPlanVo = new CloudPushPlanVo();
		cloudPushPlanVo.setCloudPushPlanId(planId);
		cloudPushPlanVo.setMessageDescription(cloudPushPlan.getMessageDescription());
		cloudPushPlanVo.setMessageTitle(cloudPushPlan.getMessageTitle());
		cloudPushPlanVo.setPlanCondition(cloudPushPlan.getPlanCondition());
		cloudPushPlanVo.setPlanStatus(cloudPushPlan.getPlanStatus());
		cloudPushPlanVo.setDeviceTypeAndroid(cloudPushPlan.getDeviceTypeAndroid());
		cloudPushPlanVo.setDeviceTypeIos(cloudPushPlan.getDeviceTypeIos());
		return cloudPushPlanVo;
		
	}

}
