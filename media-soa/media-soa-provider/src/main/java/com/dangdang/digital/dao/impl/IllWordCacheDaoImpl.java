package com.dangdang.digital.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IIllWordCacheDao;
import com.dangdang.digital.dao.IIllWordDao;
import com.dangdang.digital.model.IllWord;


@Repository
public class IllWordCacheDaoImpl implements IIllWordCacheDao {
	private  final Logger LOGGER = LoggerFactory.getLogger(IllWordCacheDaoImpl.class);

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<IllWord>> masterRedisTemplate;


	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, List<IllWord>> slaveRedisTemplate;
	
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Resource
	IIllWordDao  iIllWordDao;
	@Override
	public List<IllWord> getAll() {
		final String cacheKey =Constans.ILL_WORD_CACHE_KEY;
		List<IllWord> illWordList = new ArrayList<IllWord> ();
		if(slaveRedisTemplate.hasKey(cacheKey)){
			illWordList = (List<IllWord>) slaveRedisTemplate.opsForValue().get(cacheKey);
		}else{
			 illWordList = iIllWordDao.getIllWordList();
			 final List<IllWord> illWordListToCache = illWordList;
			taskExecutor.execute(new Runnable() {
				public void run() {
					masterRedisTemplate.opsForValue().set(cacheKey, illWordListToCache);
					masterRedisTemplate.expire(cacheKey, Constans.CACHE_EXPIRE_TIME_ILL_WORD,
							TimeUnit.SECONDS);
					LOGGER.info("异步添加 敏感词到缓存");// saleId=" + cacheSaleIdList + "
				}
			});
		}
		return illWordList;
	}
	@Override
	public List<String> getAllIllWordContent() {
		List<IllWord> illWordList =getAll();
		List<String> illWordContentList = new ArrayList<String>(illWordList.size());
		for(IllWord word:illWordList){
			illWordContentList.add(word.getWords());
		}
		return illWordContentList;
	}

}
