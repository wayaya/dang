package com.dangdang.digital.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.ISpecialTopicCacheDao;
import com.dangdang.digital.model.SpecialTopic;

/**
 * Description: All Rights Reserved.
 * 
 * @version 1.0 2015年3月19日 下午7:18:35 by
 *          yangzhenping（yangzhenping@dangdang.com）创建
 */
@Repository(value = "specialTopicCacheDao")
public class SpecialTopicCacheDaoImpl extends BaseDaoImpl<SpecialTopic> implements ISpecialTopicCacheDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpecialTopicCacheDaoImpl.class);

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, SpecialTopic> masterSTRedisTemplate;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, SpecialTopic> slaveSTRedisTemplate;

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<SpecialTopic>> masterSTListRedisTemplate;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, List<SpecialTopic>> slaveSTListRedisTemplate;

	@Resource
	private ThreadPoolTaskExecutor taskExecutor;

	@Override
	public SpecialTopic getSpecialTopicFromCache(Integer stId) {
		String cacheKey = Constans.SPECIAL_TOPIC_CACHE_KEY + stId;
		if (slaveSTRedisTemplate.hasKey(cacheKey)) {
			return slaveSTRedisTemplate.opsForValue().get(cacheKey);
		} else {
			SpecialTopic specialTopic = this.selectOne("SpecialTopicMapper.selectByPrimaryKey", stId);
			setSpecialTopicToCache(specialTopic);
			return specialTopic;
		}
	}

	@Override
	public void setSpecialTopicToCache(final SpecialTopic specialTopic) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				if (specialTopic != null) {
					masterSTRedisTemplate.opsForValue().set(Constans.SPECIAL_TOPIC_CACHE_KEY + specialTopic.getStId(), specialTopic);
					masterSTRedisTemplate.expire(Constans.SPECIAL_TOPIC_CACHE_KEY + specialTopic.getStId(),
							Constans.CACHE_EXPIRE_TIME_OF_SPECIAL_TOPIC, TimeUnit.SECONDS);
					LOGGER.info("添加专题" + specialTopic.getStId() + "到缓存");
				}
			}
		});
	}

	public void deleteSpecialTopicFromCache(Set<String> cacheKeys) {
		LOGGER.info("删除专题缓存key" + cacheKeys);
		masterSTRedisTemplate.delete(cacheKeys);
	}

	@Override
	public List<SpecialTopic> getHomePageSTListFromCache(final String deviceType, final String channelType) {
		final String cacheKey = Constans.HOME_PAGE_ST_LIST_CACHE_KEY + deviceType.toLowerCase() + "_" + channelType.toLowerCase();
		if (slaveSTListRedisTemplate.hasKey(cacheKey)) {
			return slaveSTListRedisTemplate.opsForValue().get(cacheKey);
		} else {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("deviceType", deviceType);
			paramMap.put("channelTypes", channelType);
			final List<SpecialTopic> specialTopicList = this.selectList("SpecialTopicMapper.getHomePageSpecialTopicList", paramMap);
			taskExecutor.execute(new Runnable() {
				public void run() {
					if (specialTopicList.size() > 0) {
						masterSTListRedisTemplate.opsForValue().set(cacheKey, specialTopicList);
						masterSTListRedisTemplate.expire(cacheKey, Constans.CACHE_EXPIRE_TIME_OF_HOME_PAGE_ST_LIST, TimeUnit.SECONDS);
						LOGGER.info("添加首页专题到缓存");
					}
				}
			});
			return specialTopicList;
		}
	}

}
