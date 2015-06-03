package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IPushStrategyDao;
import com.dangdang.digital.model.PushStrategy;
import com.dangdang.digital.service.IPushStrategyService;

@Service
public class PushStrategyServiceImpl extends BaseServiceImpl<PushStrategy, Long> implements IPushStrategyService{

	@Resource
	IPushStrategyDao pushStrategyDao;
	
	@Override
	public IBaseDao<PushStrategy> getBaseDao() {
		return pushStrategyDao;
	}

}
