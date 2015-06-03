package com.dangdang.digital.service;

import com.dangdang.digital.model.UserMergeRecord;


/**
 * UserMergeRecord Manager.
 */
public interface IUserMergeRecordService extends IBaseService<UserMergeRecord, Long> {
	
	public void createUserMergeRecord(Long oldCustId,Long newCustId,String consumerDepositId,String boughtId);
}
