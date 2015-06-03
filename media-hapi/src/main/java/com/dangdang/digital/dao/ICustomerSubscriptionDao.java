package com.dangdang.digital.dao;

import com.dangdang.digital.model.CustomerSubscription;

public interface ICustomerSubscriptionDao extends IBaseDao<CustomerSubscription>{

	void updateAnonymousRecords(String deviceSerialNo, Long custId);

	void clearCustomerSusbscription(Integer appId, String deviceSerialNo);

}
