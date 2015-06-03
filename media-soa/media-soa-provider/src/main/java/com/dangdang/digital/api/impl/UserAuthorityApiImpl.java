/**
 * Description: UserAuthorityApiImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 下午3:19:42  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.api.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.dangdang.base.commons.utils.LogUtil;
import com.dangdang.common.api.ICommonApi;
import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.api.IUserAuthorityApi;
import com.dangdang.digital.constant.ActivityEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.ActivityRecord;
import com.dangdang.digital.model.UserAuthority;
import com.dangdang.digital.model.UserMonthly;
import com.dangdang.digital.service.IActivityRecordService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.IUserAuthorityService;
import com.dangdang.digital.service.IUserMonthlyService;
import com.dangdang.digital.vo.BindUserMediaAuthorityResultVo;
import com.dangdang.digital.vo.BindUserMediaAuthorityVo;
import com.dangdang.digital.vo.UserAuthorityResultVo;
import com.dangdang.digital.vo.UserMonthlyCacheVo;

/**
 * Description: 用户阅读权限接口
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 下午3:19:42  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Component("userAuthorityApi")
public class UserAuthorityApiImpl implements IUserAuthorityApi {

	@Resource
	private ISystemApi systemApi;
	@Resource
	private ICommonApi commonApi;
	@Resource
	private IUserMonthlyService userMonthlyService;
	@Resource
	private IUserAuthorityService userAuthorityService;
	@Resource
	private IActivityRecordService activityRecordService;
	@Resource
	private ICacheService cacheService;
	//首次登陆送包月id系统配置缓存key
	private static final String FIRST_LOGIN_MONTHLY_ID_KEY = "first_login_monthly_id";	
	//首次登陆送包月id默认值
	private static final String FIRST_LOGIN_MONTHLY_ID_DEFULT = "30001";
	//首次分享送包月id系统配置缓存key
	private static final String FIRST_SHARE_MONTHLY_ID_KEY = "first_share_monthly_id";	
	//分时段分享送包月时间配置系统配置缓存key
	private static final String TIME_GIVING_MONTHLY_SET_KEY = "time_giving_monthly_set";	
	//首次分享送包月id默认值
	private static final String FIRST_SHARE_MONTHLY_ID_DEFULT = "30000";
	//限时分享送包月活动时间格式
	private static final String[] TIME_GIVING_MONTHLY_DATE_FORMAT = {"yyyyMMddhhmmss"};
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthorityApiImpl.class);
	@Override
	public UserAuthorityResultVo queryAuthority(Long custId, Long mediaId,
			List<Long> chapterIds){
		UserAuthorityResultVo resultVo = new UserAuthorityResultVo();
		boolean result = false;
		if (mediaId != null && custId != null) {
			result = userAuthorityService.findWithDetailByCustIdAndMediaId(
					custId, mediaId, chapterIds);
		}
		if(result){
			resultVo.setHasMediaAuthority(result);
		}else{
			UserMonthly userMonthly = userMonthlyService.findUserMonthlyNowByCustId(custId, mediaId, chapterIds);
			if(userMonthly != null && userMonthly.getMonthlyEndTime() != null){
				resultVo.setMonthlyEndTime(userMonthly.getMonthlyEndTime());
			}
		}
		return resultVo;
	}
	@Override
	public BindUserMediaAuthorityResultVo bindUserAuthority(BindUserMediaAuthorityVo vo) throws Exception{
		Short userAuthorityType = vo.getUserAuthorityType();
		BindUserMediaAuthorityResultVo resultVo = new BindUserMediaAuthorityResultVo();
		resultVo.setUserAuthorityType(userAuthorityType);
		if (userAuthorityType != null
				&& userAuthorityType == Constans.USER_AUTHORITY_MONTHLY) {
			// 绑定包月权限
			UserMonthly result = userMonthlyService.bindUserMonthlyAuthority(vo);
			//更新缓存
			if(result != null){
				UserMonthlyCacheVo userMonthlyCacheVo = new UserMonthlyCacheVo();
				userMonthlyCacheVo.getUserMonthlys().put(result.getMonthlyPaymentRelation(), result);
				cacheService.updateUserMonthlyCacheVo(userMonthlyCacheVo);
				resultVo.setUserMonthly(result);
				return resultVo;
			}
		} else if (userAuthorityType != null
				&& (userAuthorityType == Constans.USER_AUTHORITY_MEIDA || userAuthorityType == Constans.USER_AUTHORITY_CHAPTER)) {
			// 绑定电子书权限
			UserAuthority result = userAuthorityService.bindUserMediaAuthority(vo);
			// 更新缓存
			if(result != null){
				cacheService.setUserAuthorityCacheVo(result);
				resultVo.setUserAuthority(result);
				return resultVo;
			}
		}
		return resultVo;
	}
	@Override
	public boolean bindUserMonthlyForFirstLogin(Long custId) throws Exception {
		if(custId == null){
			throw new MediaException("参数：custId为空！");
		}
		LogUtil.info(LOGGER, "首次登陆送包月，custId:{0}", custId);
		BindUserMediaAuthorityVo vo = new BindUserMediaAuthorityVo();
		vo.setUserAuthorityType(Constans.USER_AUTHORITY_MONTHLY);		
		vo.setMonthlyId(Integer.valueOf(systemApi.getProperty(FIRST_LOGIN_MONTHLY_ID_KEY, FIRST_LOGIN_MONTHLY_ID_DEFULT)));
		vo.setCustId(custId);
		vo.setFirstLoginGiving(Constans.FIRST_LOGIN_GIVING_YES);
		BindUserMediaAuthorityResultVo resultVo = this.bindUserAuthority(vo);
		if(resultVo != null && resultVo.getUserMonthly() != null){
			cacheService.insertfirstLoginGivingFlagToRedis(custId);
			try {
				Map<String, String> retMap = commonApi
						.getCustInfoFromCache(custId + "");
				if (CollectionUtils.isEmpty(retMap)
						|| !retMap.containsKey("nickName")) {
					retMap = commonApi.getCustInfo(custId + "", true);
				}
				String nikeName = retMap.get("nickName");
				ActivityRecord activityRecord = new ActivityRecord(
						ActivityEnum.FIRSTLOGIN_TYPE.getKey(), custId,
						nikeName, new Date());
				activityRecord.setPrizeName("登陆送包月");
				activityRecord.setAmount(resultVo.getUserMonthly().getMonthlyDays());
				activityRecordService.save(activityRecord);
				cacheService.cleanCacheByKey(Constans.MEDIA_USER_ACTIVITYS_KEY + custId+"_0");
				cacheService.cleanCacheByKey(Constans.MEDIA_USER_ACTIVITYS_KEY + custId+"_"+ActivityEnum.FIRSTLOGIN_TYPE.getKey());
			} catch (Exception e) {
				LogUtil.error(LOGGER, "首次登陆送包月记录活动奖励记录失败，custId:{0}", custId);
			}
			return true;
		}		
		return false;
	}
	
	@Override
	public BindUserMediaAuthorityResultVo bindUserMonthlyForFirstShare(Long custId,String nikeName) throws Exception {
		if(custId == null){
			throw new MediaException("参数：custId为空！");
		}	
		//查询系统配置中限时分享送包月有效活动
		Map<String,Object> resultMap = getMonthlyIdForTimeShare();		
		if(!CollectionUtils.isEmpty(resultMap) && resultMap.containsKey("monthlyId")){
			LogUtil.info(LOGGER, "限时分享送包月，custId:{0}", custId);
			BindUserMediaAuthorityVo vo = new BindUserMediaAuthorityVo();
			vo.setUserAuthorityType(Constans.USER_AUTHORITY_MONTHLY);		
			vo.setMonthlyId(Integer.valueOf(resultMap.get("monthlyId").toString()));
			vo.setCustId(custId);
			vo.setShareGivingTime(new Date());
			vo.setShareGivingTimeStart((Date)resultMap.get("startTime"));
			vo.setShareGivingTimeEnd((Date)resultMap.get("endTime"));
			BindUserMediaAuthorityResultVo result = this.bindUserAuthority(vo);
			if(result != null && result.getUserMonthly() != null && result.getUserMonthly().getMonthlyEndTime() != null){
				ActivityRecord activityRecord = new ActivityRecord(ActivityEnum.FIRSTSHARE_TYPE.getKey(),custId,nikeName,new Date());
				activityRecord.setPrizeName("限时分享送包月");
				activityRecord.setAmount(result.getUserMonthly().getMonthlyDays());
				try {
					activityRecordService.save(activityRecord);
					cacheService.cleanCacheByKey(Constans.MEDIA_USER_ACTIVITYS_KEY + custId+"_0");
					cacheService.cleanCacheByKey(Constans.MEDIA_USER_ACTIVITYS_KEY + custId+"_"+ActivityEnum.FIRSTSHARE_TYPE.getKey());
				} catch (Exception e) {
					LogUtil.error(LOGGER, "限时分享送包月记录活动奖励记录失败，custId:{0}", custId);
				}
			}
			return result;
		}else{
			LogUtil.info(LOGGER, "首次分享送包月，custId:{0}", custId);
			BindUserMediaAuthorityVo vo = new BindUserMediaAuthorityVo();
			vo.setUserAuthorityType(Constans.USER_AUTHORITY_MONTHLY);		
			vo.setMonthlyId(Integer.valueOf(systemApi.getProperty(FIRST_SHARE_MONTHLY_ID_KEY, FIRST_SHARE_MONTHLY_ID_DEFULT)));
			vo.setCustId(custId);
			vo.setFirstShareGiving(Constans.FIRST_SHARE_GIVING_YES);	
			BindUserMediaAuthorityResultVo result = this.bindUserAuthority(vo);
			if(result != null && result.getUserMonthly() != null && result.getUserMonthly().getMonthlyEndTime() != null){
				ActivityRecord activityRecord = new ActivityRecord(ActivityEnum.FIRSTSHARE_TYPE.getKey(),custId,nikeName,new Date());
				activityRecord.setPrizeName("首次分享送包月");
				activityRecord.setAmount(result.getUserMonthly().getMonthlyDays());
				try {
					activityRecordService.save(activityRecord);
					cacheService.cleanCacheByKey(Constans.MEDIA_USER_ACTIVITYS_KEY + custId+"_0");
					cacheService.cleanCacheByKey(Constans.MEDIA_USER_ACTIVITYS_KEY + custId+"_"+ActivityEnum.FIRSTSHARE_TYPE.getKey());
				} catch (Exception e) {
					LogUtil.error(LOGGER, "首次分享送包月记录活动奖励记录失败，custId:{0}", custId);
				}
			}
			return result;
		}
		
	}
	@Override
	public UserMonthly findUserMonthlyNowByCustId(Long custId, Long mediaId,
			List<Long> chapterIds){
		return userMonthlyService.findUserMonthlyNowByCustId(custId, mediaId, chapterIds);
	}
	
	private Map<String,Object> getMonthlyIdForTimeShare(){
		Date now = new Date();
		try {
			String timeMonthlySet = systemApi.getProperty(TIME_GIVING_MONTHLY_SET_KEY);
			LogUtil.info(LOGGER, "限时分享送包月活动时间配置：{0}", timeMonthlySet);
			if(StringUtils.isBlank(timeMonthlySet)){
				return null;
			}
			String[] monthlySet = timeMonthlySet.trim().split(";");
			for(String monthly : monthlySet){
				if(StringUtils.isNotBlank(monthly)){
					String[] monthlyChildrenSet = monthly.trim().split(":");
					if(monthlyChildrenSet.length == 2){
						String[] monthlyGrandsonSet = monthlyChildrenSet[1].trim().split(",");
						for(String dateSet : monthlyGrandsonSet){
							String[] dateChildrenSet = dateSet.split("-");
							if(dateChildrenSet.length == 2){
								String[] startTimeSet = dateChildrenSet[0].split("~");
								String[] endTimeSet = dateChildrenSet[1].split("~");
								if(startTimeSet.length == 3 && endTimeSet.length == 3){
									Date startTime = DateUtils.parseDate(startTimeSet[0]+startTimeSet[1]+startTimeSet[2]+"000000", TIME_GIVING_MONTHLY_DATE_FORMAT);
									Date endTime = DateUtils.parseDate(endTimeSet[0]+endTimeSet[1]+endTimeSet[2]+"235959", TIME_GIVING_MONTHLY_DATE_FORMAT);
									if(startTime.before(endTime) && now.after(startTime) && now.before(endTime)){
										Map<String,Object> result = new HashMap<String,Object>();
										result.put("monthlyId", Integer.valueOf(monthlyChildrenSet[0].trim()));
										result.put("startTime", startTime);
										result.put("endTime", endTime);
										return result;
									}
								}			
							}
						}
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
