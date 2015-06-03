package com.dangdang.digital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IActivityCacheDao;
import com.dangdang.digital.dao.IActivityInfoDao;
import com.dangdang.digital.model.ActivityCacheVo;
import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.utils.MediaCacheBeanUtils;

@Repository(value = "activityCacheDao")
public class ActivityCacheDaoImpl implements IActivityCacheDao {

	@Resource
	private IActivityInfoDao activityInfoDao;

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, ActivityCacheVo> masterRedisTemplate;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, ActivityCacheVo> slaveRedisTemplate;

	@Resource(name = "systemApi")
	ISystemApi systemApi;

	@Override
	@Cacheable(value = "activity", key = "'activity_info_'+#activityId")
	public ActivityCacheVo getActivityCacheVo(Integer activityId) {
		ActivityInfo activityInfo = activityInfoDao
				.selectMasterOne("ActivityInfoMapper.selectByPrimaryKey", activityId);
		if (activityInfo == null) {
			return null;
		}
		ActivityCacheVo activityCacheVo = new ActivityCacheVo();
		MediaCacheBeanUtils.copyActivityInfoToActivityCacheVo(activityInfo, activityCacheVo);
		return activityCacheVo;
	}

	@Override
	@CacheEvict(value = "activity", key = "'activity_info_'+#activityId")
	public void deleteActivityCacheVo(Integer activityId) {
	}

	@Override
	public List<ActivityCacheVo> mGetActivityCacheVo(List<Integer> activityIds) {
		List<String> keys = MediaCacheBeanUtils.getKeysByPrefixIneger(Constans.CACHE_KEY_PREFIX_ACTIVITY_INFO, activityIds);
		if (keys != null && keys.size() > 0) {
			return slaveRedisTemplate.opsForValue().multiGet(keys);
		}
		return null;
	}

	@Override
	public void mSetActivityCacheVo(List<Integer> activityIdList) {
		if (CollectionUtils.isEmpty(activityIdList)) {
			return;
		}
		List<ActivityInfo> activityInfoList = (List<ActivityInfo>) activityInfoDao.selectMasterList(
				"ActivityInfoMapper.selectByIds", activityIdList);
		if (CollectionUtils.isEmpty(activityInfoList)) {
			return;
		}
		String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_ACTIVITY_KEY,
				String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_ACTIVITY));
		Map<String, ActivityCacheVo> cacheMap = new HashMap<String, ActivityCacheVo>();
		if (!CollectionUtils.isEmpty(activityInfoList)) {
			for (ActivityInfo activity : activityInfoList) {
				if (activity == null) {
					continue;
				}
				ActivityCacheVo cacheVo = new ActivityCacheVo();
				MediaCacheBeanUtils.copyActivityInfoToActivityCacheVo(activity, cacheVo);
				cacheMap.put(Constans.CACHE_KEY_PREFIX_ACTIVITY_INFO + activity.getActivityId(), cacheVo);
			}
			masterRedisTemplate.opsForValue().multiSet(cacheMap);
			for (String key : cacheMap.keySet()) {
				masterRedisTemplate.expire(key, Integer.valueOf(expireTime), TimeUnit.SECONDS);
			}

		}
	}

	@Override
	public void mDeleteCacheBasicVo(List<Integer> activityIds) {
		List<String> keys = MediaCacheBeanUtils.getKeysByPrefixIneger(Constans.CACHE_KEY_PREFIX_ACTIVITY_INFO, activityIds);
		if (keys != null && keys.size() > 0) {
			masterRedisTemplate.delete(keys);
		}
	}

	@Override
	public void cleanActivityCache() {
		Set<String> keys = masterRedisTemplate.keys(Constans.CACHE_KEY_PREFIX_ACTIVITY_INFO + "*");
		masterRedisTemplate.delete(keys);
	}

}
