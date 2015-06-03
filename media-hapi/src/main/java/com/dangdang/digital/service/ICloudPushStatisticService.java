package com.dangdang.digital.service;

import java.util.Date;

import com.dangdang.digital.model.CloudPushStatistic;

public interface ICloudPushStatisticService extends IBaseService<CloudPushStatistic, Long>{

	void addOpenNumber(Long planId, int number, Date date);
	void addPushNumber(Long planId, int number, Date date);
}
