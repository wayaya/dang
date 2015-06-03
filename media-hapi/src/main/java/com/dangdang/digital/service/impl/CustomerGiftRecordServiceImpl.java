package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICustomerGiftRecordDao;
import com.dangdang.digital.model.CustomerGiftRecord;
import com.dangdang.digital.service.ICustomerGiftRecordService;

/**
 * 
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年3月17日 下午3:26:10  by 魏嵩（weisong@dangdang.com）创建
 */
@Service
public class CustomerGiftRecordServiceImpl extends BaseServiceImpl<CustomerGiftRecord, Long> implements ICustomerGiftRecordService {

	@Resource
	ICustomerGiftRecordDao customerGiftRecord;
	
	@Override
	public IBaseDao<CustomerGiftRecord> getBaseDao() {
		return customerGiftRecord;
	}

}
