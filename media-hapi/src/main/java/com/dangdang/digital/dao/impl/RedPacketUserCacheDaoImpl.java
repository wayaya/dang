package com.dangdang.digital.dao.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IRedPacketUserCacheDao;
import com.dangdang.digital.utils.DateUtil;

/**
 * Description:福袋用户信息dao实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年11月19日 下午2:22:34 by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Repository
public class RedPacketUserCacheDaoImpl implements IRedPacketUserCacheDao {

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Integer> masterRedisTemplate;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, Integer> slaveRedisTemplate;

	@Override
	public Integer getRedPacketLefts(Long redPacketId) throws Exception {
		return slaveRedisTemplate.opsForValue().get(Constans.MEDIA_ACTIVITY_TODAY_USER_CACHE_KEY + redPacketId);
	}

	@Override
	public void setRedPacketLefts(Long redPacketId, Integer lefts)
			throws Exception {
		int expireTime = DateUtil.getSecondsToNextDay();
		masterRedisTemplate.opsForValue().set(Constans.MEDIA_RED_PACKET_LEFTS_CACHE_KEY + redPacketId, lefts,expireTime, TimeUnit.SECONDS);
	}

	@Override
	public void deleteRedPacketLefts(Long redPacketId) {
		masterRedisTemplate.delete(Constans.MEDIA_RED_PACKET_LEFTS_CACHE_KEY + redPacketId);
	}
	
}
