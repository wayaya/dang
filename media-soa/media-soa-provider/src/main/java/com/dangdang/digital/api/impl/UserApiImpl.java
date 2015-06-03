package com.dangdang.digital.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.dangdang.digital.api.IUserApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.UserMergeRecord;
import com.dangdang.digital.model.UserMonthly;
import com.dangdang.digital.service.IUserMergeRecordService;
import com.dangdang.digital.service.IUserMonthlyService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.vo.UserRradeInfoVo;

@Component("userApi")
public class UserApiImpl implements IUserApi{
	
	@Resource
	private IUserMonthlyService userMonthlyService;
	@Resource
	private IUserService userService;
	@Resource
	private IUserMergeRecordService userMergeRecordService;

	@Override
	public List<UserMonthly> selectUserMonthly(Long custId) {
		return userMonthlyService.selectUserMonthly(custId);
	}

	@Override
	public void mergeUserTradeInfo(Long oldCustId, Long newCustId) throws MediaException{
		if (oldCustId == null || newCustId == null) {
			throw new MediaException(
					ErrorCodeEnum.ERROR_CODE_10002.getErrorCode() + "",
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage());
		}
		try {
			userService.mergeUserTradeInfo(oldCustId, newCustId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MediaException(
					ErrorCodeEnum.ERROR_CODE_10000.getErrorCode() + "",
					ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage());
		}
	}
	

	@Override
	public void rollbackForMergeUserTradeInfo(Long oldCustId, Long newCustId)
			throws MediaException {
		if (oldCustId == null || newCustId == null) {
			throw new MediaException(
					ErrorCodeEnum.ERROR_CODE_10002.getErrorCode() + "",
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage());
		}
		try {
			List<UserMergeRecord> result = userMergeRecordService.findMasterListByParams("oldCustId",oldCustId,"newCustId",newCustId);
			if(CollectionUtils.isEmpty(result)){
				return;
			}		
			userService.rollbackForMergeUserTradeInfo(oldCustId, newCustId, result.get(0).getConsumerDepositId(), result.get(0).getBoughtId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new MediaException(
					ErrorCodeEnum.ERROR_CODE_10000.getErrorCode() + "",
					ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage());
		}		
	}

	@Override
	public UserRradeInfoVo findUserTradeInfo(Long custId) {
		return userService.findUserTradeInfo(custId);
	}
	
}
