package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.CloudPushPlanStatus;

public interface ICloudPushPlanStatusDao extends IBaseDao<CloudPushPlanStatus>{

	List<CloudPushPlanStatus> findPreparedSendUntriggeredPlan();
}
