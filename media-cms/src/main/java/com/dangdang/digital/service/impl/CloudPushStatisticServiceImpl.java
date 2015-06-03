package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICloudPushStatisticDao;
import com.dangdang.digital.model.CloudPushStatistic;
import com.dangdang.digital.service.ICloudPushStatisticService;

@Service
public class CloudPushStatisticServiceImpl extends BaseServiceImpl<CloudPushStatistic, Long> implements ICloudPushStatisticService{

	@Resource
	ICloudPushStatisticDao cloudPushStatisticDao;
	
	@Override
	public IBaseDao<CloudPushStatistic> getBaseDao() {
		return cloudPushStatisticDao;
	}

}
