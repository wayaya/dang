package com.dangdang.digital.dao.impl;

import java.lang.reflect.InvocationTargetException;
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
import com.dangdang.digital.dao.IMediaCacheDao;
import com.dangdang.digital.dao.IMediaDao;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.utils.MediaCacheBeanUtils;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheWholeVo;

@Repository(value = "mediaCacheDao")
public class MediaCacheDaoImpl implements IMediaCacheDao {

	@Resource(name = "mediaDao")
	private IMediaDao mediaDao;

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, MediaCacheBasicVo> masterRedisTemplateForBasicVo;

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, MediaCacheWholeVo> masterRedisTemplateForWholeVo;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, MediaCacheBasicVo> slaveRedisTemplateForBasicVo;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, MediaCacheWholeVo> slaveRedisTemplateForWholeVo;

	@Resource(name = "systemApi")
	ISystemApi systemApi;

	@Override
	@Cacheable(value = "media", key = "'media_basic_'+#mediaId")
	public MediaCacheBasicVo getMediaCacheBasicVo(Long mediaId) throws IllegalAccessException,
			InvocationTargetException {
		Media media = mediaDao.selectMasterOne("MediaMapper.selectByPrimaryKey", mediaId);
		if (media == null) {
			return null;
		}
		MediaCacheBasicVo basicVo = new MediaCacheBasicVo();
		MediaCacheBeanUtils.copyMediaToBasicVo(media, basicVo);
		Map<String, Object> catMap = mediaDao.getCategorysByMediaId(mediaId);
		if (catMap != null) {
			if (catMap.containsKey("categorys") && catMap.get("categorys") != null) {
				basicVo.setCategorys(catMap.get("categorys").toString());
			}
			if (catMap.containsKey("categoryIds") && catMap.get("categoryIds") != null) {
				basicVo.setCategoryIds(catMap.get("categoryIds").toString());
			}
		}
		return basicVo;
	}

	@Override
	@CachePut(value = "media", key = "'media_basic_'+#media.mediaId")
	public MediaCacheBasicVo setMediaCacheBasicVo(Media media) {
		if (media == null) {
			return null;
		}
		MediaCacheBasicVo basicVo = new MediaCacheBasicVo();
		MediaCacheBeanUtils.copyMediaToBasicVo(media, basicVo);
		Map<String, Object> catMap = mediaDao.getCategorysByMediaId(media.getMediaId());
		if (catMap != null) {
			if (catMap.containsKey("categorys") && catMap.get("categorys") != null) {
				basicVo.setCategorys(catMap.get("categorys").toString());
			}
			if (catMap.containsKey("categoryIds") && catMap.get("categoryIds") != null) {
				basicVo.setCategoryIds(catMap.get("categoryIds").toString());
			}
		}
		return basicVo;
	}

	@Override
	@CacheEvict(value = "media", key = "'media_basic_'+#mediaId")
	public void deleteCacheBasicVo(Long mediaId) {
	}

	@Override
	@Cacheable(value = "media", key = "'media_whole_'+#mediaId")
	public MediaCacheWholeVo getMediaCacheWholeVo(Long mediaId) throws IllegalAccessException,
			InvocationTargetException {
		Media media = mediaDao.selectMasterOne("MediaMapper.selectByPrimaryKey", mediaId);
		if (media == null) {
			return null;
		}
		MediaCacheWholeVo wholeVo = new MediaCacheWholeVo();
		MediaCacheBeanUtils.copyMediaToWholeVo(media, wholeVo);
		Map<String, Object> catMap = mediaDao.getCategorysByMediaId(media.getMediaId());
		if (catMap != null) {
			if (catMap.containsKey("categorys") && catMap.get("categorys") != null) {
				wholeVo.setCategorys(catMap.get("categorys").toString());
			}
			if (catMap.containsKey("categoryIds") && catMap.get("categoryIds") != null) {
				wholeVo.setCategoryIds(catMap.get("categoryIds").toString());
			}
		}
		return wholeVo;
	}

	@Override
	@CachePut(value = "media", key = "'media_whole_'+#media.mediaId")
	public MediaCacheWholeVo setMediaCacheWholeVo(Media media) {
		if (media == null) {
			return null;
		}
		MediaCacheWholeVo wholeVo = new MediaCacheWholeVo();
		MediaCacheBeanUtils.copyMediaToWholeVo(media, wholeVo);
		Map<String, Object> catMap = mediaDao.getCategorysByMediaId(media.getMediaId());
		if (catMap != null) {
			if (catMap.containsKey("categorys") && catMap.get("categorys") != null) {
				wholeVo.setCategorys(catMap.get("categorys").toString());
			}
			if (catMap.containsKey("categoryIds") && catMap.get("categoryIds") != null) {
				wholeVo.setCategoryIds(catMap.get("categoryIds").toString());
			}
		}
		return wholeVo;
	}

	@Override
	@CacheEvict(value = "media", key = "'media_whole_'+#mediaId")
	public void deleteCacheWholeVo(Long mediaId) {
	}

	@Override
	public List<MediaCacheBasicVo> mGetMediaCacheBasicVo(List<Long> medisIds) {
		List<String> keys = MediaCacheBeanUtils.getKeysByPrefix(Constans.CACHE_KEY_PREFIX_MEDIA_BASIC, medisIds);
		if (keys != null && keys.size() > 0) {
			return slaveRedisTemplateForBasicVo.opsForValue().multiGet(keys);
		}
		return null;
	}

	@Override
	public void mSetMediaCacheBasicVo(List<Media> medias) {
		String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_KEY,
				String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MEDIA));
		Map<String, MediaCacheBasicVo> cacheMap = new HashMap<String, MediaCacheBasicVo>();
		if (medias != null && medias.size() > 0) {
			for (Media media : medias) {
				if (media == null) {
					continue;
				}
				MediaCacheBasicVo basicVo = new MediaCacheBasicVo();
				MediaCacheBeanUtils.copyMediaToBasicVo(media, basicVo);
				Map<String, Object> catMap = mediaDao.getCategorysByMediaId(media.getMediaId());
				if (catMap != null) {
					if (catMap.containsKey("categorys") && catMap.get("categorys") != null) {
						basicVo.setCategorys(catMap.get("categorys").toString());
					}
					if (catMap.containsKey("categoryIds") && catMap.get("categoryIds") != null) {
						basicVo.setCategoryIds(catMap.get("categoryIds").toString());
					}
				}
				cacheMap.put(Constans.CACHE_KEY_PREFIX_MEDIA_BASIC + media.getMediaId(), basicVo);
			}
			masterRedisTemplateForBasicVo.opsForValue().multiSet(cacheMap);
			for (String key : cacheMap.keySet()) {
				masterRedisTemplateForBasicVo.expire(key, Integer.valueOf(expireTime), TimeUnit.SECONDS);
			}

		}
	}

	@Override
	public void mDeleteCacheBasicVo(List<Long> medisIds) {
		List<String> keys = MediaCacheBeanUtils.getKeysByPrefix(Constans.CACHE_KEY_PREFIX_MEDIA_BASIC, medisIds);
		if (keys != null && keys.size() > 0) {
			masterRedisTemplateForBasicVo.delete(keys);
		}
	}

	@Override
	public List<MediaCacheWholeVo> mGetMediaCacheWholeVo(List<Long> medisIds) {
		List<String> keys = MediaCacheBeanUtils.getKeysByPrefix(Constans.CACHE_KEY_PREFIX_MEDIA_WHOLE, medisIds);
		if (keys != null && keys.size() > 0) {
			return slaveRedisTemplateForWholeVo.opsForValue().multiGet(keys);
		}
		return null;
	}

	@Override
	public void mSetMediaCacheWholeVo(List<Media> medias) {
		String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_KEY,
				String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MEDIA));
		Map<String, MediaCacheWholeVo> cacheMap = new HashMap<String, MediaCacheWholeVo>();
		if (medias != null && medias.size() > 0) {
			for (Media media : medias) {
				if (media == null) {
					continue;
				}
				MediaCacheWholeVo wholeVo = new MediaCacheWholeVo();
				MediaCacheBeanUtils.copyMediaToWholeVo(media, wholeVo);
				Map<String, Object> catMap = mediaDao.getCategorysByMediaId(media.getMediaId());
				if (catMap != null) {
					if (catMap.containsKey("categorys") && catMap.get("categorys") != null) {
						wholeVo.setCategorys(catMap.get("categorys").toString());
					}
					if (catMap.containsKey("categoryIds") && catMap.get("categoryIds") != null) {
						wholeVo.setCategoryIds(catMap.get("categoryIds").toString());
					}
				}
				cacheMap.put(Constans.CACHE_KEY_PREFIX_MEDIA_WHOLE + media.getMediaId(), wholeVo);
			}
			masterRedisTemplateForWholeVo.opsForValue().multiSet(cacheMap);
			for (String key : cacheMap.keySet()) {
				masterRedisTemplateForWholeVo.expire(key, Integer.valueOf(expireTime), TimeUnit.SECONDS);
			}
		}
	}

	@Override
	public void mDeleteCacheWholeVo(List<Long> medisIds) {
		List<String> keys = MediaCacheBeanUtils.getKeysByPrefix(Constans.CACHE_KEY_PREFIX_MEDIA_WHOLE, medisIds);
		if (keys != null && keys.size() > 0) {
			masterRedisTemplateForWholeVo.delete(keys);
		}
	}

	@Override
	public void cleanBasicVo() {
		Set<String> keys = masterRedisTemplateForBasicVo.keys(Constans.CACHE_KEY_PREFIX_MEDIA_BASIC + "*");
		masterRedisTemplateForBasicVo.delete(keys);
	}

	@Override
	public void cleanWholeVo() {
		Set<String> keys = masterRedisTemplateForWholeVo.keys(Constans.CACHE_KEY_PREFIX_MEDIA_WHOLE + "*");
		masterRedisTemplateForBasicVo.delete(keys);
	}

}
