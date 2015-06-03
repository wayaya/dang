package com.dangdang.digital.service;
import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.MediaStatistics;
import com.dangdang.digital.service.IBaseService;


/**
 * MediaStatistics Manager.
 */
public interface IMediaStatisticsService extends IBaseService<MediaStatistics, Long> {
	
	/**
	 * 
	 * Description: 根据 paramObj参数查询出对应的MediaStatistics统计数据
	 * 				查询条件:时间端,维度
	 * @Version1.0 2014年12月8日 下午8:52:40 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param paramObj
	 * @return
	 */
	
	public List<MediaStatistics> getUpdatedMedia(Map<String,Object> paramObj);
	public List<MediaStatistics> getNewestMedias(Map<String,Object> paramObj);
	
	public List<Long> getSaleIdListByCategoryCodeAndDimension(int offset,int count,String categoryCode,String dimension);

	public Long getUpdatedSaleTotalCount(Map<String,Object> paramObj);
	
	public List<Long> getUpdatedSaleIdList(Map<String,Object> paramObj);
	
	public Long getNewestSaleTotalCount(Map<String,Object> paramObj);
	
	public List<Long> getNewestSaleIdList(Map<String,Object> paramObj);
	
	public List<MediaStatistics>  getBySaleIds(List<Long> saleId);
	/**
	 * 
	 * Description: 批量更新每个Category对应的热销数据
	 * @Version1.0 2015年1月4日 下午4:09:07 by 魏嵩（weisong@dangdang.com）创建
	 * @param saleIds
	 */
	public void batchUpdateCategoryHotSellCache(List<Long> saleIds) throws Exception;
	
}
