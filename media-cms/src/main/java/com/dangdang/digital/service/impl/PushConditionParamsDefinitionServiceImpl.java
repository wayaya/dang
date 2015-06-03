package com.dangdang.digital.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IPushConditionParamsDefinitionDao;
import com.dangdang.digital.model.PushConditionParamsDefinition;
import com.dangdang.digital.service.IPushConditionParamsDefinitionService;

@Service
public class PushConditionParamsDefinitionServiceImpl extends BaseServiceImpl<PushConditionParamsDefinition, Long> implements IPushConditionParamsDefinitionService{

	@Resource
	IPushConditionParamsDefinitionDao pushConditionParamsDefinitionDao;
	
	@Override
	public IBaseDao<PushConditionParamsDefinition> getBaseDao() {
		return pushConditionParamsDefinitionDao;
	}
	
	@Resource(name="masterRedisTemplate")
    protected RedisTemplate<String, Object> masterRedisTemplate;
	
	@Resource(name="slaveRedisTemplate")
    protected RedisTemplate<String, Object> slaveRedisTemplate;
	
	@Override
	public PushConditionParamsDefinition getByIdAndToCache(Long id) {
		
		String key = Constans.CLOUD_PUSH_CONDITION_PARAM_DEFINITION_PREFIX+id;
		
		PushConditionParamsDefinition conditionParamsDef = (PushConditionParamsDefinition)slaveRedisTemplate.opsForValue().get(key);
		if(conditionParamsDef == null){
			
			conditionParamsDef = this.get(id);
			if(conditionParamsDef!=null){
				masterRedisTemplate.opsForValue().set(key, conditionParamsDef);
				masterRedisTemplate.expire(key, 2, TimeUnit.HOURS);
			}
		}
		return conditionParamsDef;
	}

}
