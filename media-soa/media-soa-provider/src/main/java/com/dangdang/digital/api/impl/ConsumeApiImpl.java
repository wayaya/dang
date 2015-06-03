/**
 * Description: ConsumeApiImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月29日 下午5:09:19  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.api.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.IConsumeApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.service.IActivityInfoService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.IConsumerConsumeService;
import com.dangdang.digital.service.IUserMonthlyService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.vo.ConsumerConsumeShowVo;
import com.dangdang.digital.vo.CreateConsumeVo;
import com.dangdang.digital.vo.UserMonthlyCacheVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 消费记录api All Rights Reserved.
 * 
 * @version 1.0 2014年11月29日 下午5:09:19 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Component("consumeApi")
public class ConsumeApiImpl implements IConsumeApi {
	@Resource
	private IActivityInfoService activityInfoService;
	@Resource
	private IUserService userService;
	@Resource
	private IConsumerConsumeService consumerConsumeService;
	@Resource
	private IUserMonthlyService userMonthlyService;
	@Resource
	private ICacheService cacheService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public CreateConsumeVo createAndSaveConsume(CreateConsumeVo createConsumeVo) throws Exception {
		try {
			consumerConsumeService.createAndSaveConsumeOperation(createConsumeVo);
			// 更新用户包月信息缓存
			if (Constans.BUY_FLAG_MONTHLY.equals(createConsumeVo.getConsumeType().toString())
					&& createConsumeVo.getUserMonthly() != null) {
				UserMonthlyCacheVo userMonthlyCacheVo = new UserMonthlyCacheVo();
				userMonthlyCacheVo.getUserMonthlys().put(createConsumeVo.getUserMonthly().getMonthlyPaymentRelation(),
						createConsumeVo.getUserMonthly());
				cacheService.updateUserMonthlyCacheVo(userMonthlyCacheVo);
			}
			// 更新用户抽奖次数缓存
			if (Constans.BUY_FLAG_LUCKYBAG.equals(createConsumeVo.getConsumeType().toString())
					&& createConsumeVo.getLuckyBagId() != null) {
				cacheService.deleteActivityUserVoCache(createConsumeVo.getCustId());
			}
		} catch (MediaException me) {
			createConsumeVo.setErrorCode(me.getErrorCode());
			createConsumeVo.setErrorMsg(me.getErrorMsg());
			throw me;
		} catch (Exception e) {
			LogUtil.error(logger, e, "");
			createConsumeVo.setErrorCode(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode() + "");
			createConsumeVo.setErrorMsg(ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage());
			throw e;
		}
		return createConsumeVo;
	}

	@Override
	public ConsumerConsumeShowVo getConsumerConsumeShowVoByCustId(Long custId, String buyFlag,String userName) throws Exception {
		ConsumerConsumeShowVo consumerConsumeShowVo = new ConsumerConsumeShowVo();
		// 获取主副账户信息
		UserTradeBaseVo vo = new UserTradeBaseVo();
		vo.setCustId(custId);
		vo.setUsername(userName);
		vo.setTradeType(Constans.TRADE_TYPE_QUERY);
		UserTradeBaseVo resultVo = null;
		try {
			resultVo = userService.tradeForUser(vo);
			if (resultVo == null || !resultVo.isTradeResult()) {
				throw new MediaException(ErrorCodeEnum.ERROR_CODE_18005.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_18005.getErrorMessage());
			}
			consumerConsumeShowVo.setMainBalance(resultVo.getMainBalance());
			consumerConsumeShowVo.setSubBalance(resultVo.getSubBalance());
			consumerConsumeShowVo.setMainBalanceIOS(resultVo.getMainBalanceIOS());
			consumerConsumeShowVo.setSubBalanceIOS(resultVo.getSubBalanceIOS());
		} catch (Exception e) {
			LogUtil.error(logger, e, "");
			throw new MediaException(ErrorCodeEnum.ERROR_CODE_18005.getErrorCode() + "",
					ErrorCodeEnum.ERROR_CODE_18005.getErrorMessage());
		}
		if (Constans.BUY_FLAG_MONTHLY.equals(buyFlag)) {
			// 获取包月购买列表
			consumerConsumeShowVo.setActivityInfos(activityInfoService.getMonthlyForBuyView());
		} else if (Constans.BUY_FLAG_LUCKYBAG.equals(buyFlag)) {
			// 获取福袋购买列表
			consumerConsumeShowVo.setActivityInfos(activityInfoService.getBuyLuckyBag());
		}
		return consumerConsumeShowVo;
	}

	@Override
	public List<Map<String, Object>> getOtherConsumeList(Long custId, Integer start, Integer count) {
		return consumerConsumeService.getOtherConsumeList(custId, start, count);
	}

	@Override
	public Integer getOtherConsumeCount(Long custId) {
		return consumerConsumeService.getOtherConsumeCount(custId);
	}

}
