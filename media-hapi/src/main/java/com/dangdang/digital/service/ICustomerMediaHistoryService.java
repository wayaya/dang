package com.dangdang.digital.service;

import com.dangdang.digital.model.CustomerMediaHistory;

public interface ICustomerMediaHistoryService extends IBaseService<CustomerMediaHistory, Long>{

	void saveHistory(CustomerMediaHistory history);

	void updateHistory(CustomerMediaHistory history);
	
	CustomerMediaHistory getHistory(CustomerMediaHistory history);

}
