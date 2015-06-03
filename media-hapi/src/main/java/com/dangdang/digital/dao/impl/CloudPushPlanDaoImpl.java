package com.dangdang.digital.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ICloudPushPlanDao;
import com.dangdang.digital.model.CloudPushPlan;
import com.dangdang.digital.utils.SafeConvert;
@Repository
public class CloudPushPlanDaoImpl extends BaseDaoImpl<CloudPushPlan> implements ICloudPushPlanDao{

	@Override
	public void addOpenNumber(Long planId, int number) {
		
		CloudPushPlan plan = new CloudPushPlan();
		plan.setCloudPushPlanId(planId);
		plan.setOpenedNumber(SafeConvert.convertStringToLong(""+number, 0));
		this.getSqlSessionTemplate().update("CloudPushPlanMapper.addOpenNumber", plan);
	}

	@Override
	public void addPushNumber(Long planId, int number) {
		
		CloudPushPlan plan = new CloudPushPlan();
		plan.setCloudPushPlanId(planId);
		plan.setPushedNumber(SafeConvert.convertStringToLong(""+number, 0));
		this.getSqlSessionTemplate().update("CloudPushPlanMapper.addPushNumber", plan);
		
	}

}
