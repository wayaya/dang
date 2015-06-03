package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.CloudPushPlan;

public interface ICloudPushPlanDao extends IBaseDao<CloudPushPlan>{

	void updatePushPlanStatus(List<Long> idsToSet, Integer planStatus);

}
