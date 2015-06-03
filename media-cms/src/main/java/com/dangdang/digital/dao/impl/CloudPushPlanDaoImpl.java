package com.dangdang.digital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ICloudPushPlanDao;
import com.dangdang.digital.model.CloudPushPlan;
@Repository
public class CloudPushPlanDaoImpl extends BaseDaoImpl<CloudPushPlan> implements ICloudPushPlanDao{

	@Override
	public void updatePushPlanStatus(List<Long> idsToSet, Integer planStatus) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", idsToSet);
		param.put("planStatus", planStatus);
		this.update("CloudPushPlanMapper.updatePushPlanStatus", param);
	}

}
