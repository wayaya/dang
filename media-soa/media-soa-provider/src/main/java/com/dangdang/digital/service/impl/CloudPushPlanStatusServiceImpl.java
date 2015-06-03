package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICloudPushPlanStatusDao;
import com.dangdang.digital.model.CloudPushPlanStatus;
import com.dangdang.digital.service.ICloudPushPlanStatusService;

@Service("cloudPushPlanStatusService")
public class CloudPushPlanStatusServiceImpl extends BaseServiceImpl<CloudPushPlanStatus, Long> implements ICloudPushPlanStatusService{

	@Resource
	ICloudPushPlanStatusDao cloudPushPlanStatusDao;
	
	@Override
	public IBaseDao<CloudPushPlanStatus> getBaseDao() {
		return cloudPushPlanStatusDao;
	}

	@Override
	public List<CloudPushPlanStatus> findPreparedSendUntriggeredPlan() {
		return cloudPushPlanStatusDao.findPreparedSendUntriggeredPlan();
	}

	@Override
	public List<CloudPushPlanStatus> findByCreationDateAndPlanId(Long planId) {
		return cloudPushPlanStatusDao.findByCreationDateAndPlanId(planId);
	}


}
