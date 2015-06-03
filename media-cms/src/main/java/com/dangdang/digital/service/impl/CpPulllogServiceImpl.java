package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICpPullLogDao;
import com.dangdang.digital.model.CpPulllog;
import com.dangdang.digital.service.ICpPulllogService;

@Service
public class CpPulllogServiceImpl extends BaseServiceImpl<CpPulllog, Long>
		implements ICpPulllogService {

	@Resource
	private ICpPullLogDao cpPullLogDao;
	
	@Override
	public IBaseDao<CpPulllog> getBaseDao() {
		return cpPullLogDao;
	}
	
}
