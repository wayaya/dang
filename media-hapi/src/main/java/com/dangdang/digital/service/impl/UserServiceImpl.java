package com.dangdang.digital.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.dangdang.base.account.client.api.IAccountApi;
import com.dangdang.base.account.client.api.IAccountConsumeApi;
import com.dangdang.base.account.client.commons.enums.AccountTypeEnum;
import com.dangdang.base.account.client.commons.enums.ConsumeSourceEnum;
import com.dangdang.base.account.client.commons.enums.ConsumeTypeEnum;
import com.dangdang.base.account.client.commons.enums.CostTypeEnum;
import com.dangdang.base.account.client.commons.enums.PlatformNoEnum;
import com.dangdang.base.account.client.vo.AccountConsumeApiParamVo;
import com.dangdang.base.account.client.vo.AccountConsumeItemsSearchParamVo;
import com.dangdang.base.account.client.vo.AccountInfoVo;
import com.dangdang.base.account.client.vo.AttachAccountItemsParamVo;
import com.dangdang.base.commons.enums.DeviceTypeEnum;
import com.dangdang.base.commons.utils.MD5Tool;
import com.dangdang.base.commons.vo.MessageVo;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.PayFromPaltform;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 用户相关service实现类 
 * All Rights Reserved.
 * @version 1.0  2015年1月4日 上午10:18:22  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Service
public class UserServiceImpl implements IUserService {

	@Resource
	private IAccountConsumeApi accountConsumeApi;
	@Resource
	private IAccountApi accountApi;
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Integer> masterRedisTemplate;
	private DecimalFormat decimalFormat = new DecimalFormat("000000");
	private final String CREATE_TRADE_NO_SEQ = "create_trade_no_seq";

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public UserTradeBaseVo tradeForUser(UserTradeBaseVo vo) throws Exception {
		vo.setTradeResult(false);
		if (vo.getTradeType() != null && vo.getCustId() != null) {
			if (vo.getTradeType() == Constans.TRADE_TYPE_QUERY) {
				AccountInfoVo accountInfoVo = accountApi.queryAccountInfoByCustId(vo.getCustId());
				if(accountInfoVo != null){
					vo.setMainBalance(accountInfoVo.getMasterAccountMoney());
					vo.setSubBalance(accountInfoVo.getAttachAccountMoney());
					vo.setMainBalanceIOS(accountInfoVo.getMasterAccountMoneyIOS());
					vo.setSubBalanceIOS(accountInfoVo.getAttachAccountMoneyIOS());
				}else{
					accountApi.createAccountInfo(vo.getCustId(), vo.getUsername());
					vo.setMainBalance(0);
					vo.setSubBalance(0);
					vo.setAccountGrade(0);
					vo.setAccountIntegral(0);
				}
				vo.setTradeResult(true);			
			} else if (vo.getTradeType() == Constans.TRADE_TYPE_CONSUME
					|| vo.getTradeType() == Constans.TRADE_TYPE_RECHARGE) {
				
				MessageVo<AccountInfoVo> result = null;
				
				//若是给辅账户充值，走活动充值接口
				if(vo.getTradeType() == Constans.TRADE_TYPE_RECHARGE && 
						vo.getRequireRechargeSubAmount() != null && vo.getRequireRechargeSubAmount() > 0 
						&& StringUtils.isNotEmpty(vo.getActivityCode())){
					result = accountConsumeApi.bindSilverBell(convertToAttachAccountItemsParamVo(vo));
				}else{
					result = accountConsumeApi
							.accountConsumeInfo(convertToAccountConsumeApiParamVo(vo));
				}
				
				if (result != null && result.isResult()) {
					convertToUserTradeBaseVo(result,vo);
					vo.setTradeResult(true);
				} else if (result != null && !result.isResult()) {
					LogUtil.error(
							logger,
							"调用接口accountConsumeApi.accountConsumeInfo交易失败，errorCode{0},errorMessage{1}",
							result.getErrorCode(), result.getErrorMessage());
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
	public String createTradeNo(String prefix, Long custId) {
		if(StringUtils.isBlank(prefix)){
			return null;
		}
		String seqStr = null;
		String custIdSuffix = null;
		if (custId > 9) {
			custIdSuffix = custId.toString().substring(
					custId.toString().length() - 2);
		} else {
			custIdSuffix = "0" + custId;
		}
		try {
			long seq = masterRedisTemplate.opsForValue().increment(
					CREATE_TRADE_NO_SEQ, new Random().nextInt(10) + 1);
			if (seq < 100000) {
				seqStr = decimalFormat.format(seq);
			} else {
				seqStr = seq + "";
				seqStr = seqStr.substring(seqStr.length() - 6);
			}
		} catch (Exception e) {
			LogUtil.error(logger, e, "生成交易号时连接redis失败（key:create_trade_no_seq）");
			seqStr = System.currentTimeMillis() + "";
			seqStr = seqStr.substring(seqStr.length() - 6) + "_1";
		}
		return prefix + DateUtil.getDate(new Date(), "yyMMddHHmm") + custIdSuffix + seqStr;
	}
	
	
}
