package com.dangdang.digital.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ICloudPushPlanStatusDao;
import com.dangdang.digital.model.CloudPushPlanStatus;
import com.dangdang.digital.utils.DateUtil;
@Repository
public class CloudPushPlanStatusDaoImpl extends BaseDaoImpl<CloudPushPlanStatus> implements ICloudPushPlanStatusDao{

	@Override
	public List<CloudPushPlanStatus> findPreparedSendUntriggeredPlan() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("creationDate", DateUtil.getOnlyDay(new Date()));
		map.put("sendTime", new Date());
		map.put("planJobStatus", 2);
		map.put("planSendStatus", 1);
		
		return selectList("CloudPushPlanStatusMapper.findPreparedSendUntriggeredPlan", map);
	}

	@Override
	public List<CloudPushPlanStatus> findByCreationDateAndPlanId(Long planId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("creationDate", DateUtil.getOnlyDay(new Date()));
		map.put("planId", planId);
		
		return selectList("CloudPushPlanStatusMapper.findByCreationDateAndPlanId", map);
	}

}
