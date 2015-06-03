package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.api.IUserAuthorityApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.RechargeActivityCodeEnum;
import com.dangdang.digital.dao.IActivityRecordCacheDao;
import com.dangdang.digital.dao.IActivityRecordDao;
import com.dangdang.digital.dao.IActivityUserCacheDao;
import com.dangdang.digital.dao.IActivityUserDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ILotteryDao;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.ActivityRecord;
import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.model.Bought;
import com.dangdang.digital.model.Prize;
import com.dangdang.digital.model.UserAuthority;
import com.dangdang.digital.model.UserAuthorityDetail;
import com.dangdang.digital.service.IActivityRecordService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.ILotteryService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.vo.AddBoughtMessage;
import com.dangdang.digital.vo.AddBoughtMessage.BoughtTypeEnum;
import com.dangdang.digital.vo.BindUserMediaAuthorityResultVo;
import com.dangdang.digital.vo.BindUserMediaAuthorityVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 抽奖service接口的实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年12月8日 上午10:34:21 by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Service
public class LotteryServiceImpl extends BaseServiceImpl<Prize, Long> implements ILotteryService {

	private static final Logger logger = Logger.getLogger(LotteryServiceImpl.class);

	@Resource
	private ILotteryDao lotteryDao;
	@Resource
	private ICacheService cacheService;
	@Resource
	private IActivityUserDao activityUserDao;
	@Resource
	private IActivityUserCacheDao activityUserCacheDao;
	@Resource
	private IActivityRecordDao activityRecordDao;
	@Resource
	private IActivityRecordService activityRecordService;
	@Resource
	private IUserAuthorityApi userAuthorityApi;
	@Resource
	private IUserService userService;
	@Resource
	private RabbitTemplate rabbitTemplate;
	@Resource
	private ICacheApi cacheApi;
	@Resource
	private IActivityRecordCacheDao activityRecordCacheDao;
	@Resource private ISystemApi systemApi;
	

	@Override
	public IBaseDao<Prize> getBaseDao() {
		return lotteryDao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> lotPrize(Long custId,String username,String nickname, int vestType, String deviceType) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String result = "";
		Prize lottedPrize = new Prize();
		List<Prize> prizeList = new ArrayList<Prize>();
		List<Prize> resultList = new ArrayList<Prize>();
		// 获取用的抽奖次数
		ActivityUser user = new ActivityUser();
		user = activityUserDao.selectUserByCustID(custId);
		if (null == user) {
			// 用户不存在。初始化一条
			user = new ActivityUser();
			user.setCustId(custId);
			user.setCreationDate(new Date());
			user.setUsername(nickname);
			user.setLotTimes(0);
			activityUserDao.insert(user);
		}
		int chances = user.getLotTimes();
		if (chances <= 0) {
			result = "no_chances";
			resultMap.put("result", result);
			return resultMap;
		}
		// 获取有效的奖品list(这是第一版)
		prizeList = cacheService.getPrizeListCache(1);
		if (null == prizeList || prizeList.size() < 3) {
			// 有效奖品不足3个。
//			result = "prize_size_short_of_3";
//			resultMap.put("result", result);
//			return resultMap;
			//手动增加3个！
			Prize prize = new Prize();
			//重新封装 lottedPrize
			prize = getNewPrize(prize);
			
			prizeList.add(prize);
			prizeList.add(prize);
			prizeList.add(prize);
			
		}
		/**
		 * 需求有变，暂时去掉 // 判定是否是第一次抽奖 if(activityRecordDao.judgeFirstLot(custId)){
		 * //给第一次抽奖的奖励。 //如果没有看书记录，从配置中随机送一本书 result = "first_lot";
		 * lottedPrize.setPrizeId(23794L); lottedPrize.setPrizeName("绝世天魔");
		 * lottedPrize.setPrizeType(2); lottedPrize.setAmount(50);
		 * resultMap.put("result", result);
		 * 
		 * }else {
		 */
		//上边那个版本作废了，又有新的。
		// 不是第一次抽奖
		// 获取抽中的奖品
		lottedPrize = getLottedPrize(prizeList);
		if(null==lottedPrize){
			lottedPrize = new Prize();
			lottedPrize = getNewPrize(lottedPrize);
		}
		// 判定日限
		int dayLimit = lottedPrize.getDayLimit();
		if (dayLimit > 0) {
			int dayPuts = activityRecordDao.selectPrizeTodayPutCount(lottedPrize.getPrizeId());
			if (dayPuts >= dayLimit) {
				// 超过日限
				result = "day_limit";
				resultMap.put("result", result);
				return resultMap;
			}
		}
		// 判定总限
		int totalLimit = lottedPrize.getTotalLimit();
		if (totalLimit > 0) {
			int totalPuts = activityRecordDao.selectPrizeTotalPutCount(lottedPrize.getPrizeId());
			if (totalPuts >= totalLimit) {
				// 超过总限
				result = "total_limit";
				resultMap.put("result", result);
				return resultMap;
			}
		}
		// 增加一条dblog 抽奖记录
		ActivityRecord record = new ActivityRecord(1, lottedPrize.getPrizeType(), custId, nickname,
				lottedPrize.getPrizeId(), lottedPrize.getPrizeName(), lottedPrize.getAmount(),
				lottedPrize.getVestType(), new Date());
		
		// 发奖
		String grantResult = grantPirze(custId, username, deviceType, lottedPrize);
		if (!"success".equals(grantResult)) {
			//改逻辑了，除了发金币的奖励，其他类型奖励发送失败直接给钱
			if("fail_money".equals(grantResult)){
				resultMap.put("result", grantResult);
				return resultMap;
			}
			
			//获取默认的钱
			int coins  = Integer.parseInt(systemApi.getProperty("default_prize_coins","50"));
			putCoins(custId, username, deviceType, lottedPrize.getPrizeId(), coins);
			//重新封装record
			record.setPrizeType(1);
			record.setPrizeName(coins+"银铃铛");
			record.setAmount(coins);
			//重新封装 lottedPrize
			lottedPrize.setAmount(coins);
			lottedPrize.setPrizeName(coins+"银铃铛");
			lottedPrize.setPrizeType(1);
			lottedPrize.setVestType(1);
		}
		if (null != lottedPrize.getPrizeType() && lottedPrize.getPrizeType() == 2) {
			MediaCacheBasicVo mediaVo = new MediaCacheBasicVo();
			mediaVo = cacheApi.getMediaBasicFromCache(lottedPrize.getPrizeId());
			Long saleId = mediaVo.getSaleId();
			lottedPrize.setPrizeId(saleId);
			record.setPrizeId(saleId);
		}
		activityRecordService.save(record);
		// 更新user信息 减少一次抽奖次数，增加一次使用次数
		activityUserDao.addLotTime(custId, -1, 1, nickname);
		lottedPrize.setMediaLotteryPrizeId(record.getMediaActivityRecordId());
		
		// 增加抽中的奖品
		resultList.add(lottedPrize);
		// 增加两个随机的奖品【可用的】
		prizeList.remove(lottedPrize);
		resultList = addRandomPrize(prizeList, resultList);

		resultMap.put("result", "success");
		resultMap.put("prizeList", resultList);
		activityUserCacheDao.deleteActivityUserVoCache(custId);
		activityRecordCacheDao.deleteUserActivityRecordCache(custId, 0);
		activityRecordCacheDao.deleteUserActivityRecordCache(custId, 1);
		return resultMap;
	}

	/**
	 * Description: 从奖品列表中获取抽中的奖品
	 * 
	 * @Version1.0 2014年12月8日 上午11:29:29 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param prizeList
	 * @return
	 */
	private Prize getLottedPrize(List<Prize> prizeList) {
		Prize lottedPrize = null;
		double random = Math.random();
		for (Prize prize : prizeList) {
			double value = prize.getProbability();
			random = random - value;
			if (random <= 0) {
				lottedPrize = prize;
				break;
			}
		}
 		return lottedPrize;
	}

	/**
	 * Description: 增加2个随机的奖品
	 * 
	 * @Version1.0 2014年12月8日 下午5:05:02 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param list
	 * @return
	 */
	private List<Prize> addRandomPrize(List<Prize> prizeList, List<Prize> resultlist) {
		// 生成随机的第一个
		int prize1 = getRandomInteger(prizeList.size());
		resultlist.add(prizeList.get(prize1));
		prizeList.remove(prize1);
		// 生成随机的第二个
		int prize2 = getRandomInteger(prizeList.size());
		resultlist.add(prizeList.get(prize2));
		return resultlist;
	}

	/**
	 * Description:生成一个随机的正整数
	 * 
	 * @Version1.0 2014年12月8日 下午5:16:13 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param max
	 * @return
	 */
	private int getRandomInteger(int max) {
		Random random = new Random();
		return random.nextInt(max);
	}

	/**
	 * Description: 发奖
	 * 
	 * @Version1.0 2014年12月11日 上午9:46:56 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param prize
	 * @return
	 * @throws MediaException
	 */
	@SuppressWarnings("unused")
	private String grantPirze(Long custId, String username, String deviceType, Prize prize) throws Exception {
		String result = "";
		int prizeType = prize.getPrizeType();
		switch (prizeType) {
		// 1:金币，2：章节，3：道具，4：包月
		case 1:
			// 调用发增加金币接口
			String result1 = "fail_money";
			// 加钱
			// 给user 辅账户加钱
			UserTradeBaseVo vo = new UserTradeBaseVo();
			vo.setTradeType(Constans.TRADE_TYPE_RECHARGE);
			vo.setCustId(custId);
			vo.setConsumeSource(deviceType);
			// 唯一单号
			String depositNo = userService.createTradeNo("ACT01",custId);
			vo.setSourceOrderNo(depositNo);
			vo.setUsername(username);
			vo.setRequireRechargeMainAmount(0);
			vo.setRequireRechargeSubAmount(prize.getAmount());
			//辅账户加钱需有效期，加活动码 by weisong 
			vo.setActivityCode(RechargeActivityCodeEnum.LUCKY_BAG.getCode());
			userService.tradeForUser(vo);
			if (!vo.isTradeResult()) {
				// LogUtil.error(logger,
				// "创建充值记录失败:抽中加钱的福袋，custId：{0}"+custId+"");
				throw new MediaException(ErrorCodeEnum.ERROR_CODE_15004.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_15004.getErrorMessage());
			}else {
				result = "success";
			}
			break;
		case 2:
			// 调用给书授权接口
			result = "fail_book";
			
			//如果是章节判定判定是否下架的书，如果是，直接给金币
			MediaCacheBasicVo mediaVo = new MediaCacheBasicVo();
			mediaVo = cacheApi.getMediaBasicFromCache(prize.getPrizeId());
			if(1!=mediaVo.getShelfStatus()){
				logger.error("福袋中奖，发放章节奖品失败,该书不是上架状态，状态为:"+mediaVo.getShelfStatus()+"，奖品id:"+prize.getPrizeId()+",用户id:"+custId);
				break;
			}
			
			BindUserMediaAuthorityVo userVo = new BindUserMediaAuthorityVo();
			userVo.setCustId(custId);
			userVo.setChapterNum(prize.getAmount());

			userVo.setMediaId(prize.getPrizeId());
			short sBook = 2;
			userVo.setUserAuthorityType(sBook);
			BindUserMediaAuthorityResultVo bookPrizeVo = userAuthorityApi.bindUserAuthority(userVo);
			if (null == bookPrizeVo.getUserAuthority()||null==bookPrizeVo.getUserAuthority().getUserAuthorityDetails()) {
//				throw new MediaException(ErrorCodeEnum.ERROR_CODE_15005.getErrorCode() + "",
//						ErrorCodeEnum.ERROR_CODE_15005.getErrorMessage());
				//又要改，掉接口失败 直接给钱
				//不发就不发了吧。。自己记一个log
				logger.error("福袋中奖，发放章节奖品失败，奖品id:"+prize.getPrizeId()+",用户id:"+custId);
			}else {
				try {
					// 发送添加已购消息
					this.sendAddBoughtMessage(prize, bookPrizeVo.getUserAuthority(), custId);
				} catch (Exception e) {
					logger.error("发送添加已购消息异常", e);
	
				}
				result = "success";
			}
			break;
		case 3:// 本期没有
			 // 调用发道具接口
			result = "fail_prop";
			if (2 == 1) {
				throw new MediaException(ErrorCodeEnum.ERROR_CODE_15006.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_15006.getErrorMessage());
			}else {
				result = "success";
			}
			break;
		case 4:
			// 调用发包月道具接口
			result = "fail_month";
			BindUserMediaAuthorityVo userMouthVo = new BindUserMediaAuthorityVo();
			userMouthVo.setCustId(custId);
			userMouthVo.setMediaId(prize.getPrizeId());
			short sMouth = 4;
			userMouthVo.setUserAuthorityType(sMouth);
			userMouthVo.setMonthlyId(Integer.parseInt(prize.getPrizeId() + ""));
			BindUserMediaAuthorityResultVo mouthPrizeVo = userAuthorityApi.bindUserAuthority(userMouthVo);
			if (null == mouthPrizeVo.getUserMonthly()) {
//				throw new MediaException(ErrorCodeEnum.ERROR_CODE_15007.getErrorCode() + "",
//						ErrorCodeEnum.ERROR_CODE_15007.getErrorMessage());
				//又要改，掉接口失败 直接给钱
				//不发就不发了吧。。自己记一个log
				logger.error("福袋中奖，送包月奖品失败，奖品id:"+prize.getPrizeId()+",用户id:"+custId);
			}else {
				result = "success";
			}
			break;
		default:
			break;
		}
		return result;
	}

	
	
	/**
	 * Description: 发金币奖励接口
	 * @Version1.0 2015年1月27日 上午11:40:22 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param username
	 * @param deviceType
	 * @param prize
	 * @return
	 * @throws Exception
	 */
	private void putCoins(Long custId, String username, String deviceType, Long prizeId,int coins) throws Exception {
		// 给user 辅账户加钱
		UserTradeBaseVo vo = new UserTradeBaseVo();
		vo.setTradeType(Constans.TRADE_TYPE_RECHARGE);
		vo.setCustId(custId);
		vo.setConsumeSource(deviceType);
		// 唯一单号
		String depositNo = userService.createTradeNo("ACT01",custId);
		vo.setSourceOrderNo(depositNo);
		vo.setUsername(username);
		vo.setRequireRechargeMainAmount(0);
		vo.setRequireRechargeSubAmount(coins);
		//辅账户加钱需有效期，加活动码 by weisong 
		vo.setActivityCode(RechargeActivityCodeEnum.LUCKY_BAG.getCode());
		userService.tradeForUser(vo);
		if (!vo.isTradeResult()) {
			logger.error("福袋中奖，送金币奖品失败，奖品id:"+prizeId+",用户id:"+custId+",金币数量:"+coins);
			throw new MediaException(ErrorCodeEnum.ERROR_CODE_15004.getErrorCode() + "",
					ErrorCodeEnum.ERROR_CODE_15004.getErrorMessage());
		}
	}

	
	
	
	/**
	 * 
	 * Description: 发送添加已购信息消息
	 * 
	 * @Version1.0 2015年1月7日 下午6:46:07 by 许文轩（xuwenxuan@dangdang.com）创建
	 */
	private void sendAddBoughtMessage(Prize prize, UserAuthority userAuthority, Long custId) {
		AddBoughtMessage addBoughtMessage = new AddBoughtMessage();
		// 奖品名称
		addBoughtMessage.setBoughtContent(prize.getPrizeName());
		// 设置类型为抽奖
		addBoughtMessage.setBoughtType(BoughtTypeEnum.LOT_TYPE);
		// 设置mediaId
		addBoughtMessage.setMediaId(prize.getPrizeId());
		// 设置custId
		addBoughtMessage.setCustId(custId);
		// 如果是全本权限
		if (userAuthority.getAuthorityType() == Constans.USER_AUTHORITY_MEIDA) {
			// 设置全本权限
			addBoughtMessage.setWholeFlag(Bought.WHOLE_FLAG_MEDIA);
		} else {
			// 设置章节权限
			addBoughtMessage.setWholeFlag(Bought.WHOLE_FLAG_CHAPTER);
			// 添加章节id列表
			List<Long> chapterIds = new ArrayList<Long>();
			for (UserAuthorityDetail userAuthorityDetail : userAuthority.getUserAuthorityDetails()) {
				chapterIds.add(userAuthorityDetail.getMediaChapterId());
			}
			addBoughtMessage.setChapterIds(chapterIds);
		}
		try {
			rabbitTemplate.convertAndSend("media.other.add.bought.queue", addBoughtMessage);
			logger.info("发送其他方式添加已购信息成功" + addBoughtMessage);
		} catch (Exception e) {
			logger.error("发送其他方式添加已购信息失败" + e);
		}
	}
	
	/**
	 * Description:封装一个新的 lottedPrize
	 * @Version1.0 2015年2月3日 下午3:15:22 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param lottedPrize
	 * @return
	 */
	private Prize getNewPrize(Prize prize) {
		int coins  = Integer.parseInt(systemApi.getProperty("default_prize_coins","50"));
		//重新封装 lottedPrize
		prize.setAmount(coins);
		prize.setPrizeName(coins+"银铃铛");
		prize.setPrizeType(1);
		prize.setDayLimit(0);
		prize.setTotalLimit(0);
		prize.setPrizeId(0L);
		prize.setProbability(1.0);
		prize.setVestType(1);
		return prize; 
	}

}
