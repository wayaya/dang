package com.dangdang.digital.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICloudPushDataDao;
import com.dangdang.digital.model.CloudPushData;
import com.dangdang.digital.service.ICloudPushDataService;
import com.dangdang.digital.utils.DateUtil;

@Service("cloudPushDataService")
public class CloudPushDataServiceImpl extends BaseServiceImpl<CloudPushData, Long> implements ICloudPushDataService{

	@Resource
	ICloudPushDataDao cloudPushDataDao;
	
	@Override
	public IBaseDao<CloudPushData> getBaseDao() {
		return cloudPushDataDao;
	}


	@Override
	public int coundTodayDataByPlanId(Date creationDate, Long planId) {
		
		return cloudPushDataDao.coundTodayDataByPlanId(DateUtil.getOnlyDay(creationDate), planId);
	}


}
