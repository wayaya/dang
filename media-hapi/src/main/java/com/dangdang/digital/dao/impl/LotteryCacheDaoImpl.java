package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.ILotteryCacheDao;
import com.dangdang.digital.model.Prize;

@Repository
public class LotteryCacheDaoImpl extends BaseDaoImpl<Prize> implements ILotteryCacheDao{

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<Prize>> masterRedisTemplate;


	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, List<Prize>> slaveRedisTemplate;

	@Override
	public void setPrizeListCache(List<Prize> list,int vestType) {
		int expire = Constans.CACHE_EXPIRE_TIME_OF_PRIZE;
		masterRedisTemplate.opsForValue().set(Constans.MEDIA_PRIZE_VEST_TYPE_KEY+vestType, list,expire,TimeUnit.SECONDS);
	}

	@Override
	public List<Prize> getPrizeListCache(int vestType) {
		return slaveRedisTemplate.opsForValue().get(Constans.MEDIA_PRIZE_VEST_TYPE_KEY+vestType);
	}
	@Override
	public void deletePrizeListCache(int vestType) {
		masterRedisTemplate.delete(Constans.MEDIA_PRIZE_VEST_TYPE_KEY+vestType);
	}
}
