package com.dangdang.digital.dao.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IColumnCacheDao;
import com.dangdang.digital.model.Column;
import com.dangdang.digital.model.ColumnContent;
@Repository
public class ColumnCacheDaoImpl implements IColumnCacheDao {
	//栏目
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Column> masterColumnRedisTemplate;


	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, Column> slaveColumnRedisTemplate;
	
	//栏目内容
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, ColumnContent> masterColumnContentRedisTemplate;
	
	
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate<String, ColumnContent> slaveColumnContentRedisTemplate;

	@Override
	public Column getColumnByCode(String code) {
		String cacheKey = Constans.COLUMN_CACHE_KEY+code.toLowerCase() ; 
		if(slaveColumnRedisTemplate.hasKey(cacheKey)){
			return slaveColumnRedisTemplate.opsForValue().get(cacheKey);
		}
		return null;
		
	}

	public void setColumnCache(Column column) {
		masterColumnRedisTemplate.opsForValue().set(Constans.COLUMN_CACHE_KEY+column.getColumnCode(), column);
	}

	@Override
	public List<ColumnContent> getColumnContentByCode(String columnCode) {
		return null;
	}

	@Override
	public void setColumnContentCache(String columnCode,
			List<ColumnContent> columnContentList) {
		
	}

}
