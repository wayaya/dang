package com.dangdang.digital.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ICustomerSubscriptionDao;
import com.dangdang.digital.model.CustomerSubscription;

@Repository
public class CustomerSubscriptionDaoImpl extends BaseDaoImpl<CustomerSubscription> implements ICustomerSubscriptionDao{

	@Override
	public void updateAnonymousRecords(String deviceNo, Long custId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceNo", deviceNo);
		map.put("custId", custId);
		this.update("CustomerSubscriptionMapper.updateAnonymousRecords", map);
		
	}

	@Override
	public void clearCustomerSusbscription(Integer appId, String deviceSerialNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appId", appId);
		map.put("deviceNo", deviceSerialNo);
		map.put("creationDate", new Date());
		this.update("CustomerSubscriptionMapper.clearCustomerSusbscription", map);
	}
	
}
