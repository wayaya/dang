package com.dangdang.digital.api.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.service.ISysPropertiesService;

@Component("systemApi")
public class SystemPropertiesApiImpl implements ISystemApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemPropertiesApiImpl.class);

	/**
	 * add by lvxiang ,放入Id列表操作
	 */
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, String> masterRedisTemplate;
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, String> slaveRedisTemplate;
	
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	
	
	@Resource
	private ISysPropertiesService service;

	@Override
	public String getProperty(final String key,final  String defaultValue) {
		final String cacheKey = Constans.SYSTEM_PROPERTY_KEY+key.toLowerCase() ;
		if(slaveRedisTemplate.hasKey(cacheKey)){
			String value =  slaveRedisTemplate.opsForValue().get(cacheKey);
			LOGGER.info("从缓存中读取系统参数(key=" + key + ",value="+ value +" )");
			return value;
		}
		final String value = service.getValue(key);
		if (value == null || value.trim().isEmpty()) {
			return defaultValue;
		}else{
			//只有当系统参数配置表中有时才才缓存
			taskExecutor.execute(new Runnable() {
				public void run() {
					masterRedisTemplate.opsForValue().set(cacheKey, value);
					masterRedisTemplate.expire(cacheKey, Integer.valueOf(Constans.CACHE_EXPIRE_TIME_OF_SYSTEM_PROPERTY),
							TimeUnit.SECONDS);
					LOGGER.info("异步刷系统参数(key=" + key + ",value="+ value +" )");
				}
			});
		}
		return value;
	}

	@Override
	public String getProperty(String key) {
		return getProperty(key,null);
	}

}
