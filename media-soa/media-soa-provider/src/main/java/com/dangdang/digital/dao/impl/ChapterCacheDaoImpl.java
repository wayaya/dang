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
import com.dangdang.digital.dao.IChapterCacheDao;
import com.dangdang.digital.dao.IChapterDao;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.utils.MediaCacheBeanUtils;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.ChapterCacheWholeVo;

@Repository(value = "chapterCacheDao")
public class ChapterCacheDaoImpl implements IChapterCacheDao {

	@Resource
	private IChapterDao chapterDao;

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, ChapterCacheBasicVo> masterRedisTemplateForBasicVo;

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, ChapterCacheWholeVo> masterRedisTemplateForWholeVo;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, ChapterCacheBasicVo> slaveRedisTemplateForBasicVo;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, ChapterCacheWholeVo> slaveRedisTemplateForWholeVo;

	@Resource(name = "systemApi")
	ISystemApi systemApi;

	@Override
	@Cacheable(value = "chapter", key = "'chapter_basic_'+#chapterId")
	public ChapterCacheBasicVo getChapterCacheBasicVo(Long chapterId) throws IllegalAccessException,
			InvocationTargetException {
		Chapter chapter = chapterDao.selectMasterOne("ChapterMapper.selectByPrimaryKey", chapterId);
		if (chapter == null) {
			return null;
		}
		ChapterCacheBasicVo basicVo = new ChapterCacheBasicVo();
		MediaCacheBeanUtils.copyChapterToBasicVo(chapter, basicVo);
		return basicVo;
	}

	@Override
	@CachePut(value = "chapter", key = "'chapter_basic_'+#chapter.id")
	public ChapterCacheBasicVo setChapterCacheBasicVo(Chapter chapter) {
		if (chapter == null) {
			return null;
		}
		ChapterCacheBasicVo basicVo = new ChapterCacheBasicVo();
		MediaCacheBeanUtils.copyChapterToBasicVo(chapter, basicVo);
		return basicVo;
	}

	@Override
	@CacheEvict(value = "chapter", key = "'chapter_basic_'+#chapterId")
	public void deleteCacheBasicVo(Long chapterId) {
	}

	@Override
	@Cacheable(value = "chapter", key = "'chapter_whole_'+#chapterId")
	public ChapterCacheWholeVo getChapterCacheWholeVo(Long chapterId) throws IllegalAccessException,
			InvocationTargetException {
		Chapter chapter = chapterDao.selectMasterOne("ChapterMapper.selectByPrimaryKey", chapterId);
		if (chapter == null) {
			return null;
		}
		ChapterCacheWholeVo wholeVo = new ChapterCacheWholeVo();
		MediaCacheBeanUtils.copyChapterToWholeVo(chapter, wholeVo);
		return wholeVo;
	}

	@Override
	@CachePut(value = "chapter", key = "'chapter_whole_'+#chapter.id")
	public ChapterCacheWholeVo setChapterCacheWholeVo(Chapter chapter) {
		if (chapter == null) {
			return null;
		}
		ChapterCacheWholeVo wholeVo = new ChapterCacheWholeVo();
		MediaCacheBeanUtils.copyChapterToWholeVo(chapter, wholeVo);
		return wholeVo;
	}

	@Override
	@CacheEvict(value = "chapter", key = "'chapter_whole_'+#chapterId")
	public void deleteCacheWholeVo(Long chapterId) {
	}

	@Override
	public List<ChapterCacheBasicVo> mGetChapterCacheBasicVo(List<Long> medisIds) {
		List<String> keys = MediaCacheBeanUtils.getKeysByPrefix(Constans.CACHE_KEY_PREFIX_CHAPTER_BASIC, medisIds);
		if (keys != null && keys.size() > 0) {
			return slaveRedisTemplateForBasicVo.opsForValue().multiGet(keys);
		}
		return null;
	}

	@Override
	public void mSetChapterCacheBasicVo(List<Chapter> chapters) {
		String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_KEY,
				String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MEDIA));
		Map<String, ChapterCacheBasicVo> cacheMap = new HashMap<String, ChapterCacheBasicVo>();
		if (chapters != null && chapters.size() > 0) {
			for (Chapter chapter : chapters) {
				if (chapter == null) {
					continue;
				}
				ChapterCacheBasicVo basicVo = new ChapterCacheBasicVo();
				MediaCacheBeanUtils.copyChapterToBasicVo(chapter, basicVo);
				cacheMap.put(Constans.CACHE_KEY_PREFIX_CHAPTER_BASIC + chapter.getId(), basicVo);
			}
			masterRedisTemplateForBasicVo.opsForValue().multiSet(cacheMap);
			for (String key : cacheMap.keySet()) {
				masterRedisTemplateForBasicVo.expire(key, Integer.valueOf(expireTime), TimeUnit.SECONDS);
			}
		}
	}

	@Override
	public void mDeleteCacheBasicVo(List<Long> medisIds) {
		List<String> keys = MediaCacheBeanUtils.getKeysByPrefix(Constans.CACHE_KEY_PREFIX_CHAPTER_BASIC, medisIds);
		if (keys != null && keys.size() > 0) {
			masterRedisTemplateForBasicVo.delete(keys);
		}
	}

	@Override
	public List<ChapterCacheWholeVo> mGetChapterCacheWholeVo(List<Long> medisIds) {
		List<String> keys = MediaCacheBeanUtils.getKeysByPrefix(Constans.CACHE_KEY_PREFIX_CHAPTER_WHOLE, medisIds);
		if (keys != null && keys.size() > 0) {
			return slaveRedisTemplateForWholeVo.opsForValue().multiGet(keys);
		}
		return null;
	}

	@Override
	public void mSetChapterCacheWholeVo(List<Chapter> chapters) {
		String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_MEDIA_KEY,
				String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_MEDIA));
		Map<String, ChapterCacheWholeVo> cacheMap = new HashMap<String, ChapterCacheWholeVo>();
		if (chapters != null && chapters.size() > 0) {
			for (Chapter chapter : chapters) {
				if (chapter == null) {
					continue;
				}
				ChapterCacheWholeVo wholeVo = new ChapterCacheWholeVo();
				MediaCacheBeanUtils.copyChapterToWholeVo(chapter, wholeVo);
				cacheMap.put(Constans.CACHE_KEY_PREFIX_CHAPTER_WHOLE + chapter.getId(), wholeVo);
			}
			masterRedisTemplateForWholeVo.opsForValue().multiSet(cacheMap);
			for (String key : cacheMap.keySet()) {
				masterRedisTemplateForWholeVo.expire(key, Integer.valueOf(expireTime), TimeUnit.SECONDS);
			}
		}
	}

	@Override
	public void mDeleteCacheWholeVo(List<Long> medisIds) {
		List<String> keys = MediaCacheBeanUtils.getKeysByPrefix(Constans.CACHE_KEY_PREFIX_CHAPTER_WHOLE, medisIds);
		if (keys != null && keys.size() > 0) {
			masterRedisTemplateForWholeVo.delete(keys);
		}
	}

	@Override
	public void cleanBasicVo() {
		Set<String> keys = masterRedisTemplateForBasicVo.keys(Constans.CACHE_KEY_PREFIX_CHAPTER_BASIC + "*");
		masterRedisTemplateForBasicVo.delete(keys);
	}

	@Override
	public void cleanWholeVo() {
		Set<String> keys = masterRedisTemplateForWholeVo.keys(Constans.CACHE_KEY_PREFIX_CHAPTER_WHOLE + "*");
		masterRedisTemplateForBasicVo.delete(keys);
	}

}
