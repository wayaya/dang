package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.ActivitySaleCacheVo;

/**
 * 
 * Description: 活动和销售主体关系缓存dao All Rights Reserved.
 * 
 * @version 1.0 2015年1月10日 下午4:07:51 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IActivitySaleCacheDao {

	/**
	 * 
	 * Description: 通过活动id获取活动和销售主体关系缓存
	 * 
	 * @Version1.0 2015年1月12日 上午9:36:28 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param activityId
	 * @return
	 */
	public List<ActivitySaleCacheVo> getActivitySaleCacheByActivityId(Integer activityId);

	/**
	 * 
	 * Description: 通过销售主体id获取活动和销售主体关系缓存
	 * 
	 * @Version1.0 2015年1月12日 上午9:39:37 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param saleId
	 * @return
	 */
	public List<ActivitySaleCacheVo> getActivitySaleCacheBySaleId(Long saleId);

	/**
	 * 
	 * Description: 根据活动id删除活动和销售主体对应关系缓存
	 * 
	 * @Version1.0 2015年1月12日 上午9:45:32 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param activityId
	 */
	public void deleteActivitySaleCacheByActivityId(Integer activityId);

	/**
	 * 
	 * Description: 根据saleid删除活动和销售主体对应关系缓存
	 * 
	 * @Version1.0 2015年1月12日 上午9:46:13 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param saleId
	 */
	public void deleteActivitySaleCacheBySaleId(Long saleId);

	/**
	 * 
	 * Description: 批量根据活动id删除活动和销售主体对应关系缓存
	 * 
	 * @Version1.0 2015年1月12日 上午9:51:50 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param activityIdList
	 */
	public void mDeleteActivitySaleCacheByActivityId(List<Integer> activityIdList);

	/**
	 * 
	 * Description: 批量saleid删除活动和销售主体对应关系缓存
	 * 
	 * @Version1.0 2015年1月12日 上午9:53:32 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param saleIdList
	 */
	public void mDeleteActivitySaleCacheBySaleId(List<Long> saleIdList);

	/**
	 * 
	 * Description: 通过活动id设置活动和销售主体关系缓存
	 * 
	 * @Version1.0 2015年1月12日 上午10:08:10 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param activityId
	 */
	public void setActivitySaleCacheByActivityId(Integer activityId);

	/**
	 * 
	 * Description: 批量通过活动id设置活动和销售主体关系缓存
	 * 
	 * @Version1.0 2015年1月12日 上午10:26:37 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param activityIdList
	 */
	public void mSetActivitySaleCacheByActivityId(List<Integer> activityIdList);

}
