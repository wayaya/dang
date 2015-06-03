/**
 * Description: ConsumerDepositApiImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 下午2:26:36  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.api.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.dangdang.digital.api.IConsumerDepositApi;
import com.dangdang.digital.constant.ActivityTypeEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PayFromPaltform;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.model.ConsumerDeposit;
import com.dangdang.digital.model.DsPayProduct;
import com.dangdang.digital.service.IActivityInfoService;
import com.dangdang.digital.service.IConsumerDepositService;
import com.dangdang.digital.service.IDsPayProductService;
import com.dangdang.digital.vo.CreateDepositVo;
import com.dangdang.digital.vo.DSDepositPayInfoVo;

/**
 * Description: 用户充值接口
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 下午2:26:36  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Component("consumerDepositApi")
public class ConsumerDepositApiImpl implements IConsumerDepositApi{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private IConsumerDepositService consumerDepositService;	
	@Resource
	private 
	IDsPayProductService dsPayProductService;
	@Resource
	private IActivityInfoService activityInfoService;
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Integer> masterRedisTemplate;
	
	@Override
	public void createAndSaveConsumerDeposit(ConsumerDeposit consumerDeposit)
			throws Exception {
		logger.error("用户充值开始！consumerDeposit：" + JSON.toJSONString(consumerDeposit));
		if(consumerDeposit == null){
			throw new MediaException("参数为空！");
		}
		if(consumerDeposit.getCustId() == null){
			throw new MediaException("参数[CustId]为空！");
		}
		if(StringUtils.isBlank(consumerDeposit.getDepositOrderNo())){
			throw new MediaException("参数[DepositOrderNo]为空！");		
		}
		if(consumerDeposit.getIsPaid() == null){
			throw new MediaException("参数[IsPaid]为空！");
		}
		if(consumerDeposit.getMoney() == null){
			throw new MediaException("参数[Money]为空！");
		}
		if(consumerDeposit.getMainGold() == null){
			throw new MediaException("参数[MainGold]为空！");
		}
		if(consumerDeposit.getPayment() == null){
			throw new MediaException("参数[Payment]为空！");
		}
		if(consumerDeposit.getPayTime() == null){
			throw new MediaException("参数[PayTime]为空！");
		}
		consumerDepositService.createAndSaveDepositOperation(consumerDeposit);
		logger.error("用户充值结束！consumerDeposit：" + JSON.toJSONString(consumerDeposit));
	}

	@Override
	public List<DSDepositPayInfoVo> getDSConsumerDepositPayInfo(
			String fromPaltform) throws MediaException{
		if(StringUtils.isBlank(fromPaltform) || PayFromPaltform.getBySource(fromPaltform) == null){
			throw new MediaException(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode() + "",ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage());
		}
		return activityInfoService.getDSConsumerDepositPayInfo(fromPaltform);
	}

	@Override
	public void createAndSaveConsumerDepositForMiss(
			CreateDepositVo createDepositVo) throws Exception {
		logger.error("调用充值扫漏接口开始，createDepositVo==" + JSON.toJSONString(createDepositVo));
		if(createDepositVo == null){
			throw new MediaException("参数为空！");
		}
		if(createDepositVo.getCustId() == null){
			throw new MediaException("参数[CustId]为空！");
		}
		if(StringUtils.isBlank(createDepositVo.getDepositOrderNo())){
			throw new MediaException("参数[DepositOrderNo]为空！");		
		}
		if(createDepositVo.getPayTime() == null){
			throw new MediaException("参数[PayTime]为空！");
		}
		if(createDepositVo.getRelationProductId() == null){
			throw new MediaException("参数[RelationProductId]为空！");
		}
		List<ConsumerDeposit> depositList = consumerDepositService.findMasterListByParams("depositOrderNo",createDepositVo.getDepositOrderNo());
		if(!CollectionUtils.isEmpty(depositList)){
			return;
		}
		this.createAndSaveConsumerDeposit(this.convertFromCreateDepositVo(createDepositVo));
		logger.error("调用充值扫漏接口结束，充值订单号==" + createDepositVo.getDepositOrderNo());
	}
	
	private ConsumerDeposit convertFromCreateDepositVo(CreateDepositVo createDepositVo) throws Exception {
		ConsumerDeposit consumerDeposit = new ConsumerDeposit();
		String[] productIds = createDepositVo.getRelationProductId().split(",");
		int money = 0;
		int mainGold = 0;
		int subGold = 0;
		int activityTypeId = 0;
		String fromPaltform = null;
		for (String productId : productIds) {
			boolean hasActivity = false;
			List<ActivityInfo> activityInfos = null;
			if (StringUtils.isBlank(createDepositVo.getPayment())) {
				activityInfos = activityInfoService.findListByParams(
						"withinPeriodValidity", 1, "status",
						Constans.ACTIVITYINFO_STATUS_VALID,
						"relationProductId", productId);
			} else {
				activityInfos = activityInfoService.findListByParams(
						"withinPeriodValidity", 1, "status",
						Constans.ACTIVITYINFO_STATUS_VALID,
						"relationProductId", productId, "activityTypeId",
						createDepositVo.getPayment());
			}
			if((ActivityTypeEnum.SMS_RECHARGE.getKey() + "").equals(createDepositVo.getPayment())){
				if(!CollectionUtils.isEmpty(activityInfos)){
					for(ActivityInfo activityInfo : activityInfos){
						if (activityInfo.getActivityTypeId() != null
								&& activityInfo.getActivityTypeId() == ActivityTypeEnum.SMS_RECHARGE.getKey()) {
							mainGold += activityInfo.getDepositReadPrice() != null ? activityInfo.getDepositReadPrice() : 0;
							subGold += activityInfo.getDepositGiftReadPrice() != null ? activityInfo.getDepositGiftReadPrice() : 0;
							money += activityInfo.getDepositMoney() != null ? activityInfo.getDepositMoney() : 0;
							activityTypeId = activityInfo.getActivityTypeId();
							fromPaltform = activityInfo.getFromPaltform();
							hasActivity = true;
							break;
						}
					}		
				}
			}else{
				if(!CollectionUtils.isEmpty(activityInfos)){
					for(ActivityInfo activityInfo : activityInfos){
						if (activityInfo.getActivityTypeId() != null
								&& (activityInfo.getActivityTypeId() == ActivityTypeEnum.ALIPAY_RECHARGE_1.getKey()
									|| activityInfo.getActivityTypeId() == ActivityTypeEnum.TENPAY_RECHARGE_1.getKey()
									|| activityInfo.getActivityTypeId() == ActivityTypeEnum.WECHAT_RECHARGE_1.getKey()
									|| activityInfo.getActivityTypeId() == ActivityTypeEnum.ALIPAY_RECHARGE.getKey()
									|| activityInfo.getActivityTypeId() == ActivityTypeEnum.TENPAY_RECHARGE.getKey()
									|| activityInfo.getActivityTypeId() == ActivityTypeEnum.WECHAT_RECHARGE.getKey()
									|| activityInfo.getActivityTypeId() == ActivityTypeEnum.IOS_RECHARGE.getKey() 
									|| activityInfo.getActivityTypeId() == ActivityTypeEnum.IPAD_RECHARGE.getKey())) {
							mainGold += activityInfo.getDepositReadPrice() != null ? activityInfo.getDepositReadPrice() : 0;
							subGold += activityInfo.getDepositGiftReadPrice() != null ? activityInfo.getDepositGiftReadPrice() : 0;
							money += activityInfo.getDepositMoney() != null ? activityInfo.getDepositMoney() : 0;
							activityTypeId = activityInfo.getActivityTypeId();
							fromPaltform = activityInfo.getFromPaltform();
							hasActivity = true;
							break;
						}
					}		
				}
			}			
			if (!hasActivity) {
				DsPayProduct dsPayProduct = dsPayProductService.findUniqueByParams("productId", productId);
				if (dsPayProduct == null) {
					throw new MediaException("获取不到充值虚拟商品，productId:"+productId);
				}
				int productMoney = dsPayProduct.getMoney() != null
						&& dsPayProduct.getMoney() > 0 ? dsPayProduct
						.getMoney() : 0;
				if((ActivityTypeEnum.SMS_RECHARGE.getKey() + "").equals(createDepositVo.getPayment())){
					productMoney = productMoney/2;
				}
				money += productMoney;
				mainGold += productMoney;
			}
		}
		if(money < 1){
			throw new MediaException("获取不到充值虚拟商品，orderNO:"+createDepositVo.getDepositOrderNo());
		}
		consumerDeposit.setSubGold(subGold);
		consumerDeposit.setMoney(money);
		consumerDeposit.setMainGold(mainGold);
		consumerDeposit.setGiveMainGold(0);
		consumerDeposit.setCreationDate(new Date());
		consumerDeposit.setCustId(createDepositVo.getCustId());
		consumerDeposit.setDepositOrderNo(createDepositVo.getDepositOrderNo());
		consumerDeposit.setFromPaltform(StringUtils.isBlank(fromPaltform) ? PayFromPaltform.DS_ANDROID.getSource() : fromPaltform);
		consumerDeposit.setLastModifiedDate(consumerDeposit.getLastModifiedDate());
		consumerDeposit.setPayment(activityTypeId < 1 ? (ActivityTypeEnum.ALIPAY_RECHARGE_1.getKey() + "") : (activityTypeId + ""));
		consumerDeposit
				.setIsPaid(consumerDeposit.getPayment().equals(
						ActivityTypeEnum.SMS_RECHARGE.getKey() + "") ? Constans.CONSUMER_DEPOSIT_ISPAID_CHECK_SUCCESS
						: Constans.CONSUMER_DEPOSIT_ISPAID_CHECK_NOT);
		consumerDeposit.setDeviceType(consumerDeposit.getFromPaltform());
		consumerDeposit.setPayTime(createDepositVo.getPayTime());
		consumerDeposit.setProductCount(1);
		consumerDeposit.setRelationProductId(createDepositVo.getRelationProductId());
		consumerDeposit.setUserName(createDepositVo.getUserName());
		return consumerDeposit;
	}

	@Override
	public List<String> getDSPayRelationProductIdByMoney(Integer money)
			throws MediaException {
		if(money == null || money < 1){
			throw new MediaException("参数不合法！");
		}
		return dsPayProductService.getDSPayRelationProductIdByMoney(money);
	}

	@Override
	public Set<String> getDepositRelationProduct(List<String> productIds)
			throws MediaException {
		if(CollectionUtils.isEmpty(productIds)){
			return new HashSet<String>(0);
		}
		return consumerDepositService.getDepositRelationProduct(productIds);
	}

	@Override
	public void rollbackForDeposit(String orderNo) {
		List<ConsumerDeposit> depositList = consumerDepositService.findMasterListByParams("depositOrderNo",orderNo);
		if(CollectionUtils.isEmpty(depositList)){
			return;
		}
		consumerDepositService.rollbackForDeposit(depositList.get(0));
	}

}
