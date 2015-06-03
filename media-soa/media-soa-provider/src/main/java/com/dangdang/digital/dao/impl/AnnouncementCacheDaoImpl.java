package com.dangdang.digital.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IAnnouncementCacheDao;
import com.dangdang.digital.dao.IAnnouncementsCategoryDao;
import com.dangdang.digital.dao.IAnnouncementsContentDao;
import com.dangdang.digital.model.AnnouncementsCategory;
import com.dangdang.digital.model.AnnouncementsContent;
@Repository
public class AnnouncementCacheDaoImpl implements IAnnouncementCacheDao {
	
	private  final Logger logger = LoggerFactory.getLogger(AnnouncementCacheDaoImpl.class);
	//类型
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, AnnouncementsCategory> masterCategoryRedisTemplate;


	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, AnnouncementsCategory> slaveCategoryRedisTemplate;
		
	//公告内容
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<AnnouncementsContent>> masterContentRedisTemplate;
		
		
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, List<AnnouncementsContent>> slaveContentRedisTemplate;

	@Resource 
	private IAnnouncementsCategoryDao  announcementsCategoryDao;
	
	@Resource 
	private IAnnouncementsContentDao  announcementsContentDao;
	
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Override
	public AnnouncementsCategory getAnnouncementsCategoryByCode(
			final String categoryCode) {
		String cacheKey = Constans.ANNOUNCEMENT_CATEGORY_CACHE_KEY+categoryCode ; 
		AnnouncementsCategory ac  = null;
		if(slaveCategoryRedisTemplate.hasKey(cacheKey)){
			ac =  slaveCategoryRedisTemplate.opsForValue().get(cacheKey);
			logger.info("缓存中读取,AnnouncementsCategory:" + ac);
		}else{
			logger.info("缓存中不存在,key:" + cacheKey + "categoryCode:" + categoryCode + ")到缓存");
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("categoryCode", categoryCode);
				ac = announcementsCategoryDao.selectOne("AnnouncementsCategoryMapper.getAll", paramMap);
				if(null != ac){
					final AnnouncementsCategory temp = ac;
						taskExecutor.execute(new Runnable() {
							public void run() {
								logger.info("异步公告类型(" + categoryCode + ")到缓存");
								setAnnouncementsCategoryCache(temp);
							}
				});
				}	
		}
		return ac;
	}
	private  void setAnnouncementsCategoryCache(AnnouncementsCategory ac) {
		String cacheKey = Constans.ANNOUNCEMENT_CATEGORY_CACHE_KEY+ac.getCategoryCode() ; 
		masterCategoryRedisTemplate.opsForValue().set(cacheKey, ac);
		masterCategoryRedisTemplate.expire(cacheKey, Constans.CACHE_EXPIRE_TIME_OF_ANNOUNCEMENT_CATEGORY, TimeUnit.SECONDS);
	}

	@Override
	public List<AnnouncementsContent> getAnnouncementsContentByCode(
			final String categoryCode) {
		List<AnnouncementsContent> acList = new ArrayList <AnnouncementsContent>();
		String cacheKey = Constans.ANNOUNCEMENT_CONTENT_CACHE_KEY+categoryCode ; 
		if(slaveContentRedisTemplate.hasKey(cacheKey)){
			acList =  slaveContentRedisTemplate.opsForValue().get(cacheKey);
		}else{
			logger.info("缓存中不存在,key:" + cacheKey + "categoryCode:" + categoryCode + ")到缓存");
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("categoryCode", categoryCode);
			 acList = announcementsContentDao.selectList("AnnouncementsContentMapper.getAll", paramMap);
			if(null != acList ){
				final List<AnnouncementsContent>  temp = acList;
				taskExecutor.execute(new Runnable() {
					public void run() {
						logger.info("异步公告内容(" + temp + ")到缓存");
						setAnnouncementsContentCache(categoryCode,temp);
					}
				});
			}
		}
		return acList;
	}

	
	private void setAnnouncementsContentCache(String categoryCode,
			List<AnnouncementsContent> acList) {
		String cacheKey = Constans.ANNOUNCEMENT_CONTENT_CACHE_KEY+categoryCode; 
		masterContentRedisTemplate.opsForValue().set(cacheKey, acList);
		masterContentRedisTemplate.expire(cacheKey, Constans.CACHE_EXPIRE_TIME_OF_ANNOUNCEMENT_CONTENT, TimeUnit.SECONDS);

	}
	
}
