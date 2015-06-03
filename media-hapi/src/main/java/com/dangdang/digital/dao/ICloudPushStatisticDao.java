package com.dangdang.digital.dao;

import java.util.Date;

import com.dangdang.digital.model.CloudPushStatistic;

public interface ICloudPushStatisticDao extends IBaseDao<CloudPushStatistic>{
	void addOpenNumber(Long planId, int number, Date date);
	void addPushNumber(Long planId, int number, Date date);
}
