package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICloudPushConditionDao;
import com.dangdang.digital.model.CloudPushCondition;
import com.dangdang.digital.service.ICloudPushConditionService;

@Service
public class CloudPushConditionServiceImpl extends BaseServiceImpl<CloudPushCondition, Long> implements ICloudPushConditionService{

	@Resource
	ICloudPushConditionDao cloudPushConditionDao;
	
	@Override
	public IBaseDao<CloudPushCondition> getBaseDao() {
		return cloudPushConditionDao;
	}

}
