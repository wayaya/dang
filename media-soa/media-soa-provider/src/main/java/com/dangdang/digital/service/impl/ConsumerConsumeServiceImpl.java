/**
 * Description: ConsumerConsumeServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:26:25  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.constant.ActivityTypeEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.dao.IActivityUserDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IConsumerConsumeDao;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.model.ConsumerConsume;
import com.dangdang.digital.model.ConsumerConsumeDetail;
import com.dangdang.digital.model.Prop;
import com.dangdang.digital.model.UserMonthly;
import com.dangdang.digital.service.IActivityInfoService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.IConsumerConsumeDetailService;
import com.dangdang.digital.service.IConsumerConsumeService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.service.IPropService;
import com.dangdang.digital.service.IUserMonthlyService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.vo.CreateConsumeVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 用户消费记录实体service实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年11月14日 上午10:26:25 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class ConsumerConsumeServiceImpl extends BaseServiceImpl<ConsumerConsume, Long> implements
		IConsumerConsumeService {

	@Resource
	IConsumerConsumeDao consumerConsumeDao;
	@Resource
	IActivityUserDao activityUserDao;
	@Resource
	IConsumerConsumeDetailService consumerConsumeDetailService;
	@Resource
	IActivityInfoService activityInfoService;
	@Resource
	private IMediaService mediaService;
	@Resource
	private IPropService propService;
	@Resource
	private IUserService userService;
	@Resource
	private ICacheService cacheService;
	@Resource
	private IUserMonthlyService userMonthlyService;
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Integer> masterRedisTemplate;
	private DecimalFormat decimalFormat = new DecimalFormat("000000");
	private final String CREATE_CONSUME_NO_SEQ = "create_consume_no_seq";

	@Override
	public IBaseDao<ConsumerConsume> getBaseDao() {
		return this.consumerConsumeDao;
	}

	@Override
	public ConsumerConsume findWithDetailByConsumerConsumeId(Long consumerConsumeId) {
		ConsumerConsume consume = this.get(consumerConsumeId);
		if (consume != null && consume.getConsumerConsumeId() != null) {
			consume.setConsumeDetails(consumerConsumeDetailService.findListByParams("consumerConsumeId",
					consume.getConsumerConsumeId()));
		}
		return consume;
	}

	@Override
	public ConsumerConsume encapsulateConsumerConsumeInfo(CreateConsumeVo createConsumeVo) throws Exception {
		// 设置回滚
		createConsumeVo.setRollbackPoint(Constans.CREATE_CONSUME_ROLLBACK_EOI);
		// 封装消费记录数据
		ConsumerConsume consume = new ConsumerConsume();
		consume.setSupportBalance(Short.valueOf("1"));
		if (!createConsumeVo.getActivityIds().isEmpty()) {
			StringBuffer activeIds = new StringBuffer();
			for (Integer activeId : createConsumeVo.getActivityIds()) {
				activeIds.append(activeId).append(",");
			}
			consume.setActiveId(activeIds.substring(0, activeIds.length() - 1));
		}
		consume.setConsumeTpye(createConsumeVo.getConsumeType());
		consume.setCustId(createConsumeVo.getCustId());
		consume.setFromPaltform(createConsumeVo.getFromPaltform());
		consume.setUserName(createConsumeVo.getUsername());
		consume.setConsumeSerialNo(createConsumeSerialNo(createConsumeVo.getCustId()));
		if (createConsumeVo.getConsumeType().intValue() == Integer.valueOf(Constans.BUY_FLAG_MONTHLY).intValue()) {
			// 购买包月
			consume.setIsFinalEstimate(Constans.CONSUME_NOTFINAL_ESTIMATE);
			consume.setMonthlyId(createConsumeVo.getMonthlyId());
			encapsulateMonthlyInfo(consume, createConsumeVo);
		} else {
			consume.setIsFinalEstimate(Constans.CONSUME_ISFINAL_ESTIMATE);
			if (createConsumeVo.getConsumeType().intValue() == Integer.valueOf(Constans.BUY_FLAG_PROP).intValue()) {
				encapsulatePropInfo(consume, createConsumeVo);
			} else if (createConsumeVo.getConsumeType().intValue() == Integer.valueOf(Constans.BUY_FLAG_REWARDS)
					.intValue()) {
				encapsulateRewardsInfo(consume, createConsumeVo);
			} else if (createConsumeVo.getConsumeType().intValue() == Integer.valueOf(Constans.BUY_FLAG_LUCKYBAG)
					.intValue()) {
				consume.setLuckyBagId(createConsumeVo.getLuckyBagId());
				encapsulateLuckybagInfo(consume, createConsumeVo);
			} else if(createConsumeVo.getConsumeType().intValue() == Integer.valueOf(Constans.BUY_FLAG_BALANCETRANSFER)
					.intValue()){
				encapsulateTransferAccountInfo(consume, createConsumeVo);
			} else {
				
				LogUtil.error(logger, "custId：{0}，消费类型未定义，consumeType：{1}", createConsumeVo.getCustId(),
						createConsumeVo.getConsumeType());
				throw new MediaException("消费类型未定义！");
			}
		}
		createConsumeVo.setConsumerConsume(consume);
		return consume;
	}

	@Override
	public boolean checkParamsForCreateConsumerConsume(ConsumerConsume consumerConsume) {

		return false;
	}

	@Override
	public String createConsumeSerialNo(Long custId) {
		String seqStr = null;
		String custIdSuffix = null;
		if (custId > 9) {
			custIdSuffix = custId.toString().substring(custId.toString().length() - 2);
		} else {
			custIdSuffix = "0" + custId;
		}
		try {
			long seq = masterRedisTemplate.opsForValue().increment(CREATE_CONSUME_NO_SEQ, new Random().nextInt(10) + 1);
			if (seq < 100000) {
				seqStr = decimalFormat.format(seq);
			} else {
				seqStr = seq + "";
				seqStr = seqStr.substring(seqStr.length() - 6);
			}
		} catch (Exception e) {
			LogUtil.error(logger, e, "生成消费流水号时连接redis失败（key:create_deposit_no_seq）");
			seqStr = System.currentTimeMillis() + "";
			seqStr = seqStr.substring(seqStr.length() - 6) + "_1";
		}
		return "CC" + DateUtil.getDate(new Date(), "yyMMddHHmm") + custIdSuffix + seqStr;
	}

	@Override
	public ConsumerConsume saveConsumerConsumeInfo(CreateConsumeVo createConsumeVo) throws Exception {
		// 设置回滚
		createConsumeVo.setRollbackPoint(Constans.CREATE_CONSUME_ROLLBACK_SOI);
		ConsumerConsume consume = createConsumeVo.getConsumerConsume();
		this.save(consume);
		for (ConsumerConsumeDetail detail : consume.getConsumeDetails()) {
			detail.setConsumerConsumeId(consume.getConsumerConsumeId());
			consumerConsumeDetailService.save(detail);
		}
		return consume;
	}
	/**
	 * 封装转账消费数据
	 * Description: 
	 * @Version1.0 2015年3月20日 下午7:01:16 by 魏嵩（weisong@dangdang.com）创建
	 * @param consume
	 * @param createConsumeVo
	 */
	private void encapsulateTransferAccountInfo(ConsumerConsume consume,
			CreateConsumeVo createConsumeVo) {
		consume.setMainGoldUsed(createConsumeVo.getRewardsMoney());
		consume.setPrePrice(0);
		consume.setSubGoldUsed(0);
		consume.setTotalPrice(createConsumeVo.getRewardsMoney());
		consume.setSupportBalance(Short.valueOf("0"));
	}

	/**
	 * 
	 * Description: 封装包月数据
	 * 
	 * @Version1.0 2014年12月5日 下午5:09:31 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param consume
	 * @param createConsumeVo
	 */
	private void encapsulateMonthlyInfo(ConsumerConsume consume, CreateConsumeVo createConsumeVo) throws Exception {
		ActivityInfo activityInfo = activityInfoService
				.findUniqueByParams("activityId", createConsumeVo.getMonthlyId());
		long now = new Date().getTime();
		if (activityInfo != null && ActivityTypeEnum.MONTHLY.getKey() == activityInfo.getActivityTypeId()
				&& now > activityInfo.getStartTime().getTime() && now < activityInfo.getEndTime().getTime()) {
			consume.setMainGoldUsed(activityInfo.getMonthlyPaymentPrice());
			consume.setTotalPrice(activityInfo.getMonthlyPaymentPrice());
			consume.setPrePrice(0);
			consume.setSubGoldUsed(0);
			consume.setCommodityName(activityInfo.getActivityName());
			int monthlyDays = 0;
			String monthlyPaymentRelation = StringUtils.isBlank(activityInfo.getMonthlyPaymentRelation()) ? null
					: activityInfo.getMonthlyPaymentRelation().trim();
			if (activityInfo.getMonthlyBuyDays() != null) {
				monthlyDays += activityInfo.getMonthlyBuyDays();
			}
			if (activityInfo.getMonthlyGiveDays() != null) {
				monthlyDays += Integer.valueOf(activityInfo.getMonthlyGiveDays());
			}
			UserMonthly userMonthly = userMonthlyService.findMasterUserMonthly(
					Short.valueOf(activityInfo.getMonthlyPaymentType()), createConsumeVo.getCustId(),
					monthlyPaymentRelation);
			if (userMonthly != null) {
				consume.setMonthlyStartTime(userMonthly.getMonthlyEndTime() != null
						&& userMonthly.getMonthlyEndTime().getTime() > now ? userMonthly.getMonthlyEndTime()
						: new Date(now));
				consume.setMonthlyEndTime(DateUtil.addDate(consume.getMonthlyStartTime(), monthlyDays));
				userMonthly.setOperateVersion(userMonthly.getOperateVersion() + 1);
				Map<String, Object> map = this.convertBeanToMap(userMonthly);
				map.put("monthlyDays", monthlyDays);
				int result = userMonthlyService.updateUserMonthly(map);
				if (result == 0) {
					throw new MediaException("更新数据库用户包月信息失败！");
				}
			} else {
				userMonthly = new UserMonthly();
				consume.setMonthlyStartTime(new Date(now));
				consume.setMonthlyEndTime(DateUtil.addDate(consume.getMonthlyStartTime(), monthlyDays));
				userMonthly.setCustId(createConsumeVo.getCustId());
				userMonthly.setMonthlyType(Short.valueOf(activityInfo.getMonthlyPaymentType()));
				userMonthly.setOperateVersion(1);
				userMonthly.setMonthlyPaymentRelation(monthlyPaymentRelation);
				userMonthly.setMonthlyStartTime(new Date());
				userMonthly.setMonthlyEndTime(DateUtils.addDays(userMonthly.getMonthlyStartTime(), monthlyDays));
				Map<String, Object> map = this.convertBeanToMap(userMonthly);
				map.put("monthlyDays", monthlyDays);
				int result = userMonthlyService.insertUserMonthly(map);
				if (result == 0) {
					throw new MediaException("插入数据库用户包月信息失败！");
				}
			}
			createConsumeVo.setUserMonthly(userMonthly);
		} else {
			LogUtil.error(logger, "custId：{0}，该包月类型不可购买！，monthlyId：{1}", createConsumeVo.getCustId(),
					createConsumeVo.getMonthlyId());
			throw new MediaException(ErrorCodeEnum.ERROR_CODE_18002.getErrorCode() + "",
					ErrorCodeEnum.ERROR_CODE_18002.getErrorMessage());
		}
	}

	/**
	 * 
	 * Description: 封装道具数据
	 * 
	 * @Version1.0 2014年12月5日 下午5:09:49 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param consume
	 * @param createConsumeVo
	 */
	private void encapsulatePropInfo(ConsumerConsume consume, CreateConsumeVo createConsumeVo) throws Exception {
		Map<Integer, Integer> propMap = createConsumeVo.getPropMap();
		if (!propMap.isEmpty()) {
			List<Integer> propIds = new ArrayList<Integer>();
			for (Integer propId : propMap.keySet()) {
				propIds.add(propId);
			}
			List<Prop> props = propService.findByIds(propIds);
			if (props != null && props.size() == propMap.size()) {
				Integer totalPrice = 0;
				for (Prop prop : props) {
					ConsumerConsumeDetail detail = new ConsumerConsumeDetail();
					detail.setPropCount(propMap.get(prop.getPropId()));
					detail.setMainGoldPrice(prop.getPropMainGoldPrice() * detail.getPropCount());
					detail.setPrePrice(0);
					detail.setPropId(prop.getPropId());
					detail.setPropName(prop.getPropName());
					detail.setSubGoldPrice(0);
					detail.setTotalPrice(detail.getMainGoldPrice());
					totalPrice += detail.getTotalPrice();
					consume.getConsumeDetails().add(detail);
				}
				consume.setMainGoldUsed(totalPrice);
				consume.setPrePrice(0);
				consume.setSubGoldUsed(0);
				consume.setTotalPrice(totalPrice);
			} else {
				LogUtil.error(logger, "custId：{0}，道具信息不存在，不可购买！", createConsumeVo.getCustId());
				throw new MediaException(ErrorCodeEnum.ERROR_CODE_18002.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_18002.getErrorMessage());
			}
		} else {
			LogUtil.error(logger, "custId：{0}，道具id为空，不可购买！", createConsumeVo.getCustId());
			throw new MediaException(ErrorCodeEnum.ERROR_CODE_18002.getErrorCode() + "",
					ErrorCodeEnum.ERROR_CODE_18002.getErrorMessage());
		}
	}

	/**
	 * 
	 * Description: 封装打赏数据
	 * 
	 * @Version1.0 2014年12月5日 下午5:10:05 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param consume
	 * @param createConsumeVo
	 */
	private void encapsulateRewardsInfo(ConsumerConsume consume, CreateConsumeVo createConsumeVo) throws Exception {
		consume.setMainGoldUsed(createConsumeVo.getRewardsMoney());
		consume.setPrePrice(0);
		consume.setSubGoldUsed(0);
		consume.setTotalPrice(createConsumeVo.getRewardsMoney());
		ConsumerConsumeDetail detail = new ConsumerConsumeDetail();
		detail.setMainGoldPrice(consume.getMainGoldUsed());
		detail.setMediaId(createConsumeVo.getMediaId());
		detail.setPrePrice(consume.getPrePrice());
		detail.setSubGoldPrice(consume.getSubGoldUsed());
		detail.setTotalPrice(consume.getTotalPrice());
		detail.setMediaName(cacheService.getMediaBasicFromCache(createConsumeVo.getMediaId()).getTitle());
		consume.getConsumeDetails().add(detail);
	}

	/**
	 * 
	 * Description: 封装购买福袋数据
	 * 
	 * @Version1.0 2015年1月4日 上午9:32:19 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param consume
	 * @param createConsumeVo
	 */
	private void encapsulateLuckybagInfo(ConsumerConsume consume, CreateConsumeVo createConsumeVo) throws Exception {
		ActivityInfo activityInfo = activityInfoService.findUniqueByParams("activityId",
				createConsumeVo.getLuckyBagId());
		long now = new Date().getTime();
		if (activityInfo != null && ActivityTypeEnum.LUCKY_BAG.getKey() == activityInfo.getActivityTypeId()
				&& now > activityInfo.getStartTime().getTime() && now < activityInfo.getEndTime().getTime()) {
			consume.setMainGoldUsed(activityInfo.getPrizePrice());
			consume.setTotalPrice(activityInfo.getPrizePrice());
			consume.setPrePrice(0);
			consume.setSubGoldUsed(0);
			consume.setCommodityName(activityInfo.getActivityName());
			//增加用户抽奖次数
			activityUserDao.addLotTime(consume.getCustId(), activityInfo.getPrizeQuantity(), 0,createConsumeVo.getUsername());
		} else {
			LogUtil.error(logger, "custId：{0}，该福袋不可购买！，luckyBagId：{1}", createConsumeVo.getCustId(),
					createConsumeVo.getLuckyBagId());
			throw new MediaException(ErrorCodeEnum.ERROR_CODE_18002.getErrorCode() + "",
					ErrorCodeEnum.ERROR_CODE_18002.getErrorMessage());
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void createAndSaveConsumeOperation(CreateConsumeVo createConsumeVo) throws Exception {
		try {
			// 封装消费记录数据
			this.encapsulateConsumerConsumeInfo(createConsumeVo);
			// 预处理(扣减阅读币，礼券，积分，礼品卡等占用)
			pretreatmentForCreateConsume(createConsumeVo);
			// 保存消费记录
			this.saveConsumerConsumeInfo(createConsumeVo);
			// 后处理(发消息异步处理：记录日志，赠送礼券，积分等)
			aftertreatmentForCreateConsume(createConsumeVo);
			// 更新用户信息
			updateUserInfoForCreateConsume(createConsumeVo);
		} catch (Exception e) {
			// 回滚操作
			rollbackForCreateConsume(createConsumeVo);
			throw e;
		}
	}

	/**
	 * 
	 * Description: 预处理(扣减阅读币，礼券，积分，礼品卡等占用)
	 * 
	 * @Version1.0 2014年11月26日 下午2:23:20 by 张宪斌（zhangxianbin@dangdang.com）创建
	 */
	private void pretreatmentForCreateConsume(CreateConsumeVo createConsumeVo) throws Exception {
		ConsumerConsume consumerConsume = createConsumeVo.getConsumerConsume();
		if (consumerConsume.getMainGoldUsed().intValue() < 1) {
			return;
		}
		// 用户账户扣减阅读币
		UserTradeBaseVo vo = new UserTradeBaseVo();
		vo.setTradeType(Constans.TRADE_TYPE_CONSUME);
		vo.setOnlyPayMainAmount(true);
		vo.setCustId(consumerConsume.getCustId());
		vo.setFromPaltform(createConsumeVo.getFromPaltform());
		vo.setRequirePayAmount(consumerConsume.getMainGoldUsed().intValue());
		vo.setConsumeSource(createConsumeVo.getDeviceType());
		vo.setSourceOrderNo(consumerConsume.getConsumeSerialNo());
		vo.setUsername(createConsumeVo.getUsername());
		UserTradeBaseVo resultVo = null;
		try {
			resultVo = userService.tradeForUser(vo);
			if (resultVo != null && resultVo.isTradeResult()) {
				// 设置回滚
				createConsumeVo.setRollbackPoint(Constans.CREATE_CONSUME_ROLLBACK_PFCO);
				consumerConsume.setPayTime(new Date());
			} else {
				LogUtil.error(logger, "创建消费记录时用户消费失败，custId：{0}", createConsumeVo.getCustId());
				throw new MediaException(ErrorCodeEnum.ERROR_CODE_18001.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_18001.getErrorMessage());
			}
		} catch (Exception e) {
			LogUtil.error(logger, e, "创建消费记录时调用用户交易接口失败，custId：{0}", createConsumeVo.getCustId());
			throw new MediaException(ErrorCodeEnum.ERROR_CODE_18001.getErrorCode() + "",
					ErrorCodeEnum.ERROR_CODE_18001.getErrorMessage());
		}
	}

	/**
	 * 
	 * Description: 更新用户相关信息
	 * 
	 * @Version1.0 2014年11月26日 下午2:31:50 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @throws Exception
	 */
	private void updateUserInfoForCreateConsume(CreateConsumeVo createConsumeVo) throws Exception {

	}

	/**
	 * 
	 * Description: 后处理(发消息异步处理：记录日志，权限绑定，赠送礼券，积分等)
	 * 
	 * @Version1.0 2014年11月26日 下午2:41:42 by 张宪斌（zhangxianbin@dangdang.com）创建
	 */
	private void aftertreatmentForCreateConsume(CreateConsumeVo createConsumeVo) throws Exception {

	}

	/**
	 * 
	 * Description: 异常回滚操作
	 * 
	 * @Version1.0 2014年11月26日 下午2:59:59 by 张宪斌（zhangxianbin@dangdang.com）创建
	 */
	private void rollbackForCreateConsume(CreateConsumeVo createConsumeVo) throws Exception {
		// 回滚用户阅读币扣减
		if (createConsumeVo.getRollbackPoint() >= Constans.CREATE_CONSUME_ROLLBACK_PFCO) {
			UserTradeBaseVo vo = new UserTradeBaseVo();
			vo.setTradeType(Constans.TRADE_TYPE_RECHARGE);
			vo.setCustId(createConsumeVo.getCustId());
			vo.setFromPaltform(createConsumeVo.getFromPaltform());
			vo.setRequireRechargeMainAmount(createConsumeVo.getConsumerConsume().getMainGoldUsed());
			vo.setConsumeSource(createConsumeVo.getDeviceType());
			vo.setSourceOrderNo(createConsumeVo.getConsumerConsume().getConsumeSerialNo());
			vo.setUsername(createConsumeVo.getUsername());
			UserTradeBaseVo resultVo = null;
			try {
				resultVo = userService.tradeForUser(vo);
				if (resultVo != null && resultVo.isTradeResult()) {
					LogUtil.info(logger, "用户扣减阅读币回滚操作成功！custId:{0}", createConsumeVo.getCustId());
				} else {
					LogUtil.error(logger, "用户扣减阅读币回滚操作失败！custId:{0}", createConsumeVo.getCustId());
					throw new MediaException("用户扣减阅读币回滚操作失败！");
				}
			} catch (Exception e) {
				LogUtil.error(logger, e, "用户扣减阅读币回滚操作调用接口失败！custId:{0}", createConsumeVo.getCustId());
				throw new MediaException("用户扣减阅读币回滚操作调用接口失败！");
			}
		}
		// 回滚更新用户信息操作

	}

	/**
	 * 
	 * Description: 获取其它消费列表
	 * 
	 * @Version1.0 2015年1月5日 上午9:40:04 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @param start
	 * @param count
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getOtherConsumeList(Long custId, Integer start, Integer count) {
		return consumerConsumeDao.getOtherConsumeList(custId, start, count);
	}
	
	@Override
	public Integer getOtherConsumeCount(Long custId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("custId", custId);
		return consumerConsumeDao.getCount(paramMap);
	}

}
