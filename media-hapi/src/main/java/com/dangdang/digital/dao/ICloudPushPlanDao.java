package com.dangdang.digital.dao;

import com.dangdang.digital.model.CloudPushPlan;

public interface ICloudPushPlanDao extends IBaseDao<CloudPushPlan>{

	void addOpenNumber(Long planId, int number);
	void addPushNumber(Long planId, int number);
}
