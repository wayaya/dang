package com.dangdang.digital.dao;

import java.util.Date;

import com.dangdang.digital.model.CloudPushData;

public interface ICloudPushDataDao extends IBaseDao<CloudPushData>{

	int coundTodayDataByPlanId(Date creationDate, Long planId);
	
}
