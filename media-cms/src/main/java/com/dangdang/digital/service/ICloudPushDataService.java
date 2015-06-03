package com.dangdang.digital.service;

import java.util.Date;

import com.dangdang.digital.model.CloudPushData;
import com.dangdang.digital.model.CloudPushPlan;
import com.dangdang.digital.model.NotificationApikey;

public interface ICloudPushDataService extends IBaseService<CloudPushData, Long>{

	int coundTodayDataByPlanId(Date creationDate, Long planId);

	void deleteTodayDataByPlanId(Date date, Long cloudPushPlanId);
}
