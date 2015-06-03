package com.dangdang.digital.dao.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IActivityUserCacheDao;
import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.vo.ReturnActivityUserVo;

/**
 * Description:福袋用户信息dao实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年11月19日 下午2:22:34 by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Repository
public class ActivityUserCacheDaoImpl extends BaseDaoImpl<ActivityUser>
		implements IActivityUserCacheDao {

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Map<String, String>> masterRedisTemplate;
	
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, ReturnActivityUserVo> masterRedisTemplateUser;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, Map<String, String>> slaveRedisTemplate;
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, ReturnActivityUserVo> slaveRedisTemplateUser;

	@Override
	public Map<String, String> getTodayActivityUserCache(Long custId) throws Exception {
		int expireTime = DateUtil.getSecondsToNextDay();
		masterRedisTemplate.expire(Constans.MEDIA_ACTIVITY_TODAY_USER_CACHE_KEY + custId,expireTime, TimeUnit.SECONDS);
		return slaveRedisTemplate.opsForValue().get(
				Constans.MEDIA_ACTIVITY_TODAY_USER_CACHE_KEY + custId);
	}

	@Override
	public void setTodayActivityUserCache(Long custId, Map<String, String> map) throws Exception {
		int expireTime = DateUtil.getSecondsToNextDay();
		masterRedisTemplate.opsForValue().set(Constans.MEDIA_ACTIVITY_TODAY_USER_CACHE_KEY + custId, map,expireTime, TimeUnit.SECONDS);
		
	}
	
	@Override
	public void deleteTodayActivityUserCache(Long custId){
		masterRedisTemplate.delete(Constans.MEDIA_ACTIVITY_TODAY_USER_CACHE_KEY + custId);
	}


	@Override
	public ReturnActivityUserVo getActivityUserVoCache(Long custId) {
		//System.out.println(slaveRedisTemplateUser.getExpire(Constans.MEDIA_ACTIVITY_USER_KEY + custId));
		return slaveRedisTemplateUser.opsForValue().get(Constans.MEDIA_ACTIVITY_USER_KEY + custId);
	}

	@Override
	public void setActivityUserVoCache(Long custId,ReturnActivityUserVo vo) throws Exception {
		//int expireTime = Constans.CACHE_EXPIRE_TIME_OF_ACT_USER_KEY;
		int expireTime = DateUtil.getSecondsToNextDay();
		masterRedisTemplateUser.opsForValue().set(Constans.MEDIA_ACTIVITY_USER_KEY + custId,vo, expireTime, TimeUnit.SECONDS);
	}

	@Override
	public void deleteActivityUserVoCache(Long custId) {
		masterRedisTemplateUser.delete(Constans.MEDIA_ACTIVITY_USER_KEY + custId);
	}

	
}
