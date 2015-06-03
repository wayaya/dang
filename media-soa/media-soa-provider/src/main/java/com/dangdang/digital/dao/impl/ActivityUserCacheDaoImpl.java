package com.dangdang.digital.dao.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IActivityUserCacheDao;
import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.vo.ReturnActivityUserVo;

/**
 * Description:福袋用户信息dao实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年11月19日 下午2:22:34 by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Repository
public class ActivityUserCacheDaoImpl extends BaseDaoImpl<ActivityUser>
		implements IActivityUserCacheDao {

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Map<String, String>> masterRedisTemplate;
	
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, ReturnActivityUserVo> masterRedisTemplateUser;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, Map<String, String>> slaveRedisTemplate;
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, ReturnActivityUserVo> slaveRedisTemplateUser;

	
	@Override
	public void deleteTodayActivityUserCache(Long custId){
		masterRedisTemplate.delete(Constans.MEDIA_ACTIVITY_TODAY_USER_CACHE_KEY + custId);
	}


	@Override
	public void deleteActivityUserVoCache(Long custId) {
		masterRedisTemplateUser.delete(Constans.MEDIA_ACTIVITY_USER_KEY + custId);
	}

	
}
