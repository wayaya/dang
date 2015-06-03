package com.dangdang.digital.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IMediaDao;
import com.dangdang.digital.dao.IMediaSaleCacheDao;
import com.dangdang.digital.dao.IMediaSaleDao;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.utils.MediaCacheBeanUtils;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;

@Repository(value = "mediaSaleCacheDao")
public class MediaSaleCacheDaoImpl implements IMediaSaleCacheDao {

	@Resource(name = "mediaSaleDao")
	private IMediaSaleDao mediaSaleDao;
	
	@Resource(name = "mediaDao")
	private IMediaDao mediaDao;

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, MediaSaleCacheVo> masterRedisTemplateForMediaSale;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, MediaSaleCacheVo> slaveRedisTemplateForMediaSale;

	@Resource(name = "systemApi")
	ISystemApi systemApi;
	
	@Resource
	ICacheService cacheService;
	
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<MediaSaleCacheVo>> masterRedisTemplateForMediaSaleBatch;

	@Override
	@Cacheable(value = "mediaSale", key = "'media_sale_'+#saleId")
	public MediaSaleCacheVo getMediaSaleCacheVo(Long saleId) throws Exception {
		MediaSale mediaSale = mediaSaleDao.selectMasterOne("MediaSaleMapper.selectByPrimaryKey", saleId);
		if (mediaSale == null) {
			return null;
		}
		MediaSaleCacheVo vo = new MediaSaleCacheVo();
		MediaCacheBeanUtils.copyMediaSaleToVo(mediaSale, vo);
		List<Long> mediaIdList = mediaDao.getMediaListBySaleId(saleId);
		List<MediaCacheWholeVo> mediaList = cacheService.batchGetMediaWholeFromCache(mediaIdList);
		vo.setMediaList(mediaList);
		return vo;
	}

	@Override
	@CachePut(value = "mediaSale", key = "'media_sale_'+#mediaSale.saleId")
	public MediaSaleCacheVo setMediaSaleCacheVo(MediaSale mediaSale) throws Exception {
		if (mediaSale == null) {
			return null;
		}
		MediaSaleCacheVo vo = new MediaSaleCacheVo();
		MediaCacheBeanUtils.copyMediaSaleToVo(mediaSale, vo);
		List<Long> mediaIdList = mediaDao.getMediaListBySaleId(mediaSale.getSaleId());
		List<MediaCacheWholeVo> mediaList = cacheService.batchGetMediaWholeFromCache(mediaIdList);
		vo.setMediaList(mediaList);
		return vo;
	}

	@Override
	@CacheEvict(value = "mediaSale", key = "'media_sale_'+#saleId")
	public void deleteCacheVo(Long saleId) {
	}

	@Override
	public List<MediaSaleCacheVo> mGetMediaSaleCacheVo(List<Long> medisIds) {
		List<String> keys = MediaCacheBeanUtils.getKeysByPrefix(Constans.CACHE_KEY_PREFIX_MEDIA_SALE, medisIds);
		if (keys != null && keys.size() > 0) {
			return slaveRedisTemplateForMediaSale.opsForValue().multiGet(keys);
		}
		return null;
	}

	@Override
	public void mSetMediaSaleCacheVo(List<MediaSale> mediaSales) throws Exception {
		String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_SALE_KEY,
				String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_SALE));
		Map<String, MediaSaleCacheVo> cacheMap = new HashMap<String, MediaSaleCacheVo>();
		List<MediaSaleCacheVo> mediaSaleCacheVoList = new ArrayList<MediaSaleCacheVo>();
		if (mediaSales != null && mediaSales.size() > 0) {
			List<Long> saleIdList= new ArrayList<Long>();
			for (MediaSale mediaSale : mediaSales) {
				if (mediaSale == null) {
					continue;
				}
				saleIdList.add(mediaSale.getSaleId());
				MediaSaleCacheVo vo = new MediaSaleCacheVo();
				MediaCacheBeanUtils.copyMediaSaleToVo(mediaSale, vo);
				List<Long> mediaIdList = mediaDao.getMediaListBySaleId(mediaSale.getSaleId());
				List<MediaCacheWholeVo> mediaList = cacheService.batchGetMediaWholeFromCache(mediaIdList);
				vo.setMediaList(mediaList);
				cacheMap.put(Constans.CACHE_KEY_PREFIX_MEDIA_SALE + mediaSale.getSaleId(), vo);
				mediaSaleCacheVoList.add(vo);
			}
			masterRedisTemplateForMediaSale.opsForValue().multiSet(cacheMap);
			for (String key : cacheMap.keySet()) {
				masterRedisTemplateForMediaSale.expire(key, Integer.valueOf(expireTime), TimeUnit.SECONDS);
			}
		}
	}

	@Override
	public void mDeleteCacheVo(List<Long> medisIds) {
		List<String> keys = MediaCacheBeanUtils.getKeysByPrefix(Constans.CACHE_KEY_PREFIX_MEDIA_SALE, medisIds);
		if (keys != null && keys.size() > 0) {
			masterRedisTemplateForMediaSale.delete(keys);
		}
	}

	@Override
	public void cleanMediaSaleCache() {
		Set<String> keys = masterRedisTemplateForMediaSale.keys(Constans.CACHE_KEY_PREFIX_MEDIA_SALE + "*");
		masterRedisTemplateForMediaSale.delete(keys);
	}

}
