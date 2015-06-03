package com.dangdang.digital.service;


import com.dangdang.digital.model.CustomerSubscription;

public interface ICustomerSubscriptionService extends IBaseService<CustomerSubscription,Long>{

	void updateAnonymousRecords(String deviceSerialNo, Long custId);

	void clearCustomerSusbscription(Integer appId, String deviceSerialNo);
	
}