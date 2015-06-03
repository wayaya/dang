package com.dangdang.digital.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICustomerMediaHistoryDao;
import com.dangdang.digital.model.CustomerMediaHistory;
import com.dangdang.digital.service.ICustomerMediaHistoryService;
import com.dangdang.digital.utils.DateUtil;

@Service
@Transactional
public class CustomerMediaHistoryServiceImpl extends BaseServiceImpl<CustomerMediaHistory, Long>
				implements ICustomerMediaHistoryService{
	@Resource
	ICustomerMediaHistoryDao customerMediaHistoryDao;
	
	@Resource(name="masterRedisTemplate")
    protected RedisTemplate<String, Object> masterRedisTemplate;
	
	@Resource(name="slaveRedisTemplate")
    protected RedisTemplate<String, Object> slaveRedisTemplate;

	
	@Override
	public IBaseDao<CustomerMediaHistory> getBaseDao() {
		return customerMediaHistoryDao;
	}

	@Override
	public void saveHistory(CustomerMediaHistory history) {
		
		String key = getHistoryKey(history);
		this.save(history);
		setValueToRedis(history, key);
	}
	
	@Override
	public void updateHistory(CustomerMediaHistory history) {
		
		String key = getHistoryKey(history);
		this.update(history);
		setValueToRedis(history, key);
	}

	@Override
	public CustomerMediaHistory getHistory(CustomerMediaHistory history) {
		
		String key = getHistoryKey(history);
		CustomerMediaHistory result = (CustomerMediaHistory)slaveRedisTemplate.opsForValue().get(key);
		if(result==null){
			List<CustomerMediaHistory> list = this.findListByParamsObjs(history);
			if(list!=null && list.size()>0){
				result = list.get(0);
				setValueToRedis(result, key);
			}
		}
		return result;
	}
	
	private void setValueToRedis(CustomerMediaHistory history, String key) {
		masterRedisTemplate.delete(key);
		masterRedisTemplate.opsForValue().set(key, history);
		masterRedisTemplate.expire(key, 24*2, TimeUnit.HOURS);
	}
	
	private String getHistoryKey(CustomerMediaHistory history) {
		StringBuilder key = new StringBuilder(Constans.RECOMMEND_CUSTOMER_HISTORY_KEY);
		key.append(history.getCustId());
		key.append("_");
		key.append(history.getType());
		key.append(DateUtil.format1(history.getCreationDate()));
		return key.toString();
	}

}
