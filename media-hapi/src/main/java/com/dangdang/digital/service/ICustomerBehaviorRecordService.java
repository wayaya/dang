package com.dangdang.digital.service;

import com.dangdang.digital.model.CustomerBehaviorRecord;

public interface ICustomerBehaviorRecordService extends IBaseService<CustomerBehaviorRecord, Long>{

	void saveRecord(CustomerBehaviorRecord queryRecord);

	CustomerBehaviorRecord getRecord(CustomerBehaviorRecord query);

	void updateRecord(CustomerBehaviorRecord custRecord);

}
