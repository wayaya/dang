package com.dangdang.digital.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICloudPushStatisticDao;
import com.dangdang.digital.model.CloudPushStatistic;
import com.dangdang.digital.service.ICloudPushStatisticService;
import com.dangdang.digital.utils.DateUtil;

@Service("cloudPushStatisticService")
public class CloudPushStatisticServiceImpl extends BaseServiceImpl<CloudPushStatistic, Long> implements ICloudPushStatisticService{

	@Resource
	ICloudPushStatisticDao cloudPushStatisticDao;
	
	@Override
	public IBaseDao<CloudPushStatistic> getBaseDao() {
		return cloudPushStatisticDao;
	}

	@Override
	public void addOpenNumber(Long planId, int number, Date date) {
		
		CloudPushStatistic statistic = new CloudPushStatistic();
		statistic.setCloudPushPlanId(planId);
		statistic.setStatisticDay(DateUtil.getOnlyDay(date));
		
		List<CloudPushStatistic> list = this.findListByParamsObjs(statistic);
		if(list == null || list.size()==0){
			statistic.setOpenedNumber(0L);
			statistic.setPushedNumber(0L);
			this.save(statistic);
		}
		cloudPushStatisticDao.addOpenNumber(planId, number, statistic.getStatisticDay());
	}

	@Override
	public void addPushNumber(Long planId, int number, Date date) {
		
		CloudPushStatistic statistic = new CloudPushStatistic();
		statistic.setCloudPushPlanId(planId);
		statistic.setStatisticDay(DateUtil.getOnlyDay(date));
		
		List<CloudPushStatistic> list = this.findListByParamsObjs(statistic);
		if(list == null || list.size()==0){
			statistic.setOpenedNumber(0L);
			statistic.setPushedNumber(0L);
			this.save(statistic);
		}
		cloudPushStatisticDao.addPushNumber(planId, number, statistic.getStatisticDay());
	}

}
