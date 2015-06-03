package com.dangdang.digital.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.ActivityEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.dao.IActivityRecordCacheDao;
import com.dangdang.digital.dao.IActivityRecordDao;
import com.dangdang.digital.dao.IActivityUserCacheDao;
import com.dangdang.digital.dao.IActivityUserDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IConsumerConsumeDao;
import com.dangdang.digital.dao.IEbookConsRecordDao;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.model.ActivityRecord;
import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.model.WorshipRecord;
import com.dangdang.digital.service.IActivityInfoService;
import com.dangdang.digital.service.IActivityUserService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.service.IWorshipRecordService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.vo.ActivityVo;
import com.dangdang.digital.vo.ReturnActivityUserVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description:活动【排行、福袋等】用户信息Service实现类 
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午2:22:34  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Service
public class ActivityUserServiceImpl extends BaseServiceImpl<ActivityUser,Long> implements IActivityUserService {

	@Resource
	private IActivityRecordDao activityRecordDao;
	@Resource
	private IActivityUserDao activityUserDao;
	@Resource
	private IConsumerConsumeDao consumerConsumeDao;
	@Resource
	private IEbookConsRecordDao ebookConsRecordDao;
	@Resource
	private IUserService userService;
	@Resource
	private IActivityUserCacheDao activityUserCacheDao;
	@Resource
	private IActivityInfoService activityInfoService;
	@Resource
	private IActivityRecordCacheDao activityRecordCacheDao;
	
	@Resource
	private ICacheService cacheService;
	@Resource
	private ISystemApi systemApi;
	@Resource
	private ICacheApi  cacheApi;
	
	@Resource
	private  IWorshipRecordService worshipRecordService;
	
	@Override
	public IBaseDao<ActivityUser> getBaseDao() {
		return activityUserDao;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,String> worship(ActivityVo activityVo) throws Exception {
		Map<String, String> resultMap = new HashMap<String,String>();
		String result = "";
		int cons = 0;
		//判断缓存，今天是否超过膜拜限制了
		Long custId = activityVo.getCustId();
		String username = activityVo.getUsername();
		String nickname = activityVo.getNickname();
		Map<String,String> userCacheMap = activityUserCacheDao.getTodayActivityUserCache(custId);
		if(null==userCacheMap){
			userCacheMap = new HashMap<String,String>();
		}
		if(userCacheMap.containsKey(Constans.WORSHIP_DAY_LIMIT)){
			String shareCacheValue = userCacheMap.get(Constans.MEDIA_WORSHIP_LIMIT_CACHE_KEY);
			if(Constans.MEDIA_ACTIVITY_TODAY_USER_LIMIT_FLAG.equals(shareCacheValue)){
				resultMap.put("result", "day_limt");
				return resultMap;
			}
		}
		int activityType = ActivityEnum.WORSHIP_TYPE.getKey();
		/**
		 * 1,判断今日膜拜的次数是否<=配置中的数量
		 * 2，更新膜拜用户 膜拜+1，被膜拜用户  被膜拜+1
		 * 3，记录一条膜拜记录
		 * 4，给用户账号加钱
		 * 5，给返回值
		 * 
		 */
		//获取配置中膜拜的限制次数
//		String limitWorshipTimesStr = systemApi.getProperty("worship_day_limit_times", "999");
//		Integer limitWorshipTimes = Integer.parseInt(limitWorshipTimesStr);
		//获取活动表中有关膜拜的配置
		ActivityInfo worshConf = activityInfoService.getActivityInfoByCode("day-worship-give");
		int limitWorshipTimes = worshConf.getDayWorshipLimit()==null?3:worshConf.getDayWorshipLimit();
		
		//获取该用户今天的膜拜数量
		String startDateStr = DateUtil.formatDate(new Date());
		Date startDate = DateUtil.getdate(startDateStr);
		Date endDate = DateUtil.addDaysOnToday(startDate,1);
		
		Integer userDayWorshipTimes = activityRecordDao.selectUserParticipateTimesByType(activityVo.getCustId(),activityVo.getActivityType(),startDate, endDate);
		if(userDayWorshipTimes>=limitWorshipTimes){
			result = "day_limt";//今天分享超限制了。
			userCacheMap.put(Constans.MEDIA_WORSHIP_LIMIT_CACHE_KEY, Constans.MEDIA_ACTIVITY_TODAY_USER_LIMIT_FLAG);
			activityUserCacheDao.setTodayActivityUserCache(custId, userCacheMap);
		}else {
			activityUserDao.addOneWorshipTime(activityVo.getCustId(),nickname);
			activityUserDao.addOneWorshipedTime(activityVo.getWorshipedCustId(),activityVo.getWorshipedUsername());
			ActivityRecord record = new ActivityRecord(activityType,activityVo.getCustId(),activityVo.getWorshipedCustId(),nickname,activityVo.getWorshipedUsername(),new Date());
			
			//获取配置中钱的最小、大值,改成从促销活动配置里取了。
//			String minConsStr = systemApi.getProperty("worship_min_cons", "1");
//			String maxConsStr = systemApi.getProperty("worship_max_cons", "10");
//			int minCons = Integer.parseInt(minConsStr);
//			int maxCons = Integer.parseInt(maxConsStr);
			int minCons = worshConf.getLowestGoldPiece()==null?2:worshConf.getLowestGoldPiece();
			int maxCons = worshConf.getHighestGoldPiece()==null?8:worshConf.getHighestGoldPiece();
			cons = (int)(Math.random()*(maxCons+1-minCons))+minCons;
			//金币就是奖品名称
			record.setPrizeName("膜拜");
			record.setAmount(cons);
			/**
			 * begin 向膜拜记录表中更新该用户的膜拜记录用户  lvxiang 2015-03-16
			 */
			WorshipRecord wsr = new WorshipRecord();
			wsr.setCustId(custId);
			wsr.setUsername(username);
			wsr.setWorshipedCustId(activityVo.getWorshipedCustId());
			wsr.setWorshipedUsername(activityVo.getWorshipedUsername());
			wsr.setAmount(cons);
			wsr.setStatus(WorshipRecord.STATUS_OK);
			worshipRecordService.save(wsr);
			//膜拜我的
			String cacheKeyWorShipToMe = Constans.CUST_WORSHIP_CACHE_KEY.concat(String.valueOf(custId)).concat("_"+WorshipRecord.Worship_OF_ME);
			//被膜拜用户的
			String cacheKeyMyWorShip = Constans.CUST_WORSHIP_CACHE_KEY.concat(String.valueOf(activityVo.getWorshipedCustId())).concat("_"+WorshipRecord.Worship_TO_ME);
			cacheApi.cleanCacheByKey(cacheKeyWorShipToMe);
			cacheApi.cleanCacheByKey(cacheKeyMyWorShip);
			/**
			 * end 向膜拜记录表中更新该用户的膜拜记录用户  lvxiang 2015-03-16
			 */
			
			activityRecordDao.insert(record);
			//加钱
			//给user 辅账户加钱
			UserTradeBaseVo vo = new UserTradeBaseVo();
			vo.setTradeType(Constans.TRADE_TYPE_RECHARGE);
			vo.setCustId(custId);
			vo.setConsumeSource(activityVo.getDeviceType());
			//唯一单号
			String depositNo = userService.createTradeNo("ACT02",record.getMediaActivityRecordId());
			vo.setSourceOrderNo(depositNo);
			vo.setUsername(username);
			vo.setRequireRechargeMainAmount(0);
			vo.setRequireRechargeSubAmount(cons);
			//辅账户加钱需有效期，加活动码 by weisong 
			
			userService.tradeForUser(vo);
			if(!vo.isTradeResult()){
				LogUtil.error(logger, "创建充值记录失败:膜拜加钱，custId：{0}",custId);
				throw new MediaException(ErrorCodeEnum.ERROR_CODE_15513.getErrorCode()+"",ErrorCodeEnum.ERROR_CODE_15513.getErrorMessage());
			}
			result = "success";
			activityUserCacheDao.deleteActivityUserVoCache(custId);
			activityUserCacheDao.deleteActivityUserVoCache(activityVo.getWorshipedCustId());
			
			activityRecordCacheDao.deleteUserActivityRecordCache(custId, 0);
			activityRecordCacheDao.deleteUserActivityRecordCache(custId, 2);
		}
		resultMap.put("cons", cons+"");
		resultMap.put("result", result);
		return resultMap;
	}
	
	@Override
	@Transactional
	public String share(ActivityVo activityVo) throws Exception {
		/**
		 * 1,判断今日分享的次数是否<=配置中的数量
		 * 2，更新分享用户 分享次数+1
		 * 3，记录一条分享记录
		 * 4，给用户账号加一次抽奖机会
		 * 5，给返回值
		 * 
		 */
		String result = "";
		Long custId = activityVo.getCustId();
		//判断缓存，今天是否超过分享限制了
		Map<String,String> userCacheMap = activityUserCacheDao.getTodayActivityUserCache(custId);
		if(null==userCacheMap){
			userCacheMap = new HashMap<String,String>();
		}
		if(userCacheMap.containsKey(Constans.MEDIA_SHARE_LIMIT_CACHE_KEY)){
			String shareCacheValue = userCacheMap.get(Constans.MEDIA_SHARE_LIMIT_CACHE_KEY);
			if(Constans.MEDIA_ACTIVITY_TODAY_USER_LIMIT_FLAG.equals(shareCacheValue)){
				return "0";
			}
		}
		//获取配置中分享的限制次数
	    String limitShareTimesStr = systemApi.getProperty("share_day_limit_times", "3");
		Integer limitShareTimes = Integer.parseInt(limitShareTimesStr);
		//获取该用户今天的分享数量
		String startDateStr = DateUtil.formatDate(new Date());
		Date startDate = DateUtil.getdate(startDateStr);
		Date endDate = DateUtil.addDaysOnToday(startDate,1);
		
		//Integer userDayShareTimes = activityRecordDao.selectUserParticipateTimesByType(activityVo.getCustId(),activityVo.getActivityType(),startDate, endDate);
		Integer userDayShareTimes = activityRecordDao.selectUserParticipateTimesByType(activityVo.getCustId(),activityVo.getActivityType(),startDate, endDate);
		if(userDayShareTimes>=limitShareTimes){
			result = "0";//今天分享超限制了。
			userCacheMap.put(Constans.MEDIA_SHARE_LIMIT_CACHE_KEY, Constans.MEDIA_ACTIVITY_TODAY_USER_LIMIT_FLAG);
			activityUserCacheDao.setTodayActivityUserCache(custId, userCacheMap);
		}else {
			activityUserDao.addOneShareTime(custId,activityVo.getUsername());
			String addTimesStr = systemApi.getProperty("add_share_times", "1");
			int addTimes = Integer.parseInt(addTimesStr);
			activityUserDao.addLotTime(custId, addTimes, 0,activityVo.getUsername());
			ActivityRecord record = new ActivityRecord(activityVo.getActivityType(),custId, activityVo.getUsername(), new Date());
			record.setPrizeName("分享");
			record.setAmount(addTimes);
			activityRecordDao.insert(record);
			activityUserCacheDao.deleteActivityUserVoCache(custId);
			result = "1";
			activityRecordCacheDao.deleteUserActivityRecordCache(custId, 0);
			//以后有查看该类奖品记录的需求时：
			activityRecordCacheDao.deleteUserActivityRecordCache(custId, 3);
		}
		return result;
		
	}

	@Override
	public ActivityUser selectActivityUserInfo(Long custId) {
		return  activityUserDao.selectOne("ActivityUserMapper.selectByCustId", custId);
	}
	@Override
	public ActivityUser selectActivityUserInfoFromMaster(Long custId) {
		return activityUserDao.selectMasterOne("ActivityUserMapper.selectByCustId", custId);
	}
	
	
	@Override
	@Transactional
	public int sendLotTimes(ActivityVo activityVo) throws Exception {
		int result = 0;
		Long custId = activityVo.getCustId();
		//判断缓存，今天是否超过领过福袋了限制了
		Map<String,String> userCacheMap = activityUserCacheDao.getTodayActivityUserCache(custId);
		if(null==userCacheMap){
			userCacheMap = new HashMap<String,String>();
		}
		if(userCacheMap.containsKey(Constans.MEDIA_PUSHLOTS_LIMIT_CACHE_KEY)){
			String pushLotsCacheValue = userCacheMap.get(Constans.MEDIA_PUSHLOTS_LIMIT_CACHE_KEY);
			if(Constans.MEDIA_ACTIVITY_TODAY_USER_LIMIT_FLAG.equals(pushLotsCacheValue)){
				return 0;
			}
		}
		
		//获取该用户今天的灵福袋的记录条数
		String startDateStr = DateUtil.formatDate(new Date());
		Date startDate = DateUtil.getdate(startDateStr);
		Date endDate = DateUtil.addDaysOnToday(startDate,1);
		
		Integer userDayPushLotsTimes = activityRecordDao.selectUserParticipateTimesByType(activityVo.getCustId(),activityVo.getActivityType(),startDate, endDate);
		//获取配置中每日的限制次数
	    String limitPutsTimesStr = systemApi.getProperty("day_put_lot_times", "1");
		Integer limitPutsTimes = Integer.parseInt(limitPutsTimesStr);
		if(userDayPushLotsTimes>=limitPutsTimes){
			result = 0;//今天分享超限制了。
			userCacheMap.put(Constans.MEDIA_PUSHLOTS_LIMIT_CACHE_KEY, Constans.MEDIA_ACTIVITY_TODAY_USER_LIMIT_FLAG);
			activityUserCacheDao.setTodayActivityUserCache(custId, userCacheMap);
		}else {
			String addTimesStr = systemApi.getProperty("send_lot_times", "1");
			int addTimes = Integer.parseInt(addTimesStr);
			activityUserDao.addLotTime(custId,addTimes, 0, activityVo.getNickname());
			ActivityRecord record = new ActivityRecord(activityVo.getActivityType(),custId, activityVo.getNickname(), new Date());
			record.setPrizeName("每日送"+addTimes+"个福袋");
			record.setAmount(addTimes);
			activityRecordDao.insert(record);
			activityUserCacheDao.deleteActivityUserVoCache(custId);
			activityRecordCacheDao.deleteUserActivityRecordCache(custId, 0);
			activityRecordCacheDao.deleteUserActivityRecordCache(custId, 5);
			result = addTimes;
		}
		return  result;
	}
	
	@Override
	@Transactional
	public void transferLuckyPacket(Long sender, Long receiver, int amount) throws MediaException {
		
		ActivityUser senderUser = this.selectActivityUserInfo(sender);
		if(senderUser!=null && senderUser.getLotTimes()>=amount){
			//给发送者扣除
			activityUserDao.addLotTime(sender, -1*amount , 0, senderUser.getUsername());
			//获得接收者nickName
			UserTradeBaseVo receiverBaseInfo = cacheApi.getUserWholeInfoByCustId(receiver);
			String receiverNickName = "";
			if(receiverBaseInfo!=null && receiverBaseInfo.getNickname()!=null){
				receiverNickName = receiverBaseInfo.getNickname();
			}
			//给接收者加福袋
			activityUserDao.addLotTime(receiver, amount , 0, receiverNickName);
		}else{
			//福袋数量不足
			throw new MediaException(ErrorCodeEnum.ERROR_CODE_22008.getErrorCode()+"", ErrorCodeEnum.ERROR_CODE_22008.getErrorMessage());
		}
	}
}
