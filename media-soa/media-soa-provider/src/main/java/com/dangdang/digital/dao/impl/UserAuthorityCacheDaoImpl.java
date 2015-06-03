/**
 * Description: UserAuthorityCacheDaoImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 上午11:56:29  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.dangdang.digital.dao.IUserAuthorityCacheDao;
import com.dangdang.digital.dao.IUserAuthorityDao;
import com.dangdang.digital.model.UserAuthority;
import com.dangdang.digital.utils.MediaCacheBeanUtils;
import com.dangdang.digital.vo.UserAuthorityCacheVo;

/**
 * Description: All Rights Reserved.
 * 
 * @version 1.0 2014年12月15日 上午11:56:29 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Repository
public class UserAuthorityCacheDaoImpl implements IUserAuthorityCacheDao {

	@Resource
	private IUserAuthorityDao userAuthorityDao;

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, UserAuthorityCacheVo> masterRedisTemplat;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, UserAuthorityCacheVo> slaveRedisTemplate;

	@Override
	public UserAuthorityCacheVo getUserAuthorityCacheVoByCustIdAndMediaId(Long custId, Long mediaId,String redisFlag) {
		UserAuthorityCacheVo userAuthorityCacheVo = null;
		if(StringUtils.isNotBlank(redisFlag) && redisFlag.equals("master")){
			userAuthorityCacheVo = masterRedisTemplat.opsForValue().get("user_authority_" + custId + "_" + mediaId);
		}else{
			userAuthorityCacheVo = slaveRedisTemplate.opsForValue().get("user_authority_" + custId + "_" + mediaId);
		}		
		if(userAuthorityCacheVo == null){
			UserAuthority userAuthority = userAuthorityDao.findWithDetailByCustIdAndMediaId(custId, mediaId);
			if (userAuthority == null) {
				return null;
			}
			userAuthorityCacheVo = new UserAuthorityCacheVo();
			MediaCacheBeanUtils.copyUserAuthorityToCacheVo(userAuthority, null, userAuthorityCacheVo);
			masterRedisTemplat.opsForValue().set("user_authority_" + custId + "_" + mediaId, userAuthorityCacheVo);
		}
		return userAuthorityCacheVo;
	}

	@Override
	@CachePut(value = "userAuthority", key = "'user_authority_'+#userAuthority.custId+'_'+#userAuthority.mediaId")
	public UserAuthorityCacheVo setUserAuthorityCacheVo(UserAuthority userAuthority,
			UserAuthorityCacheVo oldUserAuthorityCacheVo) {
		if (userAuthority == null) {
			return null;
		}
		UserAuthorityCacheVo userAuthorityCacheVo = new UserAuthorityCacheVo();
		MediaCacheBeanUtils.copyUserAuthorityToCacheVo(userAuthority, oldUserAuthorityCacheVo, userAuthorityCacheVo);
		return userAuthorityCacheVo;
	}

	@Override
	@CachePut(value = "userAuthority", key = "'user_authority_'+#userAuthority.custId+'_'+#userAuthority.mediaId")
	public UserAuthorityCacheVo setUserAuthorityCacheVo(UserAuthority userAuthority) {
		if (userAuthority == null) {
			return null;
		}
		UserAuthorityCacheVo userAuthorityCacheVo = new UserAuthorityCacheVo();
		MediaCacheBeanUtils.copyUserAuthorityToCacheVo(userAuthority, null, userAuthorityCacheVo);
		return userAuthorityCacheVo;
	}

	@Override
	@CacheEvict(value = "userAuthority", key = "'user_authority_'+#custId+'_'+#mediaId")
	public void deleteUserAuthorityCacheVo(Long custId, Long mediaId) {

	}

	@Override
	public List<UserAuthorityCacheVo> getUserAuthorityCacheVoByCustId(Long custId) {
		List<UserAuthorityCacheVo> cacheList = new ArrayList<UserAuthorityCacheVo>();
		Set<String> keys = slaveRedisTemplate.keys("user_authority_" + custId + "_" + "*");
		if (CollectionUtils.isEmpty(keys)) {
			List<UserAuthority> dbList = userAuthorityDao.findWithDetailByCustId(custId);
			if (CollectionUtils.isEmpty(dbList)) {
				return null;
			} else {
				for (UserAuthority userAuthority : dbList) {
					UserAuthorityCacheVo cacheVo = new UserAuthorityCacheVo();
					MediaCacheBeanUtils.copyUserAuthorityToCacheVo(userAuthority, null, cacheVo);
					cacheList.add(cacheVo);
				}
			}
		} else {
			cacheList = slaveRedisTemplate.opsForValue().multiGet(keys);
		}
		return cacheList;
	}
}
