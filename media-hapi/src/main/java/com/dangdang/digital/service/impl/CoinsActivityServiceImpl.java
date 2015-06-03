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
import com.dangdang.digital.service.ICoinsActivityService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.vo.UserTradeBaseVo;

@Service
public class CoinsActivityServiceImpl implements ICoinsActivityService{
	private static final Logger logger = LoggerFactory.getLogger(CoinsActivityServiceImpl.class);
	
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
	public Map<String,Integer> getSpreadCoins(Long custId,String username,String nickname,String deviceType,int type)  throws Exception {
		Map<String,Integer> resultMap = new HashMap<String, Integer>();
		//判定该用户今天是否已经不能领取
		Map<String,String> userCacheMap = activityUserCacheDao.getTodayActivityUserCache(custId);
		if(null==userCacheMap){
			userCacheMap = new HashMap<String,String>();
		}
		if(userCacheMap.containsKey(Constans.MEDIA_SPREAD_COINS_LIMIT_CACHE_KEY)){
			String limitCacheValue = userCacheMap.get(Constans.MEDIA_SPREAD_COINS_LIMIT_CACHE_KEY);
			if(Constans.MEDIA_ACTIVITY_TODAY_USER_LIMIT_FLAG.equals(limitCacheValue)){
				resultMap.put("coins", -2);
				return resultMap;//用户超过日限制
			}
		}
		String startDateStr = DateUtil.formatDate(new Date());
		Date startDate = DateUtil.getdate(startDateStr);
		Date endDate = DateUtil.addDaysOnToday(startDate,1);
		Integer userDayGetTimes = activityRecordDao.selectUserParticipateTimesByType(custId,Constans.MEDIA_ACTIVITY_GETCONS_TYPE,startDate, endDate);
		Integer sysLimit = Integer.parseInt(systemApi.getProperty("spread_coins_user_today_limit","5"));
		if(userDayGetTimes>=sysLimit){
			userCacheMap.put(Constans.MEDIA_SPREAD_COINS_LIMIT_CACHE_KEY, Constans.MEDIA_ACTIVITY_TODAY_USER_LIMIT_FLAG);
			activityUserCacheDao.setTodayActivityUserCache(custId, userCacheMap);
			resultMap.put("coins", -2);
			return resultMap;
		}
		//获取剩余的撒金币次数
		int leftTimes = sysLimit-userDayGetTimes;
		resultMap.put("leftTimes", leftTimes);
		//判断今天此次是否重复领取撒金币
		//需求有变动，又去掉了！！！
//		ActivityRecord aRecord = activityRecordDao.selectTodayGetSpreadByType(custId, type);
//		if(null!=aRecord){
//			resultMap.put("coins", -1);
//			return resultMap;//用户超过日限制
//		}
		//获取金币
		//获取配置中钱的最小、大值
		String minCoinStr = systemApi.getProperty("spread_coins_min_coin", "2");
		String maxCoinStr = systemApi.getProperty("spread_coins_max_coin", "5");
		int minCoin = Integer.parseInt(minCoinStr);
		int maxCoin = Integer.parseInt(maxCoinStr);
		int coins = (int)(Math.random()*(maxCoin+1-minCoin))+minCoin;
		
		ActivityRecord record = new ActivityRecord(Constans.MEDIA_ACTIVITY_GETCONS_TYPE,custId,nickname,"掉钱袋啦",coins,new Date());
		record.setPrizeType(type);
		activityRecordDao.insert(record);
		//给user 辅账户加钱
		UserTradeBaseVo vo = new UserTradeBaseVo();
		vo.setTradeType(Constans.TRADE_TYPE_RECHARGE);
		vo.setCustId(custId);
		vo.setConsumeSource(deviceType);
		//唯一单号
		String depositNo = userService.createTradeNo("ACT06",custId);
		vo.setSourceOrderNo(depositNo);
		vo.setUsername(username);
		vo.setRequireRechargeMainAmount(0);
		vo.setRequireRechargeSubAmount(coins);
		//辅账户加钱需有效期，加活动码 by weisong 
		vo.setActivityCode(RechargeActivityCodeEnum.SPREAD_COINS.getCode());
		userService.tradeForUser(vo);
		if(!vo.isTradeResult()){
			LogUtil.error(logger, "创建充值记录失败:掉钱袋啦，custId：{0}",custId);
			throw new MediaException(ErrorCodeEnum.ERROR_CODE_15513.getErrorCode()+"",ErrorCodeEnum.ERROR_CODE_15513.getErrorMessage());
		}
		activityRecordCacheDao.deleteUserActivityRecordCache(custId, 0);
		activityRecordCacheDao.deleteUserActivityRecordCache(custId, 6);
		resultMap.put("coins", coins);
		return resultMap;
	}


	@Override
	public String getLeftTimesInterval() throws Exception {
		StringBuffer sbf = new StringBuffer();
		String intervalStr = systemApi.getProperty("spread_coins_interval", "7200");
		int interval = Integer.parseInt(intervalStr);
		int leftMsec = DateUtil.getSecondsToNextDay();
		//去除此次的，此次的默认是60s延迟
		int leftTimes = (leftMsec/interval);
		if(leftTimes<1){
			return null;
		}
		for(int i = 1;i<=leftTimes;i++){
			Long currentLong = System.currentTimeMillis();
			if(i==leftTimes){
				sbf.append(currentLong+i*interval*1000+"");
			}else {
				sbf.append(currentLong+i*interval*1000+",");
			}
		}
		return sbf.toString();
	}
}
