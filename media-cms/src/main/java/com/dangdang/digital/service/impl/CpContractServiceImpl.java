package com.dangdang.digital.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICpContractDao;
import com.dangdang.digital.model.CpContract;
import com.dangdang.digital.service.ICpContractService;


/**
 * CpContract Manager.
 */
@Service
public class CpContractServiceImpl extends BaseServiceImpl<CpContract, Integer> implements ICpContractService {

	@Resource
	ICpContractDao dao;
	
	public IBaseDao<CpContract> getBaseDao() {
		return dao;
	}
	
}
