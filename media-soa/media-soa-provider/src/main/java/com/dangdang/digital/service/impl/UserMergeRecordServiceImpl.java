package com.dangdang.digital.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IUserMergeRecordDao;
import com.dangdang.digital.model.UserMergeRecord;
import com.dangdang.digital.service.IUserMergeRecordService;
/**
 * UserMergeRecord Manager.
 */
@Service
public class UserMergeRecordServiceImpl extends BaseServiceImpl<UserMergeRecord, Long> implements IUserMergeRecordService {

	@Resource
	IUserMergeRecordDao dao;
	
	public IBaseDao<UserMergeRecord> getBaseDao() {
		return dao;
	}

	@Override
	public void createUserMergeRecord(Long oldCustId, Long newCustId,
			String consumerDepositId, String boughtId) {
		UserMergeRecord userMergeRecord = new UserMergeRecord();
		userMergeRecord.setOldCustId(oldCustId);
		userMergeRecord.setNewCustId(newCustId);
		userMergeRecord.setCreateTime(new Date());
		userMergeRecord.setLastUpdateTime(userMergeRecord.getCreateTime());
		userMergeRecord.setBoughtId(boughtId);
		userMergeRecord.setConsumerDepositId(consumerDepositId);
		this.save(userMergeRecord);
	}
	
}
