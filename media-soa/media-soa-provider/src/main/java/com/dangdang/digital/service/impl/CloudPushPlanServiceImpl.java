package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICloudPushPlanDao;
import com.dangdang.digital.model.CloudPushPlan;
import com.dangdang.digital.service.ICloudPushPlanService;

@Service("cloudPushPlanService")
public class CloudPushPlanServiceImpl extends BaseServiceImpl<CloudPushPlan, Long> implements ICloudPushPlanService{

	@Resource
	ICloudPushPlanDao cloudPushPlanDao;
	
	@Override
	public IBaseDao<CloudPushPlan> getBaseDao() {
		return cloudPushPlanDao;
	}

}
