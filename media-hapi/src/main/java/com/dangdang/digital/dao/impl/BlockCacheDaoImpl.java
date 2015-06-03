package com.dangdang.digital.dao.impl;



import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IBlockCacheDao;
import com.dangdang.digital.model.Block;

/**
 * Description: 块dao 实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月22日 上午11:23:06  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Repository
public class BlockCacheDaoImpl implements IBlockCacheDao{


	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Block> masterRedisTemplate;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, Block> slaveRedisTemplate;
	
	
	@Override
	public Block getBlockCache(String code) {
		return slaveRedisTemplate.opsForValue().get(Constans.MEDIA_BLOCK_CACHE_KEY+code);
	}

	@Override
	public void setBlockCache(String code, Block block) {
		int expireTime = Constans.CACHE_EXPIRE_TIME_OF_BLOCK;
		masterRedisTemplate.opsForValue().set(Constans.MEDIA_BLOCK_CACHE_KEY+code, block, expireTime, TimeUnit.SECONDS);
	}

	@Override
	public void deleteBlockCache(String code) {
		masterRedisTemplate.delete(Constans.MEDIA_BLOCK_CACHE_KEY+code);
	}



}
