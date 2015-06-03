package com.dangdang.digital.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IDiscoveryCacheDao;
import com.dangdang.digital.dao.IDiscoveryDao;
import com.dangdang.digital.model.Discovery;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.vo.MediaSaleCacheVo;

@Repository(value = "discoveryCacheDao")
public class DiscoveryCacheDaoImpl implements IDiscoveryCacheDao {

	
	@Resource
	private IDiscoveryDao discoveryDao;

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Discovery> masterRedisTemplateForMediaSale;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, Discovery> slaveRedisTemplateForMediaSale;

	@Resource
	ICacheService cacheService;
	
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<MediaSaleCacheVo>> masterRedisTemplateForMediaSaleBatch;
	
	
	@Override
	@Cacheable(value = "discovery", key = "'media_discovery_'+#discoveryId")
	public Discovery getDiscovery(Long discoveryId){
		Discovery dis = discoveryDao.selectOne("DiscoveryMapper.selectByPrimaryKey", discoveryId);
		if(dis == null){
			return null;
		}
		return dis;
	}

	@Override
	@CachePut(value = "discovery", key = "'media_discovery_'+#discovery.id")
	public Discovery setDiscovery(Discovery discovery) throws Exception {
		if(discovery == null){
			return null;
		}
		return discovery;
	}

	@Override
	@CacheEvict(value = "discovery", key = "'media_discovery_'+#discoveryId")
	public void deleteCacheVo(Long discoveryId) {
	}

}
