package com.dangdang.digital.dao.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.ICatetoryCacheDao;
import com.dangdang.digital.model.Catetory;
@Repository
public class CatetoryCacheDaoImpl implements ICatetoryCacheDao {
	
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Catetory> masterRedisTemplate;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, Catetory> slaveRedisTemplate;


	
	@Override
	public Catetory getCatetoryByCode(String code) {
		String cacheKey = Constans.MEDIA_CATEGORY_CACHE_KEY+code.toLowerCase(); 
		Catetory catetory = slaveRedisTemplate.opsForValue().get(cacheKey);
		return catetory;
	}

	@Override
	public void setCatetoryCache(Catetory catetory) {
		//缓存key=指定前缀_path
		masterRedisTemplate.opsForValue().set(Constans.MEDIA_CATEGORY_CACHE_KEY+catetory.getCode().toLowerCase(), catetory);
	}


	@Override
	public Set<String> getCatetoryListByParentCode(String parentCode) {
		Set<String> categoryCacheKeySet = slaveRedisTemplate.keys(Constans.COLUMN_CACHE_KEY + parentCode + "*");
		if(null == categoryCacheKeySet|| categoryCacheKeySet.size()<0){
			//
		}
		return slaveRedisTemplate.keys(Constans.COLUMN_CACHE_KEY + parentCode + "*");
	}

	@Override
	public Catetory getCatetoryByCacheKey(String cacheKey) {
		return slaveRedisTemplate.opsForValue().get(cacheKey);
	}

}
