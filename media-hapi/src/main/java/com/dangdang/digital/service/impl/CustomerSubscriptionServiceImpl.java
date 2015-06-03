package com.dangdang.digital.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICustomerSubscriptionDao;
import com.dangdang.digital.model.CustomerMediaHistory;
import com.dangdang.digital.model.CustomerSubscription;
import com.dangdang.digital.service.ICustomerSubscriptionService;
import com.dangdang.digital.utils.DateUtil;

@Service
@Transactional
public class CustomerSubscriptionServiceImpl extends BaseServiceImpl<CustomerSubscription, Long>
				implements ICustomerSubscriptionService{
	@Resource
	ICustomerSubscriptionDao customerSubscriptionDao;
	
	@Resource(name="masterRedisTemplate")
    protected RedisTemplate<String, Object> masterRedisTemplate;
	
	@Resource(name="slaveRedisTemplate")
    protected RedisTemplate<String, Object> slaveRedisTemplate;

	
	@Override
	public IBaseDao<CustomerSubscription> getBaseDao() {
		return customerSubscriptionDao;
	}


	@Override
	public void updateAnonymousRecords(String deviceSerialNo, Long custId) {
		customerSubscriptionDao.updateAnonymousRecords(deviceSerialNo, custId);
	}


	@Override
	public void clearCustomerSusbscription(Integer appId, String deviceSerialNo) {
		customerSubscriptionDao.clearCustomerSusbscription(appId, deviceSerialNo);
	}

}
