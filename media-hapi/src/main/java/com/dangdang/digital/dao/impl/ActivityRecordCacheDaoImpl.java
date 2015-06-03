package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IActivityRecordCacheDao;
import com.dangdang.digital.vo.ReturnActivityVo;

/**
 * Description:该用户活动参与cache dao 实现类
 * 
 * @version 1.0 2014年11月19日 下午2:22:34 by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Repository
public class ActivityRecordCacheDaoImpl implements IActivityRecordCacheDao {

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<ReturnActivityVo>> masterRedisList;
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, List<ReturnActivityVo>> slaveRedisList;
	
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Integer> masterRedisRed;
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, Integer> slaveRedisRed;


	@Override
	public List<ReturnActivityVo> getUserActivityRecordCache(Long custId,int type,int prizeType) {
		String prizeTypeStr = "";
		if(0!=prizeType){
			prizeTypeStr = prizeType+"";
		}
		return slaveRedisList.opsForValue().get(Constans.MEDIA_USER_ACTIVITYS_KEY + custId+"_"+type+"_"+prizeTypeStr);
	}

	@Override
	public void setUserActivityRecordCache(Long custId,int type,Integer prizeType,
			List<ReturnActivityVo> list) {
		String prizeTypeStr = "";
		if(0!=prizeType){
			prizeTypeStr = prizeType+"";
		}
		int expireTime = Constans.CACHE_EXPIRE_TIME_OF_USER_ACTIVITYS;
		masterRedisList.opsForValue().set(Constans.MEDIA_USER_ACTIVITYS_KEY + custId+"_"+type+"_"+prizeTypeStr, list, expireTime, TimeUnit.SECONDS);
	}
	@Override
	public void deleteUserActivityRecordCache(Long custId,int type) {
		Set<String> keys = masterRedisList.keys(Constans.MEDIA_USER_ACTIVITYS_KEY + custId+"_"+type+"*");
		masterRedisList.delete(keys);
	}

	@Override
	public List<ReturnActivityVo> getLatestActivityRecordCache(
			int activityType) {
		return slaveRedisList.opsForValue().get(Constans.MEDIA_ACTIVITY_RECORDS_CACHE_KEY +activityType);
	}

	@Override
	public void setLatestActivityRecordCache(int activityType,
			List<ReturnActivityVo> list) {
		int expireTime = Constans.CACHE_EXPIRE_TIME_OF_ACTIVITY_RECORDS;
		masterRedisList.opsForValue().set(Constans.MEDIA_ACTIVITY_RECORDS_CACHE_KEY +activityType, list, expireTime, TimeUnit.SECONDS);
	}

	@Override
	public void deleteLatestActivityRecordCache(int activityType) {
		masterRedisList.delete(Constans.MEDIA_ACTIVITY_RECORDS_CACHE_KEY +activityType);
	}

	@Override
	public Integer getGettedRedPacketCoins(Long custId, Long redPacketId) {
		//System.out.println(slaveRedisRed.getExpire(Constans.MEDIA_RED_PACKET_CUSTID_COINS_CACHE_KEY +custId+"_"+redPacketId));
		return slaveRedisRed.opsForValue().get(Constans.MEDIA_RED_PACKET_CUSTID_COINS_CACHE_KEY +custId+"_"+redPacketId);
	}

	@Override
	public void setGettedRedPacketCoins(Long custId, Long redPacketId, int coins) {
		int expireTime = Constans.CACHE_EXPIRE_TIME_OF_MEDIA_RED_PACKET_CUSTID_COINS;
		masterRedisRed.opsForValue().set(Constans.MEDIA_RED_PACKET_CUSTID_COINS_CACHE_KEY+custId+"_"+redPacketId, coins, expireTime, TimeUnit.SECONDS);
		
	}

	@Override
	public void deleteGettedRedPacketCoins(Long custId, Long redPacketId) {
		masterRedisRed.delete(Constans.MEDIA_RED_PACKET_CUSTID_COINS_CACHE_KEY+custId+"_"+redPacketId);
	}
	
}
