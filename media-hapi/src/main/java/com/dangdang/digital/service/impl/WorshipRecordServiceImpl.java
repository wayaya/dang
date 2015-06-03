package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.model.WorshipRecord;
import com.dangdang.digital.dao.IWorshipRecordDao;
import com.dangdang.digital.service.IWorshipRecordService;
/**
 * WorshipRecord Manager.
 */
@Service("worshipRecordService")
public class WorshipRecordServiceImpl extends BaseServiceImpl<WorshipRecord, Long> implements IWorshipRecordService {

	@Resource
	IWorshipRecordDao dao;
	
	public IBaseDao<WorshipRecord> getBaseDao() {
		return dao;
	}
	
}
