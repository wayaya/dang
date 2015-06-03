package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.dangdang.digital.service.IRedisQueryService;

@Service
public class RedisQueryServiceImpl implements IRedisQueryService{

	@Resource(name="masterRedisTemplate")
    protected RedisTemplate<String, Object> masterRedisTemplate;
	
	@Resource(name="slaveRedisTemplate")
    protected RedisTemplate<String, Object> slaveRedisTemplate;
	
	
	@Override
	public Object getMasterValue(String key){
		
		return masterRedisTemplate.opsForValue().get(key);
	}
	
	@Override
	public Object getSlaveValue(String key){
		
		return slaveRedisTemplate.opsForValue().get(key);
	}
	
}
