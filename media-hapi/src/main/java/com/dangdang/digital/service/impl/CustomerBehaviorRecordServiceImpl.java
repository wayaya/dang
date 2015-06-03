package com.dangdang.digital.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICustomerBehaviorRecordDao;
import com.dangdang.digital.model.CustomerBehaviorRecord;
import com.dangdang.digital.service.ICustomerBehaviorRecordService;
import com.dangdang.digital.utils.DateUtil;

@Service
public class CustomerBehaviorRecordServiceImpl extends BaseServiceImpl<CustomerBehaviorRecord, Long> implements ICustomerBehaviorRecordService{

	@Resource
	ICustomerBehaviorRecordDao customerBehaviorRecordDao;
	
	@Override
	public IBaseDao<CustomerBehaviorRecord> getBaseDao() {
		return customerBehaviorRecordDao;
	}
	
	@Resource(name="masterRedisTemplate")
    protected RedisTemplate<String, Object> masterRedisTemplate;
	
	@Resource(name="slaveRedisTemplate")
    protected RedisTemplate<String, Object> slaveRedisTemplate;

	@Override
	public void saveRecord(CustomerBehaviorRecord record) {
		
		String redisKey = Constans.RECOMMEND_CUSTOMER_BEHAVIOUR_KEY+record.getCustId()+DateUtil.format1(record.getCreateDate());
		this.save(record);
		masterRedisTemplate.delete(redisKey);
		masterRedisTemplate.opsForValue().set(redisKey, record);
		masterRedisTemplate.expire(redisKey, 24*2, TimeUnit.HOURS);
	}
	
	@Override
	public void updateRecord(CustomerBehaviorRecord custRecord) {
		String redisKey = Constans.RECOMMEND_CUSTOMER_BEHAVIOUR_KEY+custRecord.getCustId()+DateUtil.format1(custRecord.getCreateDate());
		this.update(custRecord);
		masterRedisTemplate.delete(redisKey);
		masterRedisTemplate.opsForValue().set(redisKey, custRecord);
		masterRedisTemplate.expire(redisKey, 24*2, TimeUnit.HOURS);
	}
	
	@Override
	public CustomerBehaviorRecord getRecord(CustomerBehaviorRecord query){
		
		CustomerBehaviorRecord result = null;
		String redisKey = Constans.RECOMMEND_CUSTOMER_BEHAVIOUR_KEY+query.getCustId()+DateUtil.format1(query.getCreateDate());
		result = (CustomerBehaviorRecord)slaveRedisTemplate.opsForValue().get(redisKey);
		
		if(result == null){
			
			List<CustomerBehaviorRecord> list = this.findListByParamsObjs(query);
			if(list == null || list.size() ==0){
				return null;
			}else{
				
				result = list.get(0);
				if(result != null){
					masterRedisTemplate.opsForValue().set(redisKey, result);
					masterRedisTemplate.expire(redisKey, 24*2, TimeUnit.HOURS);
					return result;
				}
			}
		}
		
		return result;
	}
	
}
