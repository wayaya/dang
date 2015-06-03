/**
 * Description: UserServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年12月3日 下午2:55:49  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.dangdang.base.account.client.api.IAccountApi;
import com.dangdang.base.account.client.api.IAccountConsumeApi;
import com.dangdang.base.account.client.commons.enums.AccountTypeEnum;
import com.dangdang.base.account.client.commons.enums.ConsumeSourceEnum;
import com.dangdang.base.account.client.commons.enums.ConsumeTypeEnum;
import com.dangdang.base.account.client.commons.enums.CostTypeEnum;
import com.dangdang.base.account.client.commons.enums.PlatformNoEnum;
import com.dangdang.base.account.client.vo.AccountConsumeApiParamVo;
import com.dangdang.base.account.client.vo.AccountConsumeItemsSearchParamVo;
import com.dangdang.base.account.client.vo.AccountConsumeItemsVo;
import com.dangdang.base.account.client.vo.AccountInfoVo;
import com.dangdang.base.account.client.vo.AttachAccountItemsParamVo;
import com.dangdang.base.commons.enums.DeviceTypeEnum;
import com.dangdang.base.commons.utils.MD5Tool;
import com.dangdang.base.commons.vo.MessageVo;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.PayFromPaltform;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.Bought;
import com.dangdang.digital.model.ConsumerDeposit;
import com.dangdang.digital.model.UserMergeRecord;
import com.dangdang.digital.service.IBoughtService;
import com.dangdang.digital.service.IConsumerDepositService;
import com.dangdang.digital.service.IUserMergeRecordService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.vo.UserRradeInfoVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 用户相关service实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年12月3日 下午2:55:49 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class UserServiceImpl implements IUserService {

	@Resource
	private IAccountConsumeApi accountConsumeApi;
	@Resource
	private IAccountApi accountApi;
	@Resource
	private IConsumerDepositService consumerDepositService;
	@Resource
	private IBoughtService boughtService;
	@Resource
	private IUserMergeRecordService userMergeRecordService;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public UserTradeBaseVo tradeForUser(UserTradeBaseVo vo) throws Exception {
		vo.setTradeResult(false);
		if (vo.getTradeType() != null && vo.getCustId() != null) {
			if (vo.getTradeType() == Constans.TRADE_TYPE_QUERY) {
				AccountInfoVo accountInfoVo = null;
				try {
					accountInfoVo = accountApi.queryAccountInfoByCustId(vo.getCustId());
				} catch (Exception e) {
					e.printStackTrace();
					LogUtil.error(
							logger,
							"调用接口accountConsumeApi.queryAccountInfoByCustId查询用户余额失败！custId:{0}",
							vo.getCustId() + "");
				}
				if(accountInfoVo != null){
					vo.setMainBalance(accountInfoVo.getMasterAccountMoney());
					vo.setSubBalance(accountInfoVo.getAttachAccountMoney());
					vo.setMainBalanceIOS(accountInfoVo.getMasterAccountMoneyIOS());
					vo.setSubBalanceIOS(accountInfoVo.getAttachAccountMoneyIOS());
				}else{
					try {
						accountApi.createAccountInfo(vo.getCustId(), vo.getUsername());
					} catch (Exception e) {
						e.printStackTrace();
						LogUtil.error(
								logger,
								"调用接口accountConsumeApi.createAccountInfo创建账户信息失败！custId:{0}",
								vo.getCustId() + "");
					}
					vo.setMainBalance(0);
					vo.setSubBalance(0);
					vo.setAccountGrade(0);
					vo.setAccountIntegral(0);
				}
				vo.setTradeResult(true);			
			} else if (vo.getTradeType() == Constans.TRADE_TYPE_CONSUME
					|| vo.getTradeType() == Constans.TRADE_TYPE_RECHARGE) {
				MessageVo<AccountInfoVo> result = null;
				try {

					//若是给辅账户充值，走活动充值接口
					if(vo.getTradeType() == Constans.TRADE_TYPE_RECHARGE && 
							vo.getRequireRechargeSubAmount() != null && vo.getRequireRechargeSubAmount() > 0 
							&& StringUtils.isNotEmpty(vo.getActivityCode())){
						
						result = accountConsumeApi.bindSilverBell(convertToAttachAccountItemsParamVo(vo));
					}else{
						result = accountConsumeApi
								.accountConsumeInfo(convertToAccountConsumeApiParamVo(vo));
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
					LogUtil.error(logger,
							"调用接口accountConsumeApi.accountConsumeInfo用户交易失败！UserTradeBaseVo:{0}",
							JSON.toJSONString(vo));
				}
				if (result != null && result.isResult()) {
					convertToUserTradeBaseVo(result,vo);
					vo.setTradeResult(true);
				} else if (result == null || (result != null && !result.isResult())) {
					LogUtil.error(logger,
							"调用接口accountConsumeApi.accountConsumeInfo交易失败，result:{0},UserTradeBaseVo:{1}",
							JSON.toJSONString(result),JSON.toJSONString(vo));
					throw new MediaException(result.getErrorMessage());
				}
			}
		}
		return vo;
	}
	
	private AttachAccountItemsParamVo convertToAttachAccountItemsParamVo(
			UserTradeBaseVo vo) {
		AttachAccountItemsParamVo parameterVo = new AttachAccountItemsParamVo();
		StringBuffer paramterStr = new StringBuffer();
		
		String tradeConsumeSource = vo.getConsumeSource();
		String consumeSource = null;
		String deviceType = null;
		String activityCode = vo.getActivityCode();
		if(StringUtils.isNotBlank(tradeConsumeSource)){
			tradeConsumeSource = tradeConsumeSource.toLowerCase();
			if(tradeConsumeSource.indexOf("android") != -1){
				consumeSource = ConsumeSourceEnum.CONSUME_SOURCE_ANDROID.getConsumeSource();
				deviceType = DeviceTypeEnum.DEVICE_TYPE_ANDROID.getDeviceType();
				activityCode+="_android"; 
			}else if(tradeConsumeSource.indexOf("iphone") != -1){
				consumeSource = ConsumeSourceEnum.CONSUME_SOURCE_IOS.getConsumeSource();
				deviceType = DeviceTypeEnum.DEVICE_TYPE_IPHONE.getDeviceType();
				activityCode+="_ios"; 
			}else if(tradeConsumeSource.indexOf("ipad") != -1){
				consumeSource = ConsumeSourceEnum.CONSUME_SOURCE_IOS.getConsumeSource();
				deviceType = DeviceTypeEnum.DEVICE_TYPE_IPAD.getDeviceType();
				activityCode+="_ios"; 
			}
		}
		
		parameterVo.setActivityCode(activityCode);
		paramterStr.append(activityCode);
		
		parameterVo.setVersionNo("");
		parameterVo.setChannelId("");
		
		if(vo.getRequireRechargeSubAmount() != null && vo.getRequireRechargeSubAmount() > 0){
			parameterVo.setFaceValue(vo.getRequireRechargeSubAmount());
			paramterStr.append(parameterVo.getFaceValue()+"");
		}else {
			return new AttachAccountItemsParamVo();
		}
		
		parameterVo.setCustId(vo.getCustId());
		paramterStr.append(parameterVo.getCustId()+"");
		
		parameterVo.setUserName(vo.getUsername());
		
		parameterVo.setConsumeSource(consumeSource);
		paramterStr.append(consumeSource);
		
		parameterVo.setDeviceType(deviceType);
		paramterStr.append(deviceType);
		
		String fromPlatform = vo.getFromPaltform();
		if (StringUtils.isBlank(fromPlatform)
				|| PayFromPaltform.YC.getSource().equals(fromPlatform)
				|| PayFromPaltform.YC_ANDROID.getSource().equals(fromPlatform)
				|| PayFromPaltform.YC_IOS.getSource().equals(fromPlatform)) {
			parameterVo.setPlatformNo(PlatformNoEnum.PLATFORM_NO_MEDIA.getPlatformNo());
		}else if(PayFromPaltform.DS.getSource().equals(fromPlatform)
				|| PayFromPaltform.DS_ANDROID.getSource().equals(fromPlatform)
				|| PayFromPaltform.DS_IOS.getSource().equals(fromPlatform)){
			parameterVo.setPlatformNo(PlatformNoEnum.PLATFORM_NO_READER.getPlatformNo());
		}
		paramterStr.append(parameterVo.getPlatformNo());
		
		parameterVo.setSourceOrderNo(vo.getSourceOrderNo());
		paramterStr.append(parameterVo.getSourceOrderNo());
		
		parameterVo.setSignKey(MD5Tool.MD5Encode(paramterStr.toString()));
		return parameterVo;
	}

	private AccountConsumeApiParamVo convertToAccountConsumeApiParamVo(
			UserTradeBaseVo vo) {
		AccountConsumeApiParamVo parameterVo = new AccountConsumeApiParamVo();
		StringBuffer paramterStr = new StringBuffer();
		parameterVo.setCustId(vo.getCustId());
		paramterStr.append(parameterVo.getCustId());
		parameterVo.setUserName(vo.getUsername());
		paramterStr.append(parameterVo.getUserName());
		if(vo.getTradeType() == Constans.TRADE_TYPE_CONSUME){
			parameterVo.setConsumeMoney(vo.getRequirePayAmount());
			if(vo.isOnlyPayMainAmount()){
				parameterVo.setAccountType(AccountTypeEnum.ACCOUNT_TYPE_MASTER.getAccountType());
			}else{
				parameterVo.setAccountType(AccountTypeEnum.ACCOUNT_TYPE_ATTACH.getAccountType());
			}
		}else{
			if(vo.getRequireRechargeMainAmount() != null && vo.getRequireRechargeMainAmount() > 0){
				parameterVo.setConsumeMoney(vo.getRequireRechargeMainAmount());
				parameterVo.setAccountType(AccountTypeEnum.ACCOUNT_TYPE_MASTER.getAccountType());
			}else if(vo.getRequireRechargeSubAmount() != null && vo.getRequireRechargeSubAmount() > 0){
				parameterVo.setConsumeMoney(vo.getRequireRechargeSubAmount());
				parameterVo.setAccountType(AccountTypeEnum.ACCOUNT_TYPE_ATTACH.getAccountType());
			}
		}		
		paramterStr.append(parameterVo.getConsumeMoney());		
		paramterStr.append(parameterVo.getAccountType());
		if(vo.getTradeType() == Constans.TRADE_TYPE_CONSUME){
			parameterVo.setConsumeType(ConsumeTypeEnum.CONSUME_TYPE_CONSUME.getConsumeType());
		}else if(vo.getTradeType() == Constans.TRADE_TYPE_RECHARGE){
			parameterVo.setConsumeType(ConsumeTypeEnum.CONSUME_TYPE_TOP_UP.getConsumeType());
		}
		paramterStr.append(parameterVo.getConsumeType());
		if(ConsumeTypeEnum.CONSUME_TYPE_CONSUME.getConsumeType().equals(parameterVo.getConsumeType())){
			if(vo.isOnlyPayMainAmount()){
				parameterVo.setCostType(CostTypeEnum.COST_TYPE_MASTER.getCostType());
			}else{
				parameterVo.setCostType(CostTypeEnum.COST_TYPE_ATTACH_BEFORE_MASTER.getCostType());
			}
			paramterStr.append(parameterVo.getCostType());
		}
		String tradeConsumeSource = vo.getConsumeSource();
		String consumeSource = null;
		String deviceType = null;
		if(StringUtils.isNotBlank(tradeConsumeSource)){
			tradeConsumeSource = tradeConsumeSource.toLowerCase();
			if(tradeConsumeSource.indexOf("android") != -1){
				consumeSource = ConsumeSourceEnum.CONSUME_SOURCE_ANDROID.getConsumeSource();
				deviceType = DeviceTypeEnum.DEVICE_TYPE_ANDROID.getDeviceType();
			}else if(tradeConsumeSource.indexOf("iphone") != -1){
				consumeSource = ConsumeSourceEnum.CONSUME_SOURCE_IOS.getConsumeSource();
				deviceType = DeviceTypeEnum.DEVICE_TYPE_IPHONE.getDeviceType();
			}else if(tradeConsumeSource.indexOf("ipad") != -1){
				consumeSource = ConsumeSourceEnum.CONSUME_SOURCE_IOS.getConsumeSource();
				deviceType = DeviceTypeEnum.DEVICE_TYPE_IPAD.getDeviceType();
			}
		}
		parameterVo.setConsumeSource(consumeSource);
		paramterStr.append(consumeSource);
		parameterVo.setDeviceType(deviceType);
		paramterStr.append(deviceType);
		String fromPlatform = vo.getFromPaltform();
		if (StringUtils.isBlank(fromPlatform)
				|| PayFromPaltform.YC.getSource().equals(fromPlatform)
				|| PayFromPaltform.YC_ANDROID.getSource().equals(fromPlatform)
				|| PayFromPaltform.YC_IOS.getSource().equals(fromPlatform)) {
			parameterVo.setPlatformNo(PlatformNoEnum.PLATFORM_NO_MEDIA.getPlatformNo());
		}else if(PayFromPaltform.DS.getSource().equals(fromPlatform)
				|| PayFromPaltform.DS_ANDROID.getSource().equals(fromPlatform)
				|| PayFromPaltform.DS_IOS.getSource().equals(fromPlatform)){
			parameterVo.setPlatformNo(PlatformNoEnum.PLATFORM_NO_READER.getPlatformNo());
		}		
		paramterStr.append(parameterVo.getPlatformNo());
		parameterVo.setSourceOrderNo(vo.getSourceOrderNo());
		paramterStr.append(parameterVo.getSourceOrderNo());
		parameterVo.setSignKey(MD5Tool.MD5Encode(paramterStr.toString()));
		return parameterVo;
	}

	private void convertToUserTradeBaseVo(MessageVo<AccountInfoVo> messageVo,UserTradeBaseVo userTradeBaseVo) {
		if(messageVo != null && messageVo.getT() != null){
			AccountInfoVo vo = messageVo.getT();
			userTradeBaseVo.setMainBalance(vo.getMasterAccountMoney());
			userTradeBaseVo.setSubBalance(vo.getAttachAccountMoney());
			if(!userTradeBaseVo.isOnlyPayMainAmount() && StringUtils.isNotBlank(messageVo.getDesc())){
				String[] desc = messageVo.getDesc().split(",");
				if(desc.length == 2){
					userTradeBaseVo.setPayMainAmount(Integer.valueOf(desc[0].split(":")[1]));
					userTradeBaseVo.setPaySubAmount(Integer.valueOf(desc[1].split(":")[1]));
				}
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void mergeUserTradeInfo(Long oldCustId, Long newCustId) {
		//合并充值记录
		String consumerDepositId = consumerDepositService.mergeUserDeposit(oldCustId, newCustId,null);
		//合并已购显示页记录
		String boughtId = boughtService.mergeUserBought(oldCustId, newCustId,null);
		List<UserMergeRecord> result = userMergeRecordService.findMasterListByParams("oldCustId",oldCustId,"newCustId",newCustId);
		if(CollectionUtils.isEmpty(result)){
			userMergeRecordService.createUserMergeRecord(oldCustId, newCustId, consumerDepositId, boughtId);
		}else{
			UserMergeRecord userMergeRecord = result.get(0);
			userMergeRecord.setConsumerDepositId(consumerDepositId);
			userMergeRecord.setBoughtId(boughtId);
			userMergeRecord.setLastUpdateTime(new Date());
			userMergeRecordService.update(userMergeRecord);
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void rollbackForMergeUserTradeInfo(Long oldCustId, Long newCustId,
			String consumerDepositId, String boughtId) {
		//回滚合并充值记录
		consumerDepositService.mergeUserDeposit(newCustId, oldCustId, consumerDepositId);
		//回滚合并已购显示页记录
		boughtService.mergeUserBought(newCustId, oldCustId, boughtId);
		
	}

	@Override
	public UserRradeInfoVo findUserTradeInfo(Long custId) {
		UserRradeInfoVo vo = new UserRradeInfoVo();
		vo.setCustId(custId);
		if(custId == null || custId < 1){
			vo.setErrorMsg("参数错误，custId不合法");
			return vo;
		}
		List<ConsumerDeposit> consumeDepositList = null;
		try {
			consumeDepositList = consumerDepositService.findListByParams("custId",custId);
		} catch (Exception e) {
			e.printStackTrace();
			vo.setErrorMsg("数据库查询充值记录异常！custId:" + custId.toString());
			return vo;
		}
		if(!CollectionUtils.isEmpty(consumeDepositList)){
			vo.getConsumerDepositList().addAll(consumeDepositList);
		}
		List<Bought> boughtList = null;
		try {
			boughtList = boughtService.findListByParams("custId",custId);
		} catch (Exception e) {
			e.printStackTrace();
			vo.setErrorMsg("数据库查询已购信息异常！custId:" + custId.toString());
			return vo;
		}
		if(!CollectionUtils.isEmpty(boughtList)){
			vo.getBoughtList().addAll(boughtList);
		}
		vo.setResult(true);
		return vo;
	}
	
	@Override
	public List<AccountConsumeItemsVo> queryAccountConsumeItemsList(
			UserTradeBaseVo vo) {
		String consumeType = null;
		if (vo.getTradeType() != null && vo.getTradeType() == Constans.TRADE_TYPE_CONSUME) {
			consumeType = ConsumeTypeEnum.CONSUME_TYPE_CONSUME.getConsumeType();
		} else if (vo.getTradeType() != null && vo.getTradeType() == Constans.TRADE_TYPE_RECHARGE) {
			consumeType = ConsumeTypeEnum.CONSUME_TYPE_TOP_UP.getConsumeType();
		}
		Long lastConsumeItemId = vo.getLastConsumeItemId() != null ? vo
				.getLastConsumeItemId() : 0L;
		AccountConsumeItemsSearchParamVo searchVo = new AccountConsumeItemsSearchParamVo();
		String tradeConsumeSource = vo.getConsumeSource();
		String consumeSource = null;
		String deviceType = null;
		if(StringUtils.isNotBlank(tradeConsumeSource)){
			tradeConsumeSource = tradeConsumeSource.toLowerCase();
			if(tradeConsumeSource.indexOf("android") != -1){
				consumeSource = ConsumeSourceEnum.CONSUME_SOURCE_ANDROID.getConsumeSource();
				deviceType = DeviceTypeEnum.DEVICE_TYPE_ANDROID.getDeviceType();
			}else if(tradeConsumeSource.indexOf("iphone") != -1){
				consumeSource = ConsumeSourceEnum.CONSUME_SOURCE_IOS.getConsumeSource();
				deviceType = DeviceTypeEnum.DEVICE_TYPE_IPHONE.getDeviceType();
			}else if(tradeConsumeSource.indexOf("ipad") != -1){
				consumeSource = ConsumeSourceEnum.CONSUME_SOURCE_IOS.getConsumeSource();
				deviceType = DeviceTypeEnum.DEVICE_TYPE_IPAD.getDeviceType();
			}
		}
		searchVo.setConsumeSource(consumeSource);
		searchVo.setDeviceType(deviceType);
		searchVo.setConsumeType(consumeType);
		searchVo.setCustId(vo.getCustId());
		searchVo.setLastConsumeItemId(lastConsumeItemId);
		String fromPlatform = vo.getFromPaltform();
		if (PayFromPaltform.YC.getSource().equals(fromPlatform)
				|| PayFromPaltform.YC_ANDROID.getSource().equals(fromPlatform)
				|| PayFromPaltform.YC_IOS.getSource().equals(fromPlatform)) {
			searchVo.setPlatformNo(PlatformNoEnum.PLATFORM_NO_MEDIA.getPlatformNo());
		}else if(PayFromPaltform.DS.getSource().equals(fromPlatform)
				|| PayFromPaltform.DS_ANDROID.getSource().equals(fromPlatform)
				|| PayFromPaltform.DS_IOS.getSource().equals(fromPlatform)){
			searchVo.setPlatformNo(PlatformNoEnum.PLATFORM_NO_READER.getPlatformNo());
		}	
		searchVo.setSourceOrderNo(vo.getSourceOrderNo());
		return accountConsumeApi.queryAccountConsumeItemsListByWhere(searchVo);
	}
	

}
