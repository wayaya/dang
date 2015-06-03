package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IPushConditionParamDao;
import com.dangdang.digital.dao.IPushConditionVariableDao;
import com.dangdang.digital.model.PushConditionParam;
import com.dangdang.digital.model.PushConditionVariable;
import com.dangdang.digital.service.IPushConditionVariableService;

@Service
public class PushConditionVariableServiceImpl extends BaseServiceImpl<PushConditionVariable, Long> implements IPushConditionVariableService{

	@Resource
	IPushConditionVariableDao pushConditionVariableDao;
	
	@Override
	public IBaseDao<PushConditionVariable> getBaseDao() {
		return pushConditionVariableDao;
	}

}
