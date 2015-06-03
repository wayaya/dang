package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.MediaStatistics;
import com.dangdang.digital.utils.Query;


public interface IMediaStatisticsDao extends IBaseDao<MediaStatistics> {
	
	public int getMediaCategoryMappingCount(Map<String, Object> params);
	public List<Map<String, Object>> getMediaCategoryMapping(Map<String, Object> params, Query query);
	public List<Map<String, Object>> getBestSellerOfSpecifiedCategory(Map<String, Object> params, Query query);

}