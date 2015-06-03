/**
 * Description: DigestCacheDaoImpl.java
 * All Rights Reserved.
 * @version 1.0  2015年1月26日 下午4:59:59  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IDigestCacheDao;
import com.dangdang.digital.model.Digest;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月26日 下午4:59:59  by 代鹏（daipeng@dangdang.com）创建
 */
@Service("digestCacheDao")
public class DigestCacheDaoImpl implements IDigestCacheDao {
	
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<Digest>> masterRedisTemplate;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, List<Digest>> slaveRedisTemplate;

	@Override
	public void setDigestListToCache(String dayOrNight, String currentPageDate, List<Digest> digestList) {
		if(CollectionUtils.isNotEmpty(digestList)){
			String key = new StringBuffer(Constans.DIGEST_HOME_PAGE_CACHE).append(dayOrNight).append("_").append(currentPageDate).toString();
			masterRedisTemplate.opsForValue().set(key, digestList, Constans.DIGEST_HOME_PAGE_CACHE_EXPIRE_TIME, TimeUnit.SECONDS);
		}
	}

	@Override
	public List<Digest> getDigestListToCache(String dayOrNight, String currentPageDate) {
		String key = new StringBuffer(Constans.DIGEST_HOME_PAGE_CACHE).append(dayOrNight).append("_").append(currentPageDate).toString();
		return slaveRedisTemplate.opsForValue().get(key);
	}

}
