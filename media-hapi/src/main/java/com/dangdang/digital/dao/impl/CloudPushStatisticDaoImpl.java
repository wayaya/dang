package com.dangdang.digital.dao.impl;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ICloudPushStatisticDao;
import com.dangdang.digital.model.CloudPushStatistic;
import com.dangdang.digital.utils.SafeConvert;
@Repository
public class CloudPushStatisticDaoImpl extends BaseDaoImpl<CloudPushStatistic> implements ICloudPushStatisticDao{

	@Override
	public void addOpenNumber(Long planId, int number, Date date) {

		CloudPushStatistic statistic = new CloudPushStatistic();
		statistic.setCloudPushPlanId(planId);
		statistic.setStatisticDay(date);
		statistic.setOpenedNumber(SafeConvert.convertStringToLong(number+"", 0));
		
		this.getSqlSessionTemplate().update("CloudPushStatisticMapper.addOpenNumber", statistic);
	}

	@Override
	public void addPushNumber(Long planId, int number, Date date) {
		CloudPushStatistic statistic = new CloudPushStatistic();
		statistic.setCloudPushPlanId(planId);
		statistic.setStatisticDay(date);
		statistic.setPushedNumber(SafeConvert.convertStringToLong(number+"", 0));
		
		this.getSqlSessionTemplate().update("CloudPushStatisticMapper.addPushNumber", statistic);
	}
	
}
