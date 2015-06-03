/**
 * Description: UserMonthlyCacheDaoImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午7:09:28  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.dao.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IUserMonthlyCacheDao;
import com.dangdang.digital.dao.IUserMonthlyDao;
import com.dangdang.digital.vo.UserMonthlyCacheVo;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午7:09:28  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Repository
public class UserMonthlyCacheDaoImpl implements IUserMonthlyCacheDao {

	@Resource
	private IUserMonthlyDao userMonthlyDao;
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, UserMonthlyCacheVo> masterRedisTemplat;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, UserMonthlyCacheVo> slaveRedisTemplate;

	@Override
	public UserMonthlyCacheVo getUserMonthlyCacheVoByCustId(Long custId,String redisFlag) {
		UserMonthlyCacheVo userMonthlyCacheVo = null;
		if(StringUtils.isNotBlank(redisFlag) && redisFlag.equals("master")){
			userMonthlyCacheVo = masterRedisTemplat.opsForValue().get("user_monthly_" + custId);
		}else{
			userMonthlyCacheVo = slaveRedisTemplate.opsForValue().get("user_monthly_" + custId);
		}		
		if(userMonthlyCacheVo == null){
			userMonthlyCacheVo = userMonthlyDao.findUserMonthlyCacheVoByCustId(custId);
			if(userMonthlyCacheVo == null){
				return null;
			}
			masterRedisTemplat.opsForValue().set("user_monthly_" + custId, userMonthlyCacheVo);
		}
		return userMonthlyCacheVo;
	}

	@Override
	@CachePut(value = "userMonthlyCacheVo", key = "'user_monthly_'+#custId")
	public UserMonthlyCacheVo updateUserMonthlyCacheVo(UserMonthlyCacheVo userMonthlyCacheVo,Long custId) {
		if(userMonthlyCacheVo == null){
			return null;
		}
		return userMonthlyCacheVo;
	}

	@Override
	@CacheEvict(value = "userMonthlyCacheVo", key = "'user_monthly_'+#custId")
	public void deleteUserMonthlyCacheVo(Long custId) {
		
	}
	
}
