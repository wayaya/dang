package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IMediaStatisticsDao;
import com.dangdang.digital.model.MediaStatistics;
import com.dangdang.digital.utils.Query;

/**
 * MediaStatistics DAO.
 */
@Repository
public class MediaStatisticsDaoImpl extends BaseDaoImpl<MediaStatistics> implements IMediaStatisticsDao{
	
	@Override
	public int getMediaCategoryMappingCount(Map<String, Object> params){
		
		return (Integer)this.getSqlSessionQueryTemplate().selectOne("MediaStatisticsMapper.getMediaCategoryMappingCount", params );
	}
	
	@Override
	public List<Map<String, Object>> getMediaCategoryMapping(Map<String, Object> params, Query query){
		
		return (List<Map<String, Object>>)this.getSqlSessionQueryTemplate().selectList("MediaStatisticsMapper.getMediaCategoryMapping", params, 
				new RowBounds(query.getOffset(), query.getPageSize()));
	}
	
	@Override
	public List<Map<String, Object>> getBestSellerOfSpecifiedCategory(Map<String, Object> params, Query query){
		
		return (List<Map<String, Object>>)this.getSqlSessionQueryTemplate().selectList("MediaStatisticsMapper.getBestSellerOfSpecifiedCategory", params, 
				new RowBounds(query.getOffset(), query.getPageSize()));
	}
}