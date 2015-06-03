package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.MediaStatistics;


public interface IMediaStatisticsDao extends IBaseDao<MediaStatistics> {
	public List<MediaStatistics> getMedia(Map<String,Object> paramObj);
	
	public List<Long> getSaleIdList(Map<String,Object> paramObj);
	
	public Long getSaleTotalCount(Map<String,Object> paramObj);
	
	/**
	 * 查出当前指定saleId
	 * Description: 
	 * @Version1.0 2015年2月5日 下午3:29:44 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param saleId
	 * @return
	 */
	public List<MediaStatistics>  getBySaleIds(List<Long> saleId);
	/**
	 * 
	 * Description: 根据分类标识和维度标识,查询该分类下该维度下指定数量的Media编号
	 * @Version1.0 2014年12月12日 上午10:42:19 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param limitStart  limit开始值
	 * @param count  limit count
	 * @param categoryCode  分类标识
	 * @param dimension 	统计给度
	 * @return 查询出对应的MediaId列表,media具体信息从缓存中取
	 */
	public List<Long> getSaleIdListByCategoryCodeAndDimension(final int limitStart,final int count,final String categoryCode,final String dimension);
}