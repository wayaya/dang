package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICloudPushPlanDao;
import com.dangdang.digital.model.CloudPushPlan;
import com.dangdang.digital.service.ICloudPushPlanService;

@Service
public class CloudPushPlanServiceImpl extends BaseServiceImpl<CloudPushPlan, Long> implements ICloudPushPlanService{

	@Resource
	ICloudPushPlanDao cloudPushPlanDao;
	
	@Override
	public IBaseDao<CloudPushPlan> getBaseDao() {
		return cloudPushPlanDao;
	}

	@Override
	public void addOpenNumber(Long planId, int number) {
		cloudPushPlanDao.addOpenNumber(planId, number);
	}

	@Override
	public void addPushNumber(Long planId, int number) {
		cloudPushPlanDao.addPushNumber(planId, number);
	}

}
