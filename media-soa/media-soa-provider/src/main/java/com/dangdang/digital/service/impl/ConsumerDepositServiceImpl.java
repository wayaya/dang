/**
 * Description: ConsumerDepositServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:27:32  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.dangdang.base.account.client.commons.enums.AccountTypeEnum;
import com.dangdang.base.account.client.commons.enums.ConsumeTypeEnum;
import com.dangdang.base.account.client.vo.AccountConsumeItemsVo;
import com.dangdang.digital.constant.ActivityTypeEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.RechargeActivityCodeEnum;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IConsumerDepositDao;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.model.ConsumerDeposit;
import com.dangdang.digital.service.IActivityInfoService;
import com.dangdang.digital.service.IConsumerDepositService;
import com.dangdang.digital.service.IDsPayProductService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 用户充值记录实体service实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:27:32  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class ConsumerDepositServiceImpl extends BaseServiceImpl<ConsumerDeposit,Long> implements
		IConsumerDepositService {
	@Resource
	private IActivityInfoService activityInfoService;
	@Resource
	private IDsPayProductService dsPayProductService;
	@Resource
	private IUserService userService;
	@Resource
	IConsumerDepositDao consumerDepositDao;
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Integer> masterRedisTemplate;
	private DecimalFormat decimalFormat = new DecimalFormat("000000");
	private final String CREATE_DEPOSIT_NO_SEQ = "create_deposit_no_seq";
	//充值结果：充值成功
	private static final int CONSUME_RESULT_SUCCESS = 1;
	@Override
	public IBaseDao<ConsumerDeposit> getBaseDao() {
		return this.consumerDepositDao;
	}

	@Override
	public void createAndSaveDepositInfo(ConsumerDeposit consumerDeposit)
			throws Exception {
		if(consumerDeposit.getSubGold() == null){
			consumerDeposit.setSubGold(0);			
		}
		consumerDeposit.setDepositNo(createDepositNo(consumerDeposit.getCustId()));
		int activeGive = 0;
		String activeId = "";
		String activeName = "";
		//首次充值送
		List<ActivityInfo> activityInfosTfts = activityInfoService
				.findListByParams("activityTypeId",
						ActivityTypeEnum.THE_FIRST_TIME_SEND.getKey(),"withinPeriodValidity","withinPeriodValidity");
		if(activityInfosTfts != null && activityInfosTfts.size() > 0){
			ActivityInfo activityInfoTfts = activityInfosTfts.get(0);
			if(checkFirstDeposit(consumerDeposit.getCustId())){
				activeGive += (activityInfoTfts.getGiveGoldPiece() != null ? activityInfoTfts.getGiveGoldPiece() : 0);
				activeId += activityInfoTfts.getActivityId() + ",";
				activeName += activityInfoTfts.getActivityName() + ",";
			}
		}
		//前N个充值送
		List<ActivityInfo> activityInfosTfns = activityInfoService
				.findListByParams("activityTypeId",
						ActivityTypeEnum.THE_FIRST_N_SEND.getKey(),"withinPeriodValidity","withinPeriodValidity");
		if(activityInfosTfns != null && activityInfosTfns.size() > 0){
			ActivityInfo activityInfoTfns = activityInfosTfns.get(0);
			if(checkPreviousNumber(activityInfoTfns.getIsPreviousNumber())){
				int giveScale = (activityInfoTfns.getGiveScale() != null ? activityInfoTfns.getGiveScale() : 0);
				activeGive += (consumerDeposit.getMainGold()*giveScale/100);
				activeId += activityInfoTfns.getActivityId() + ",";
				activeName += activityInfoTfns.getActivityName() + ",";
			}
		}
		if(StringUtils.isNotBlank(activeId)){
			consumerDeposit.setActiveId(activeId.substring(0, activeId.length()-1));
			consumerDeposit.setActiveName(activeName.substring(0, activeName.length()-1));
			consumerDeposit.setSubGold(consumerDeposit.getSubGold() + activeGive);
		}
		this.save(consumerDeposit);
		
	}
	
	/**
	 * 
	 * Description: 是否是前n次充值
	 * @Version1.0 2014年12月8日 下午6:32:06 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param previousNumber
	 * @return
	 */
	private boolean checkPreviousNumber(Integer previousNumber){
		int result = (Integer)consumerDepositDao.getSqlSessionTemplate().selectOne("ConsumerDepositMapper.getTopN", previousNumber);
		return result < previousNumber.intValue();
	}
	/**
	 * 
	 * Description: 检查是否首次充值
	 * @Version1.0 2014年12月9日 上午11:04:28 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	private boolean checkFirstDeposit(Long custId){
		int result = (Integer)consumerDepositDao.getSqlSessionTemplate().selectOne("ConsumerDepositMapper.getFirst", custId);
		return result == 0;
	}

	@Override
	public String createDepositNo(Long custId) {
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
					CREATE_DEPOSIT_NO_SEQ, new Random().nextInt(10) + 1);
			if (seq < 100000) {
				seqStr = decimalFormat.format(seq);
			} else {
				seqStr = seq + "";
				seqStr = seqStr.substring(seqStr.length() - 6);
			}
		} catch (Exception e) {
			LogUtil.error(logger, e, "生成充值号时连接redis失败（key:create_deposit_no_seq）");
			seqStr = System.currentTimeMillis() + "";
			seqStr = seqStr.substring(seqStr.length() - 6) + "_1";
		}
		return "CD" + DateUtil.getDate(new Date(), "yyMMddHHmm") + custIdSuffix + seqStr;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void createAndSaveDepositOperation(ConsumerDeposit consumerDeposit) throws Exception{
		//保存充值记录数据
		this.createAndSaveDepositInfo(consumerDeposit);
		//充值
		UserTradeBaseVo vo = new UserTradeBaseVo();
		vo.setTradeType(Constans.TRADE_TYPE_RECHARGE);
		vo.setCustId(consumerDeposit.getCustId());
		vo.setConsumeSource(consumerDeposit.getDeviceType());
		vo.setSourceOrderNo(consumerDeposit.getDepositNo());
		vo.setUsername(consumerDeposit.getUserName());
		vo.setFromPaltform(consumerDeposit.getFromPaltform());
		try {
			//主账户充值
			if(consumerDeposit.getMainGold() != null && consumerDeposit.getMainGold() > 0){
				vo.setRequireRechargeMainAmount(consumerDeposit.getMainGold());
				vo.setRequireRechargeSubAmount(0);
				userService.tradeForUser(vo);
				if(!vo.isTradeResult()){
					LogUtil.error(logger, "创建充值记录失败，主账户充值，consumerDeposit：{0}",JSON.toJSONString(consumerDeposit));
					throw new MediaException(ErrorCodeEnum.ERROR_CODE_18001.getErrorCode()+"",ErrorCodeEnum.ERROR_CODE_18001.getErrorMessage());
				}
			}	
			//副账户充值
			if(consumerDeposit.getSubGold() != null && consumerDeposit.getSubGold() > 0){
				vo.setRequireRechargeMainAmount(0);
				vo.setRequireRechargeSubAmount(consumerDeposit.getSubGold());
				//送辅账户的钱加上有效期，set活动码added by weisong
				vo.setActivityCode(RechargeActivityCodeEnum.CHONGJIN_SONGYIN.getCode());
				userService.tradeForUser(vo);
				if(!vo.isTradeResult()){
					LogUtil.error(logger, "创建充值记录失败，副账户充值，consumerDeposit：{0}",JSON.toJSONString(consumerDeposit));
					throw new MediaException(ErrorCodeEnum.ERROR_CODE_18001.getErrorCode()+"",ErrorCodeEnum.ERROR_CODE_18001.getErrorMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			consumerDeposit.setIsPaid(Constans.CONSUMER_DEPOSIT_ISPAID_DEPOSIT_EXCEPTION);
			if (consumerDeposit.getConsumerDepositId() != null) {
				this.update(consumerDeposit);
			}
			LogUtil.error(logger, e,"创建充值记录时调用用户交易接口失败，consumerDeposit：{0}",JSON.toJSONString(consumerDeposit));
		}
	}

	@Override
	public String mergeUserDeposit(Long oldCustId, Long newCustId,String consumerDepositId) {
		if(StringUtils.isBlank(consumerDepositId)){
			List<ConsumerDeposit> result = this.findMasterListByParams("custId",oldCustId);
			if(CollectionUtils.isEmpty(result)){
				return "";
			}
			StringBuffer consumerDepositIdStr = new StringBuffer();
			for(ConsumerDeposit consumerDeposit : result){
				consumerDepositIdStr.append(consumerDeposit.getConsumerDepositId()).append(",");
			}
			consumerDepositId = consumerDepositIdStr.substring(0, consumerDepositIdStr.length() - 1);
		}
		
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("oldCustId", oldCustId);
		parameter.put("newCustId", newCustId);
		parameter.put("ids", consumerDepositId);
		this.getBaseDao().update("ConsumerDepositMapper.mergeUserDeposit", parameter);
		return consumerDepositId;		
	}

	@Override
	public Set<String> getDepositRelationProduct(List<String> productIds) {
		Set<String> relationProductIds = new HashSet<String>();
		List<String> activityProductIds = null;
		try {
			activityProductIds = activityInfoService.getDepositRelationProduct(productIds);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("1.查询订单购买的商品是否为虚拟充值商品失败！productIds=" + JSON.toJSONString(productIds));
		}
		if(!CollectionUtils.isEmpty(activityProductIds)){
			relationProductIds.addAll(activityProductIds);
		}
		if(productIds.size() != relationProductIds.size()){
			List<String> dsPayProductIds = null;
			try {
				dsPayProductIds = dsPayProductService.getDepositRelationProduct(productIds);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("2.查询订单购买的商品是否为虚拟充值商品失败！productIds=" + JSON.toJSONString(productIds));
			}	
			if(!CollectionUtils.isEmpty(dsPayProductIds)){
				relationProductIds.addAll(dsPayProductIds);
			}
		}
		return relationProductIds;
	}
	
	@Override
	public void rollbackForDeposit(ConsumerDeposit consumerDeposit){	
		//充值回滚
		UserTradeBaseVo vo = new UserTradeBaseVo();
		vo.setTradeType(Constans.TRADE_TYPE_CONSUME);
		vo.setCustId(consumerDeposit.getCustId());
		vo.setConsumeSource(consumerDeposit.getDeviceType());
		vo.setSourceOrderNo(consumerDeposit.getDepositNo());
		vo.setUsername(consumerDeposit.getUserName());
		vo.setFromPaltform(consumerDeposit.getFromPaltform());
		try {
			//查询充值明细
			UserTradeBaseVo queryVo = new UserTradeBaseVo();
			queryVo.setSourceOrderNo(consumerDeposit.getDepositNo());
			queryVo.setCustId(consumerDeposit.getCustId());
			queryVo.setConsumeSource(consumerDeposit.getDeviceType());
			Map<String, Integer> changeMoneyMap = calculateChangeMoney(userService.queryAccountConsumeItemsList(queryVo));
			//主账户充值回滚
			if(consumerDeposit.getMainGold() != null && consumerDeposit.getMainGold() > 0){
				vo.setOnlyPayMainAmount(true);
				vo.setRequirePayAmount(changeMoneyMap.get("changeMainMoney"));
				userService.tradeForUser(vo);
				if(!vo.isTradeResult()){
					LogUtil.error(logger, "充值回滚失败，主账户充值回滚，consumerDeposit：{0}",JSON.toJSONString(consumerDeposit));
					throw new MediaException(ErrorCodeEnum.ERROR_CODE_18001.getErrorCode()+"",ErrorCodeEnum.ERROR_CODE_18001.getErrorMessage());
				}
			}	
			//副账户充值回滚
			if(consumerDeposit.getSubGold() != null && consumerDeposit.getSubGold() > 0){
				vo.setOnlyPayMainAmount(false);
				vo.setRequirePayAmount(changeMoneyMap.get("changeSubMoney"));
				userService.tradeForUser(vo);
				if(!vo.isTradeResult()){
					LogUtil.error(logger, "充值回滚失败，副账户充值回滚，consumerDeposit：{0}",JSON.toJSONString(consumerDeposit));
					throw new MediaException(ErrorCodeEnum.ERROR_CODE_18001.getErrorCode()+"",ErrorCodeEnum.ERROR_CODE_18001.getErrorMessage());
				}
			}
			consumerDeposit.setIsPaid(Constans.CONSUMER_DEPOSIT_ISPAID_DEPOSIT_CANCEL);
			this.update(consumerDeposit);
		} catch (Exception e) {
			e.printStackTrace();
			consumerDeposit.setIsPaid(Constans.CONSUMER_DEPOSIT_ISPAID_ROLLBACK_FAIL);
			this.update(consumerDeposit);
			LogUtil.error(logger, e,"充值回滚时调用用户交易接口失败，consumerDeposit：{0}",JSON.toJSONString(consumerDeposit));
		}
	}
	
	/**
	 * 
	 * Description: 获取充值明细汇总金额
	 * @Version1.0 2015年1月14日 下午2:51:50 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param accountConsumes
	 * @return
	 */
	@Override
	public Map<String, Integer> calculateChangeMoney(List<AccountConsumeItemsVo> accountConsumes){
		Map<String, Integer> changeMoneyMap = new HashMap<String, Integer>();
		//计算充值明细充值总金额
		int depositMainAccount = 0;
		int depositSubAccount = 0;
		if(!CollectionUtils.isEmpty(accountConsumes)){
			for(AccountConsumeItemsVo accountConsume : accountConsumes){
				//消费明细
				if (accountConsume.getConsumeResult() != null
						&& accountConsume.getConsumeResult() == CONSUME_RESULT_SUCCESS
						&& StringUtils.equals(accountConsume.getConsumeType(),
								ConsumeTypeEnum.CONSUME_TYPE_CONSUME
										.getConsumeType())) {
					//主账户
					if(StringUtils.equals(accountConsume.getAccountType(),
							AccountTypeEnum.ACCOUNT_TYPE_MASTER
							.getAccountType())){
						depositMainAccount = depositMainAccount - accountConsume.getConsumeMoney();
					}
					//副账户
					if(StringUtils.equals(accountConsume.getAccountType(),
							AccountTypeEnum.ACCOUNT_TYPE_ATTACH
							.getAccountType())){
						depositSubAccount = depositSubAccount - accountConsume.getConsumeMoney();
					}
				}
				//充值明细
				if (accountConsume.getConsumeResult() != null
						&& accountConsume.getConsumeResult() == CONSUME_RESULT_SUCCESS
						&& StringUtils.equals(accountConsume.getConsumeType(),
								ConsumeTypeEnum.CONSUME_TYPE_TOP_UP
										.getConsumeType())) {
					//主账户
					if(StringUtils.equals(accountConsume.getAccountType(),
							AccountTypeEnum.ACCOUNT_TYPE_MASTER
							.getAccountType())){
						depositMainAccount = depositMainAccount + accountConsume.getConsumeMoney();
					}
					//副账户
					if(StringUtils.equals(accountConsume.getAccountType(),
							AccountTypeEnum.ACCOUNT_TYPE_ATTACH
							.getAccountType())){
						depositSubAccount = depositSubAccount + accountConsume.getConsumeMoney();
					}
				}
			}
		}
		changeMoneyMap.put("changeMainMoney", depositMainAccount);
		changeMoneyMap.put("changeSubMoney", depositSubAccount);
		return changeMoneyMap;
	}
	
	
}
