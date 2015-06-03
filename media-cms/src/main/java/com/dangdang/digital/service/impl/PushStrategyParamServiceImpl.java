package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IPushStrategyParamDao;
import com.dangdang.digital.model.PushStrategyParam;
import com.dangdang.digital.service.IPushStrategyParamService;

@Service
public class PushStrategyParamServiceImpl extends BaseServiceImpl<PushStrategyParam, Long> implements IPushStrategyParamService{

	@Resource
	IPushStrategyParamDao pushStrategyParamDao;
	
	@Override
	public IBaseDao<PushStrategyParam> getBaseDao() {
		return pushStrategyParamDao;
	}

}
