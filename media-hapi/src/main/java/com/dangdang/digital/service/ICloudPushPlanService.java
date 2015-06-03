package com.dangdang.digital.service;

import com.dangdang.digital.model.CloudPushPlan;

public interface ICloudPushPlanService extends IBaseService<CloudPushPlan, Long>{

	void addOpenNumber(Long planId, int number);
	void addPushNumber(Long planId, int number);

}
