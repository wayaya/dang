package com.dangdang.digital.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ICloudPushDataDao;
import com.dangdang.digital.model.CloudPushData;
@Repository
public class CloudPushDataDaoImpl extends BaseDaoImpl<CloudPushData> implements ICloudPushDataDao{

	@Override
	public int coundTodayDataByPlanId(Date creationDate, Long planId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", planId);
		map.put("creationDate", creationDate);
		
		return (Integer)this.getSqlSessionQueryTemplate().selectOne("CloudPushDataMapper.coundTodayDataByPlanId", map);
	}

	
}
