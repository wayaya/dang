package com.dangdang.digital.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IEbookConsRecordCacheDao;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.vo.ReturnEbookConsVo;

/**
 * Description: 电子书金币消费壕记录表的 cache dao接口 实现类
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午7:06:37  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Repository
public class EbookConsRecordCacheDaoImpl  implements IEbookConsRecordCacheDao {
	
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<ReturnEbookConsVo>> masterRedisTemplate;
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, List<ReturnEbookConsVo>> slaveRedisTemplate;
	
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<Long>> masterRedisTemplateListLong;
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, List<Long>> slaveRedisTemplateListLong;
	
	
	@Override
	public List<ReturnEbookConsVo> getEbookRewardedUsersCache(Long mediaId,String channel) {
		return slaveRedisTemplate.opsForValue().get(Constans.MEDIA_REWARDED_USER_CACHE_KEY+channel+"_"+mediaId);
	}

	@Override
	public void setEbookRewardedUsersCache(Long mediaId, String channel,List<ReturnEbookConsVo> list) throws Exception {
		//改成不是总的，是昨天的前n了
		//int expireTime = Constans.CACHE_EXPIRE_TIME_OF_REWARDED_USER;
		int expireTime = DateUtil.getSecondsToNextDay();
		masterRedisTemplate.opsForValue().set(Constans.MEDIA_REWARDED_USER_CACHE_KEY+channel+"_"+mediaId, list, expireTime, TimeUnit.SECONDS);
	}

	@Override
	public void deleteEbookRewardedUsersCache(Long mediaId,String channel) {
		masterRedisTemplate.delete(Constans.MEDIA_REWARDED_USER_CACHE_KEY+channel+"_"+mediaId);
	}
	
	@Override
	public List<Long> getUserRewardBooksIdsCache(Long custId) {
		return slaveRedisTemplateListLong.opsForValue().get(Constans.MEDIA_REWARD_SALE_IDS_CACHE_KEY + custId);
	}

	@Override
	public void setUserRewardBooksIdsCache(Long custId, List<Long> mediaIds) {
		int expireTime = Constans.CACHE_EXPIRE_TIME_OF_REWARD_SALE_IDS;
		masterRedisTemplateListLong.opsForValue().set(Constans.MEDIA_REWARD_SALE_IDS_CACHE_KEY + custId, mediaIds, expireTime, TimeUnit.SECONDS);
	}

	@Override
	public void deleteUserRewardBooksIdsCache(Long custId) {
		masterRedisTemplateListLong.delete(Constans.MEDIA_REWARD_SALE_IDS_CACHE_KEY+custId);
	}
}
