/**
 * Description: UserMonthlyServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年12月6日 上午11:25:24  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.constant.ActivityTypeEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IUserMonthlyDao;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.model.UserMonthly;
import com.dangdang.digital.service.IActivityInfoService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.IUserMonthlyService;
import com.dangdang.digital.vo.BindUserMediaAuthorityVo;
import com.dangdang.digital.vo.UserMonthlyCacheVo;

/**
 * Description: All Rights Reserved.
 * 
 * @version 1.0 2014年12月6日 上午11:25:24 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class UserMonthlyServiceImpl extends BaseServiceImpl<UserMonthly, Long>
		implements IUserMonthlyService {

	@Resource
	private IUserMonthlyDao userMonthlyDao;
	@Resource
	private ICacheService cacheService;
	@Resource
	IActivityInfoService activityInfoService;
	
	@Override
	public IBaseDao<UserMonthly> getBaseDao() {
		return userMonthlyDao;
	}

	@Override
	public UserMonthly findUserMonthlyNowByCustId(Long custId, Long mediaId,
			List<Long> chapterIds) {
		UserMonthlyCacheVo userMonthlyCacheVo = cacheService.getUserMonthlyCacheVoByCustId(custId);
		if(userMonthlyCacheVo == null){
			return null;
		}
		Map<String, UserMonthly> map = userMonthlyCacheVo.getUserMonthlys();
		UserMonthly result = getMonthlyEndTime(map.values());
		if(CollectionUtils.isEmpty(map) || result == null || result.getMonthlyEndTime() == null){
			UserMonthlyCacheVo newUserMonthlyCacheVo = userMonthlyDao.findUserMonthlyCacheVoByCustId(custId);
			if(newUserMonthlyCacheVo != null){
				result = getMonthlyEndTime(newUserMonthlyCacheVo.getUserMonthlys().values());
				cacheService.updateUserMonthlyCacheVo(newUserMonthlyCacheVo);				
			}else{
				return null;
			}
		}		
		return result;
	}
	
	private UserMonthly getMonthlyEndTime(Collection<UserMonthly> userMonthlys){
		if (CollectionUtils.isEmpty(userMonthlys)) {
			return null;
		}
		UserMonthly result = new UserMonthly();
		Date now = new Date();
		Date lastDate = now;
		for (UserMonthly userMonthly : userMonthlys) {
			if (userMonthly.getMonthlyType().shortValue() == Constans.MONTHLY_PAYMENT_TYPE_ALL
					&& userMonthly.getMonthlyEndTime().getTime() > lastDate
							.getTime()) {
				lastDate = userMonthly.getMonthlyEndTime();
			}
			if(userMonthly.getFirstLoginGiving() != null && userMonthly.getFirstLoginGiving() == Constans.FIRST_LOGIN_GIVING_YES){
				result.setFirstLoginGiving(Constans.FIRST_LOGIN_GIVING_YES);
			}
		}
		if(lastDate.getTime() > now.getTime()){
			result.setMonthlyEndTime(lastDate);
		}
		if(result.getFirstLoginGiving() == null){
			result.setFirstLoginGiving(Constans.FIRST_LOGIN_GIVING_NOT);
		}
		return result;
	}

	@Override
	public List<UserMonthly> selectUserMonthly(Long custId) {
		UserMonthlyCacheVo userMonthlyCacheVo = cacheService.getUserMonthlyCacheVoByCustId(custId);
		if(userMonthlyCacheVo == null){
			return null;
		}
		List<UserMonthly> userMonthlys = new ArrayList<UserMonthly>();
		Date now = new Date();
		for(UserMonthly userMonthly : userMonthlyCacheVo.getUserMonthlys().values()){
			if(userMonthly.getMonthlyEndTime().getTime() > now.getTime()){
				userMonthlys.add(userMonthly);
			}
		}
		return userMonthlys.isEmpty() ? null : userMonthlys;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMonthly bindUserMonthlyAuthority(BindUserMediaAuthorityVo vo) throws Exception{
		ActivityInfo activityInfo = activityInfoService.findUniqueByParams("activityId",vo.getMonthlyId());
		long now = new Date().getTime();
		if (activityInfo != null
				&& ActivityTypeEnum.MONTHLY.getKey() == activityInfo
						.getActivityTypeId()
				&& now > activityInfo.getStartTime().getTime()
				&& now < activityInfo.getEndTime().getTime()
				&& activityInfo.getStatus() != null
				&& Constans.ACTIVITYINFO_STATUS_VALID == activityInfo
						.getStatus()) {
			int monthlyDays = 0;
			String monthlyPaymentRelation = StringUtils.isBlank(activityInfo.getMonthlyPaymentRelation()) ? null : activityInfo.getMonthlyPaymentRelation().trim();
			if(activityInfo.getMonthlyBuyDays() != null){
				monthlyDays += activityInfo.getMonthlyBuyDays();
			}
			if(activityInfo.getMonthlyGiveDays() != null){
				monthlyDays += Integer.valueOf(activityInfo.getMonthlyGiveDays());
			}			
			UserMonthly userMonthly = this.findMasterUserMonthly(
					Short.valueOf(activityInfo.getMonthlyPaymentType()),
					vo.getCustId(), monthlyPaymentRelation);
			if(userMonthly != null){
				if (userMonthly.getFirstLoginGiving() != null
						&& userMonthly.getFirstLoginGiving() == Constans.FIRST_LOGIN_GIVING_YES
						&& vo.getFirstLoginGiving() != null
						&& vo.getFirstLoginGiving() == Constans.FIRST_LOGIN_GIVING_YES) {
					throw new MediaException("用户已参加过首次登陆送包月活动，更新数据库用户包月信息失败！");
				}
				if (userMonthly.getFirstShareGiving() != null
						&& userMonthly.getFirstShareGiving() == Constans.FIRST_SHARE_GIVING_YES
						&& vo.getFirstShareGiving() != null
						&& vo.getFirstShareGiving() == Constans.FIRST_SHARE_GIVING_YES) {
					throw new MediaException("用户已参加过首次分享送包月活动，更新数据库用户包月信息失败！");
				}
				if (vo.getShareGivingTimeStart() != null
						&& vo.getShareGivingTimeEnd() != null
						&& userMonthly.getShareGivingTime() != null
						&& userMonthly.getShareGivingTime().after(
								vo.getShareGivingTimeStart())
						&& userMonthly.getShareGivingTime().before(
								vo.getShareGivingTimeEnd())) {
					throw new MediaException("用户已参加过限时分享送包月活动，更新数据库用户包月信息失败！");
				}
				userMonthly.setOperateVersion(userMonthly.getOperateVersion() + 1);
				if(vo.getFirstLoginGiving() != null && vo.getFirstLoginGiving() == Constans.FIRST_LOGIN_GIVING_YES){
					userMonthly.setFirstLoginGiving(Constans.FIRST_LOGIN_GIVING_YES);
					userMonthly.setFirstLoginGivingDays(monthlyDays);
				}
				if(vo.getFirstShareGiving() != null && vo.getFirstShareGiving() == Constans.FIRST_SHARE_GIVING_YES){
					userMonthly.setFirstShareGiving(Constans.FIRST_SHARE_GIVING_YES);
					userMonthly.setFirstShareGivingDays(monthlyDays);
				}
				if(vo.getShareGivingTimeStart() != null && vo.getShareGivingTimeEnd() != null){
					userMonthly.setFirstShareGiving(Constans.FIRST_SHARE_GIVING_YES);
					userMonthly.setFirstShareGivingDays(monthlyDays);					
					userMonthly.setTimeShareGivingDays(monthlyDays);
					userMonthly.setShareGivingTime(new Date());
				}
				Map<String, Object> map = this.convertBeanToMap(userMonthly);
				map.put("monthlyDays", monthlyDays);
				if(monthlyDays < 1){
					throw new MediaException("更新数据库用户包月信息失败，包月天数小于一天！");
				}
				int result = userMonthlyDao.updateUserMonthly(map);
				if(result == 0){
					throw new MediaException("更新数据库用户包月信息失败！");
				}
			}else{
				userMonthly = new UserMonthly();
				userMonthly.setCustId(vo.getCustId());
				userMonthly.setMonthlyType(Short.valueOf(activityInfo.getMonthlyPaymentType()));
				userMonthly.setOperateVersion(1);
				userMonthly.setMonthlyPaymentRelation(monthlyPaymentRelation);
				if(vo.getFirstLoginGiving() != null && vo.getFirstLoginGiving() == Constans.FIRST_LOGIN_GIVING_YES){
					userMonthly.setFirstLoginGiving(Constans.FIRST_LOGIN_GIVING_YES);
					userMonthly.setFirstLoginGivingDays(monthlyDays);
				}
				if(vo.getFirstShareGiving() != null && vo.getFirstShareGiving() == Constans.FIRST_SHARE_GIVING_YES){
					userMonthly.setFirstShareGiving(Constans.FIRST_SHARE_GIVING_YES);
					userMonthly.setFirstShareGivingDays(monthlyDays);
				}
				if(vo.getShareGivingTimeStart() != null && vo.getShareGivingTimeEnd() != null){
					userMonthly.setFirstShareGiving(Constans.FIRST_SHARE_GIVING_YES);
					userMonthly.setFirstShareGivingDays(monthlyDays);
					userMonthly.setTimeShareGivingDays(monthlyDays);
					userMonthly.setShareGivingTime(new Date());
				}
				userMonthly.setMonthlyStartTime(new Date());
				userMonthly.setMonthlyEndTime(DateUtils.addDays(userMonthly.getMonthlyStartTime(), monthlyDays));
				Map<String, Object> map = this.convertBeanToMap(userMonthly);
				map.put("monthlyDays", monthlyDays);
				if(monthlyDays < 1){
					throw new MediaException("更新数据库用户包月信息失败，包月天数小于一天！");
				}
				int result = userMonthlyDao.insertUserMonthly(map);
				if(result == 0){
					throw new MediaException("插入数据库用户包月信息失败！");
				}
			}
			userMonthly.setMonthlyDays(monthlyDays);
			return userMonthly;
		}else{
			throw new MediaException("该包月不可购买！custId:"+vo.getCustId()+",monthlyId:"+vo.getMonthlyId());
		}
	}

	@Override
	public int insertUserMonthly(Map<String, Object> map) {
		return userMonthlyDao.insertUserMonthly(map);
	}

	@Override
	public int updateUserMonthly(Map<String, Object> map) {
		return userMonthlyDao.updateUserMonthly(map);
	}

	@Override
	public UserMonthly findMasterUserMonthly(Short monthlyType,Long custId,String monthlyPaymentRelation) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("monthlyType", monthlyType);
		map.put("custId", custId);
		map.put("monthlyPaymentRelation", monthlyPaymentRelation);
		return userMonthlyDao.findMasterUserMonthly(map);
	}
	
	
}
