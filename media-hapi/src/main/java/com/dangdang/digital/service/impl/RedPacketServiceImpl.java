package com.dangdang.digital.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.RechargeActivityCodeEnum;
import com.dangdang.digital.dao.IActivityRecordCacheDao;
import com.dangdang.digital.dao.IActivityRecordDao;
import com.dangdang.digital.dao.IActivityUserCacheDao;
import com.dangdang.digital.dao.IRedPacketUserCacheDao;
import com.dangdang.digital.dao.IRedPacketUserDao;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.ActivityRecord;
import com.dangdang.digital.model.RedPacketUser;
import com.dangdang.digital.processor.GetRedPacketProcessor;
import com.dangdang.digital.service.IRedPacketService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.vo.UserTradeBaseVo;

@Service
public class RedPacketServiceImpl implements IRedPacketService{
	private static final Logger logger = LoggerFactory.getLogger(GetRedPacketProcessor.class);
	
	@Resource
	private IRedPacketUserDao redPacketUserDao;
	@Resource
	private IRedPacketUserCacheDao redPacketUserCacheDao;
	@Resource
	private IActivityUserCacheDao activityUserCacheDao;
	@Resource
	private IActivityRecordDao activityRecordDao;
	@Resource
	private ISystemApi systemApi;
	@Resource
	private IUserService userService;
	@Resource
	private IActivityRecordCacheDao activityRecordCacheDao;
	
	
	@Override
	@Transactional
	public Map<String,Integer> getRedPacket(Long custId,String username,String nickname,String deviceType,Long redPacketId)  throws Exception {
		Map<String,Integer> resultMap = new HashMap<String, Integer>();
		//判红包是否还有剩余次数
		Integer leftTimes = redPacketUserCacheDao.getRedPacketLefts(redPacketId);
		if(null == leftTimes){
			leftTimes = redPacketUserDao.selectRedPacketLefts(redPacketId);
		}
		if(null==leftTimes||leftTimes<=0){
			redPacketUserCacheDao.setRedPacketLefts(redPacketId, 0);
			resultMap.put("coin", -1);
			return  resultMap;//红包没有次数了
		}
		//获取用户今日领的红包数量
		Map<String,String> userCacheMap = activityUserCacheDao.getTodayActivityUserCache(custId);
		if(null==userCacheMap){
			userCacheMap = new HashMap<String,String>();
		}
		if(userCacheMap.containsKey(Constans.MEDIA_RED_PACKET_DAY_LIMIT_CACHE_KEY)){
			String redPacketCacheValue = userCacheMap.get(Constans.MEDIA_RED_PACKET_DAY_LIMIT_CACHE_KEY);
			if(Constans.MEDIA_ACTIVITY_TODAY_USER_LIMIT_FLAG.equals(redPacketCacheValue)){
				resultMap.put("coin", -2);
				return  resultMap;//用户超过日限制
			}
		}
		String startDateStr = DateUtil.formatDate(new Date());
		Date startDate = DateUtil.getdate(startDateStr);
		Date endDate = DateUtil.addDaysOnToday(startDate,1);
		//Integer userDayGetTimes = redPacketRecordDao.selectUserTodayGettedRedPackets(custId,startDate, endDate);
		Integer userDayGetTimes = activityRecordDao.selectUserParticipateTimesByType(custId,Constans.MEDIA_ACTIVITY_REDPACKET_TYPE,startDate, endDate);
		if(userDayGetTimes>=Integer.parseInt(systemApi.getProperty("red_packet_user_get_limit","1"))){
			userCacheMap.put(Constans.MEDIA_RED_PACKET_DAY_LIMIT_CACHE_KEY, Constans.MEDIA_ACTIVITY_TODAY_USER_LIMIT_FLAG);
			activityUserCacheDao.setTodayActivityUserCache(custId, userCacheMap);
			resultMap.put("coin", -2);
			return  resultMap;
		}
		
		//判断用户是否领过该红包了，如果领过直接返回！
//		Integer gettedCoins = activityRecordDao.selectGettedCoinsByRedPacketId(custId, redPacketId);
		Integer gettedCoins = getUserGettedRedPacketCoins(custId,redPacketId);
		if(null!=gettedCoins){
			resultMap.put("coin",gettedCoins);
			resultMap.put("flag",1);
			return resultMap;
		}
		//获取一个红包
		//获取配置中钱的最小、大值
		String minCoinStr = systemApi.getProperty("red_packet_min_coin", "1");
		String maxCoinStr = systemApi.getProperty("red_packet_max_coin", "10");
		int minCoin = Integer.parseInt(minCoinStr);
		int maxCoin = Integer.parseInt(maxCoinStr);
		int coin = (int)(Math.random()*(maxCoin+1-minCoin))+minCoin;
		int dbCoin = coin * 100;//把元转换成银铃铛！
		ActivityRecord record = new ActivityRecord(Constans.MEDIA_ACTIVITY_REDPACKET_TYPE,custId,nickname,"红包",dbCoin,new Date());
		record.setPrizeId(redPacketId);
		activityRecordDao.insert(record);
		//给user 辅账户加钱
		UserTradeBaseVo vo = new UserTradeBaseVo();
		vo.setTradeType(Constans.TRADE_TYPE_RECHARGE);
		vo.setCustId(custId);
		vo.setConsumeSource(deviceType);
		//唯一单号
		String depositNo = userService.createTradeNo("ACT04",custId);
		vo.setSourceOrderNo(depositNo);
		vo.setUsername(username);
		vo.setRequireRechargeMainAmount(0);
		vo.setRequireRechargeSubAmount(dbCoin);//红包的钱，后台配的是单位是  百银铃铛
		//辅账户加钱需有效期，加活动码 by weisong 
		vo.setActivityCode(RechargeActivityCodeEnum.RED_BAG.getCode());
		userService.tradeForUser(vo);
		if(!vo.isTradeResult()){
			LogUtil.error(logger, "创建充值记录失败:红包加钱，custId：{0}",custId);
			throw new MediaException(ErrorCodeEnum.ERROR_CODE_15513.getErrorCode()+"",ErrorCodeEnum.ERROR_CODE_15513.getErrorMessage());
		}
		
		//该红包个数-1，增加该红包已被领取的钱数
		redPacketUserDao.updateGetted(redPacketId,coin);
		redPacketUserCacheDao.setRedPacketLefts(redPacketId, (leftTimes-1));
		activityRecordCacheDao.deleteUserActivityRecordCache(custId, Constans.MEDIA_ACTIVITY_REDPACKET_TYPE);
		activityRecordCacheDao.deleteUserActivityRecordCache(custId, 0);
		resultMap.put("coin", coin);
		return  resultMap;
	}

	@Override
	@Transactional
	public String creatRedPacket(Long custId,Long recordId) throws Exception {
		
/** 产品需求调整。。这已经是第n次了。。。。    此处逻辑再次调整。		
		//获取用户今日发的红包数量
		Map<String,String> userCacheMap = activityUserCacheDao.getTodayActivityUserCache(custId);
		if(null==userCacheMap){
			userCacheMap = new HashMap<String,String>();
		}
		if(userCacheMap.containsKey(Constans.MEDIA_RED_PACKET_SHARE_LIMIT_CACHE_KEY)){
			String redPacketCacheValue = userCacheMap.get(Constans.MEDIA_RED_PACKET_SHARE_LIMIT_CACHE_KEY);
			if(Constans.MEDIA_ACTIVITY_TODAY_USER_LIMIT_FLAG.equals(redPacketCacheValue)){
				return "0";
			}
		}
		String startDateStr = DateUtil.formatDate(new Date());
		Date startDate = DateUtil.getdate(startDateStr);
		Date endDate = DateUtil.addDaysOnToday(startDate,1);
		Integer userShareTimes = redPacketUserDao.selectUserTodayShareRedPackets(custId,startDate, endDate);
		if(userShareTimes>=Integer.parseInt(systemApi.getProperty("user_share_red_packet_limit","1"))){
			userCacheMap.put(Constans.MEDIA_RED_PACKET_SHARE_LIMIT_CACHE_KEY, Constans.MEDIA_ACTIVITY_TODAY_USER_LIMIT_FLAG);
			activityUserCacheDao.setTodayActivityUserCache(custId, userCacheMap);
			return "0";
		}
*/		
		//判断中奖的recordid 是否存在
		if(null==activityRecordDao.selectById(recordId)){
			return null;
		}
		//判断该次抽奖记录是否已经领过红包了，如果领过返回之前的红包id
		Long dbRedPacketId = redPacketUserDao.selectRedPacketIdByPrizeIdFromDb(recordId);
		if(null!=dbRedPacketId){
			return dbRedPacketId+"";
		}
		int redPacketTimes = Integer.parseInt(systemApi.getProperty("red_packet_times", "10"));
		//生成一个红包
		RedPacketUser redPacket = new RedPacketUser(custId,redPacketTimes,0,redPacketTimes,new Date());
		redPacket.setPrizeId(recordId);
		redPacketUserDao.insert(redPacket);
		activityRecordCacheDao.deleteUserActivityRecordCache(custId, 0);
		activityRecordCacheDao.deleteUserActivityRecordCache(custId, 4);
		return redPacket.getMediaRedPacketUserId()+"";
	}

	@Override
	public Integer getRedPacketLeft(Long redPacketId) throws Exception {
		//判红包是否还有剩余次数
		Integer leftTimes = redPacketUserCacheDao.getRedPacketLefts(redPacketId);
		if(null == leftTimes){
			leftTimes = redPacketUserDao.selectRedPacketLefts(redPacketId);
		}
		if(null==leftTimes||leftTimes<=0){
			redPacketUserCacheDao.setRedPacketLefts(redPacketId, 0);
			return  0;//红包没有次数了
		}
		return leftTimes==null?0:leftTimes;
	}

	@Override
	public Integer getUserGettedRedPacketCoins(Long custId, Long redPacketId) {

		Integer coins = activityRecordCacheDao.getGettedRedPacketCoins(custId, redPacketId);
		if(null == coins){
			coins = activityRecordDao.selectGettedCoinsByRedPacketId(custId, redPacketId);
			if(null==coins){
				return null;
			}
			coins = coins/100;
			activityRecordCacheDao.setGettedRedPacketCoins(custId, redPacketId, coins);
		}
		return coins;
	}
	
}
