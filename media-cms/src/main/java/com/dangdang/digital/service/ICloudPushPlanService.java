package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.CloudPushPlan;

public interface ICloudPushPlanService extends IBaseService<CloudPushPlan, Long>{

	void updatePushPlanStatus(List<Long> idsToSet, Integer planStatus);

	void updatePlan(CloudPushPlan cloudPushPlan, Integer editMode) throws MediaException;

}
