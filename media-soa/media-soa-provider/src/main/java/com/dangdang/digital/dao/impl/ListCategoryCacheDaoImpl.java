package com.dangdang.digital.dao.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IListCategoryCacheDao;
import com.dangdang.digital.model.ListCategory;

@Repository
public class ListCategoryCacheDaoImpl implements IListCategoryCacheDao {
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, ListCategory> masterRedisTemplate;

	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, ListCategory> slaveRedisTemplate;


	
	@Override
	public ListCategory getListCategoryByCode(String code) {
		String cacheKey = Constans.LISTCATEGORY_CACHE_KEY+code.toLowerCase() ; 
		ListCategory listCategory = slaveRedisTemplate.opsForValue().get(cacheKey);
		return listCategory;
	}

	@Override
	public void setListCategoryCache(ListCategory listCategory) {
		masterRedisTemplate.opsForValue().set(Constans.LISTCATEGORY_CACHE_KEY+listCategory.getCategoryCode().toLowerCase(), listCategory);
	}

}
