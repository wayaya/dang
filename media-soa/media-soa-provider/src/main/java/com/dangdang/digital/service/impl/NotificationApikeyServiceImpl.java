package com.dangdang.digital.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.INotificationApikeyDao;
import com.dangdang.digital.model.NotificationApikey;
import com.dangdang.digital.service.INotificationApikeyService;

@Service("notificationApikeyService")
public class NotificationApikeyServiceImpl extends BaseServiceImpl<NotificationApikey, Long> implements INotificationApikeyService{

	@Resource
	INotificationApikeyDao notificationApikeyDao; 
	
	@Resource(name="masterRedisTemplate")
    protected RedisTemplate<String, Object> masterRedisTemplate;
	
	@Resource(name="slaveRedisTemplate")
    protected RedisTemplate<String, Object> slaveRedisTemplate;
	
	
	@Override
	public IBaseDao<NotificationApikey> getBaseDao() {
		
		return notificationApikeyDao;
	}


	@Override
	public NotificationApikey findByAppId(Integer appid) {
		
		NotificationApikey cache = (NotificationApikey)slaveRedisTemplate.opsForValue().get(Constans.CLOUD_PUSH_API_KEY_PREFIX+appid);
		if(cache!=null){
			return cache;
		}
		
		NotificationApikey key = new NotificationApikey();
		key.setAppid(appid);
		List<NotificationApikey> list = this.findListByParamsObjs(key);
		if(list!=null && list.size()>0){
			NotificationApikey result = list.get(0);
			masterRedisTemplate.opsForValue().set(Constans.CLOUD_PUSH_API_KEY_PREFIX+appid, result);
			masterRedisTemplate.expire(Constans.CLOUD_PUSH_API_KEY_PREFIX+appid, 4, TimeUnit.HOURS);
			return result;
		}
		
		return null;
	}

}
