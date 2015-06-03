package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IDiscoveryDao;
import com.dangdang.digital.model.Discovery;
import com.dangdang.digital.service.IDiscoveryService;


@Service
public class DiscoveryServiceImpl extends BaseServiceImpl<Discovery, Long>
		implements IDiscoveryService {

	@Resource
	private IDiscoveryDao discovertyDao;
	@Override
	public IBaseDao<Discovery> getBaseDao() {
		return discovertyDao;
	}


}
