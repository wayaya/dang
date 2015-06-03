package com.dangdang.digital.service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dangdang.digital.model.MediaStatistics;
import com.dangdang.digital.utils.Query;


/**
 * MediaStatistics Manager.
 */
public interface IMediaStatisticsService extends IBaseService<MediaStatistics, Long> {

	public int getMediaCategoryMappingCount(Date date);
	public List<Map<String, Object>> getMediaCategoryMapping(MediaStatistics statistic, Query query);
	public List<String> getMediaCategoryListToCache(MediaStatistics statistic);
	public List<Long> getBestSellerOfSpecifiedCategory(Date fromDate, String categoryPath, Query query);
	public Map<String, List<Long>> getMultiCategoryHotBooks(Date date, Set<String> categoriesSet, Query query);
	
}
