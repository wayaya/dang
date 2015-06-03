package com.dangdang.digital.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.constant.ActivityEnum;
import com.dangdang.digital.dao.IActivityRecordDao;
import com.dangdang.digital.dao.IActivityUserDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IConsumerConsumeDao;
import com.dangdang.digital.model.ActivityRecord;
import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.service.IActivityRecordService;
import com.dangdang.digital.service.IActivityUserService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.vo.ActivityVo;

/**
 * Description:活动【排行、福袋等】用户信息Service实现类 
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午2:22:34  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Service
public class ActivityUserServiceImpl extends BaseServiceImpl<ActivityUser,Long> implements IActivityUserService {

//	@Resource
//	ISystemPropertiesApi systemPropertiesApi;
	
	@Resource
	IActivityRecordService activityRecordService;
	@Resource
	IActivityRecordDao activityRecordDao;
	@Resource
	IActivityUserDao activityUserDao;
	@Resource
	IConsumerConsumeDao consumerConsumeDao;
	
	@Override
	public IBaseDao<ActivityUser> getBaseDao() {
		// TODO Auto-generated method stub
		return activityUserDao;
	}
	@Override
	@Transactional
	public Map<String,String> worship(ActivityVo activityVo) throws Exception {
		Map<String, String> resultMap = new HashMap<String,String>();
		String result = "";
		int cons = 0;
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
//		String limitWorshipTimesStr = systemPropertiesApi.getProperty("worship_day_limit_times", "3");
		String limitWorshipTimesStr = "3";
		Integer limitWorshipTimes = Integer.parseInt(limitWorshipTimesStr);
		
		//获取该用户今天的膜拜数量
		String startDateStr = DateUtil.formatDate(new Date());
		Date startDate = DateUtil.getdate(startDateStr);
		Date endDate = DateUtil.addDaysOnToday(startDate,1);
		
		Integer userDayWorshipTimes = activityRecordDao.selectUserParticipateTimesByType(activityVo.getCustId(),activityVo.getActivityType(),startDate, endDate);
		if(userDayWorshipTimes>=limitWorshipTimes){
			result = "day_limt";//今天分享超限制了。
		}else {
			activityUserDao.addOneWorshipTime(activityVo.getCustId(),activityVo.getUsername());
			activityUserDao.addOneWorshipedTime(activityVo.getWorshipedCustId(),activityVo.getWorshipedUsername());
			ActivityRecord record = new ActivityRecord(activityType,activityVo.getCustId(),activityVo.getWorshipedCustId(),activityVo.getUsername(),activityVo.getWorshipedUsername(),new Date());
			activityRecordService.save(record);
			//获取配置中钱的最小、大值
			int minCons = 1;
			int maxCons = 10;
			cons = (int)(Math.random()*(maxCons+1-minCons))+minCons;
			//加钱
			//putCons(custId,putCons);
			result = "success";
		}
		resultMap.put("cons", cons+"");
		resultMap.put("result", result);
		return resultMap;
	}
	
	@Override
	@Transactional
	public Map<String,String> share(ActivityVo activityVo) {
		Map<String, String> resultMap = new HashMap<String,String>();
		String result = "";
		int chances = 0;
		/**
		 * 1,判断今日分享的次数是否<=配置中的数量
		 * 2，更新分享用户 分享次数+1
		 * 3，记录一条分享记录
		 * 4，给用户账号加一次抽奖机会
		 * 5，给返回值
		 * 
		 */
		//获取配置中分享的限制次数
		//String limitShareTimesStr = systemPropertiesApi.getProperty("share_day_limit_times", "3");
		String limitShareTimesStr = "3";
		Integer limitShareTimes = Integer.parseInt(limitShareTimesStr);
		//获取该用户今天的分享数量
		String startDateStr = DateUtil.formatDate(new Date());
		Date startDate = null;
		try {
			startDate = DateUtil.getdate(startDateStr);
			Date endDate = DateUtil.addDaysOnToday(startDate,1);
			
			Integer userDayShareTimes = activityRecordDao.selectUserParticipateTimesByType(activityVo.getCustId(),activityVo.getActivityType(),startDate, endDate);
			if(userDayShareTimes>=limitShareTimes){
				result = "day_limt";//今天分享超限制了。
			}else {
				Long custId = activityVo.getCustId();
				activityUserDao.addOneShareTime(custId,activityVo.getUsername());
				activityUserDao.addLotTime(custId, 1, 0,activityVo.getUsername());
				ActivityRecord record = new ActivityRecord(activityVo.getActivityType(),custId, activityVo.getUsername(), new Date());
				activityRecordService.save(record);
				chances = 1;
				result = "success";
				
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("chances", chances+"");
		resultMap.put("result", result);
		return resultMap;
		
	}

	@Override
	public Map<String, Object> selectActivityUserInfo(Long custId) {
		String useImgUrl = "www.baidu.com";
		//userImg = ""   获取用户的头像地址
		ActivityUser user = new ActivityUser();
		user = activityUserDao.selectOne("ActivityUserMapper.selectByCustId", custId);
		Map<String, Object> map =  convertBeanToMap(user);
		map.put("useImgUrl", useImgUrl);
		return map;
	}

	@Override
	public List<Media> selectUserconsumeBooks(Long custId) {
		List<Media> userList = new ArrayList<Media>();
		List<Long> ccList = consumerConsumeDao.selectUserconsumeBooksIds(custId);
		for(Long l:ccList){
			//从缓存中获取书的基本信息
			//Media m = getFromRedist(l);
			Media m = new Media();
			m.setMediaId(l);
			m.setProductId(l);
			userList.add(m);
		}
		return userList;
	}
	
}
