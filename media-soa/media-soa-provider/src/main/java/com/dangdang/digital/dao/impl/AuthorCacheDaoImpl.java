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
import com.dangdang.digital.dao.IAuthorCacheDao;
import com.dangdang.digital.dao.IAuthorDao;
import com.dangdang.digital.dao.IMediaDao;
import com.dangdang.digital.model.Author;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.utils.MediaCacheBeanUtils;
import com.dangdang.digital.vo.AuthorCacheVo;

@Repository(value = "authorCacheDao")
public class AuthorCacheDaoImpl implements IAuthorCacheDao {

	@Resource
	private IAuthorDao authorDao;

	@Resource(name = "mediaDao")
	private IMediaDao mediaDao;

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, AuthorCacheVo> masterRedisTemplateForAuthor;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, AuthorCacheVo> slaveRedisTemplateForAuthor;

	@Resource(name = "systemApi")
	ISystemApi systemApi;

	@Resource
	ICacheService cacheService;

	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, List<AuthorCacheVo>> masterRedisTemplateForAuthorBatch;

	@Override
	@Cacheable(value = "author", key = "'author_'+#authorId")
	public AuthorCacheVo getAuthorCacheVo(Long authorId) throws Exception {
		Author author = authorDao.selectOne("AuthorMapper.selectByPrimaryKey", authorId);
		if (author == null) {
			return null;
		}
		AuthorCacheVo vo = new AuthorCacheVo();
		MediaCacheBeanUtils.copyAuthorToCacheVo(author, vo);
		return vo;
	}

	@Override
	@CachePut(value = "author", key = "'author_'+#author.authorId")
	public AuthorCacheVo setAuthorCacheVo(Author author) {
		if (author == null) {
			return null;
		}
		AuthorCacheVo vo = new AuthorCacheVo();
		MediaCacheBeanUtils.copyAuthorToCacheVo(author, vo);
		return vo;
	}

	@Override
	@CacheEvict(value = "author", key = "'author_'+#authorId")
	public void deleteCacheVo(Long authorId) {
	}

	@Override
	public List<AuthorCacheVo> mGetAuthorCacheVo(List<Long> medisIds) {
		List<String> keys = MediaCacheBeanUtils.getKeysByPrefix(Constans.CACHE_KEY_PREFIX_AUTHOR, medisIds);
		if (keys != null && keys.size() > 0) {
			return slaveRedisTemplateForAuthor.opsForValue().multiGet(keys);
		}
		return null;
	}

	@Override
	public void mSetAuthorCacheVo(List<Author> authors) {
		String expireTime = systemApi.getProperty(Constans.CACHE_EXPIRE_TIME_OF_AUTHOR_KEY,
				String.valueOf(Constans.CACHE_EXPIRE_TIME_OF_AUTHOR));
		Map<String, AuthorCacheVo> cacheMap = new HashMap<String, AuthorCacheVo>();
		List<AuthorCacheVo> authorCacheVoList = new ArrayList<AuthorCacheVo>();
		if (authors != null && authors.size() > 0) {
			List<Long> authorIdList = new ArrayList<Long>();
			for (Author author : authors) {
				if (author == null) {
					continue;
				}
				authorIdList.add(author.getAuthorId());
				AuthorCacheVo vo = new AuthorCacheVo();
				MediaCacheBeanUtils.copyAuthorToCacheVo(author, vo);
				cacheMap.put(Constans.CACHE_KEY_PREFIX_AUTHOR + author.getAuthorId(), vo);
				authorCacheVoList.add(vo);
			}
			masterRedisTemplateForAuthor.opsForValue().multiSet(cacheMap);
			for (String key : cacheMap.keySet()) {
				masterRedisTemplateForAuthor.expire(key, Integer.valueOf(expireTime), TimeUnit.SECONDS);
			}
		}
	}

	@Override
	public void mDeleteCacheVo(List<Long> medisIds) {
		List<String> keys = MediaCacheBeanUtils.getKeysByPrefix(Constans.CACHE_KEY_PREFIX_AUTHOR, medisIds);
		if (keys != null && keys.size() > 0) {
			masterRedisTemplateForAuthor.delete(keys);
		}
	}

	@Override
	public void cleanAuthorCache() {
		Set<String> keys = masterRedisTemplateForAuthor.keys(Constans.CACHE_KEY_PREFIX_AUTHOR + "*");
		masterRedisTemplateForAuthor.delete(keys);
	}

}
