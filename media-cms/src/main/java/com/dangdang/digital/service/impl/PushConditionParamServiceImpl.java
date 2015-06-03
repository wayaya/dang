package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IPushConditionParamDao;
import com.dangdang.digital.model.PushConditionParam;
import com.dangdang.digital.service.IPushConditionParamService;

@Service
public class PushConditionParamServiceImpl extends BaseServiceImpl<PushConditionParam, Long> implements IPushConditionParamService{

	@Resource
	IPushConditionParamDao pushConditionParamDao;
	
	@Override
	public IBaseDao<PushConditionParam> getBaseDao() {
		return pushConditionParamDao;
	}

}
