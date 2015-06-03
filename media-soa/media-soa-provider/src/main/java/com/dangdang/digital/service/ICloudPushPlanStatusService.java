package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.CloudPushPlanStatus;

public interface ICloudPushPlanStatusService extends IBaseService<CloudPushPlanStatus, Long>{

	List<CloudPushPlanStatus> findPreparedSendUntriggeredPlan();
	
	List<CloudPushPlanStatus> findByCreationDateAndPlanId(Long planId);
}
