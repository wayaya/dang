package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IJobRecordDao;
import com.dangdang.digital.model.JobRecord;
import com.dangdang.digital.service.IJobRecordService;

@Service
@Transactional
public class JobRecordServiceImpl extends BaseServiceImpl<JobRecord, Long> implements IJobRecordService{

	@Resource
	IJobRecordDao jobRecordDao;
	
	@Override
	public IBaseDao<JobRecord> getBaseDao() {
		return jobRecordDao;
	}

}
