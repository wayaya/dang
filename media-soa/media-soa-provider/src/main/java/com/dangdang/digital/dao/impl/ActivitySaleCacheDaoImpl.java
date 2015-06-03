package com.dangdang.digital.dao.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IActivitySaleCacheDao;
import com.dangdang.digital.dao.IActivitySaleDao;
import com.dangdang.digital.model.ActivitySale;
import com.dangdang.digital.model.ActivitySaleCacheVo;
import com.dangdang.digital.utils.MediaCacheBeanUtils;

@Repository(value = "activitySaleCacheDao")
public class ActivitySaleCacheDaoImpl implements IActivitySaleCacheDao {

	@Resource
	private IActivitySaleDao activitySaleDao;

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, ActivitySaleCacheVo> masterRedisTemplateForBasicVo;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, ActivitySaleCacheVo> slaveRedisTemplateForWholeVo;

	@Resource(name = "systemApi")
	ISystemApi systemApi;

	@Override
	public List<ActivitySaleCacheVo> getActivitySaleCacheByActivityId(Integer activityId) {
		Set<String> keys = slaveRedisTemplateForWholeVo
				.keys(Constans.CACHE_KEY_PREFIX_ACTIVITY_SALE + activityId + "*");
		if (CollectionUtils.isNotEmpty(keys)) {
			return slaveRedisTemplateForWholeVo.opsForValue().multiGet(keys);
		} else {
			return null;
		}
	}

	@Override
	public List<ActivitySaleCacheVo> getActivitySaleCacheBySaleId(Long saleId) {
		Set<String> keys = slaveRedisTemplateForWholeVo.keys(Constans.CACHE_KEY_PREFIX_ACTIVITY_SALE + "*" + saleId);
		if (CollectionUtils.isNotEmpty(keys)) {
			return slaveRedisTemplateForWholeVo.opsForValue().multiGet(keys);
		} else {
			return null;
		}
	}

	@Override
	public void setActivitySaleCacheByActivityId(Integer activityId) {
		List<ActivitySale> activitySaleList = activitySaleDao.getByActivityIdInUse(activityId);
		if (CollectionUtils.isEmpty(activitySaleList)) {
			return;
		}
		Map<String, ActivitySaleCacheVo> cacheMap = new HashMap<String, ActivitySaleCacheVo>();
		for (ActivitySale activitySale : activitySaleList) {
			ActivitySaleCacheVo activitySaleCacheVo = new ActivitySaleCacheVo();
			MediaCacheBeanUtils.copyActivitySaleToActivitySaleCacheVo(activitySale, activitySaleCacheVo);
			cacheMap.put(Constans.CACHE_KEY_PREFIX_ACTIVITY_SALE + activityId + "_" + activitySale.getSaleId(),
					activitySaleCacheVo);
		}
		masterRedisTemplateForBasicVo.opsForValue().multiSet(cacheMap);

	}

	@Override
	public void mSetActivitySaleCacheByActivityId(List<Integer> activityIdList) {
		if (CollectionUtils.isEmpty(activityIdList)) {
			return;
		}
		List<ActivitySale> activitySaleList = activitySaleDao.getByActivityIdListInUse(activityIdList);
		if (CollectionUtils.isEmpty(activitySaleList)) {
			return;
		}
		Map<String, ActivitySaleCacheVo> cacheMap = new HashMap<String, ActivitySaleCacheVo>();
		for (ActivitySale activitySale : activitySaleList) {
			ActivitySaleCacheVo activitySaleCacheVo = new ActivitySaleCacheVo();
			MediaCacheBeanUtils.copyActivitySaleToActivitySaleCacheVo(activitySale, activitySaleCacheVo);
			cacheMap.put(
					Constans.CACHE_KEY_PREFIX_ACTIVITY_SALE + activitySale.getActivityId() + "_"
							+ activitySale.getSaleId(), activitySaleCacheVo);
		}
		masterRedisTemplateForBasicVo.opsForValue().multiSet(cacheMap);

	}

	@Override
	public void deleteActivitySaleCacheByActivityId(Integer activityId) {
		Set<String> keys = masterRedisTemplateForBasicVo.keys(Constans.CACHE_KEY_PREFIX_ACTIVITY_SALE + activityId
				+ "*");
		if (CollectionUtils.isNotEmpty(keys)) {
			masterRedisTemplateForBasicVo.delete(keys);
		}
	}

	@Override
	public void mDeleteActivitySaleCacheByActivityId(List<Integer> activityIdList) {
		if (CollectionUtils.isEmpty(activityIdList)) {
			return;
		}
		Set<String> keys = new HashSet<String>();
		for (Integer activityId : activityIdList) {
			keys.addAll(masterRedisTemplateForBasicVo.keys(Constans.CACHE_KEY_PREFIX_ACTIVITY_SALE + activityId + "*"));
		}
		if (CollectionUtils.isNotEmpty(keys)) {
			masterRedisTemplateForBasicVo.delete(keys);
		}
	}

	@Override
	public void deleteActivitySaleCacheBySaleId(Long saleId) {
		Set<String> keys = masterRedisTemplateForBasicVo.keys(Constans.CACHE_KEY_PREFIX_ACTIVITY_SALE + "*" + saleId);
		if (CollectionUtils.isNotEmpty(keys)) {
			masterRedisTemplateForBasicVo.delete(keys);
		}
	}

	@Override
	public void mDeleteActivitySaleCacheBySaleId(List<Long> saleIdList) {
		if (CollectionUtils.isEmpty(saleIdList)) {
			return;
		}
		Set<String> keys = new HashSet<String>();
		for (Long saleId : saleIdList) {
			keys.addAll(masterRedisTemplateForBasicVo.keys(Constans.CACHE_KEY_PREFIX_ACTIVITY_SALE + "*" + saleId));
		}
		if (CollectionUtils.isNotEmpty(keys)) {
			masterRedisTemplateForBasicVo.delete(keys);
		}
	}

}
