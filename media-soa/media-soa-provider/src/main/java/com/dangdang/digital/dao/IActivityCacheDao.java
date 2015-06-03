package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.ActivityCacheVo;

/**
 * 
 * Description: 活动信息缓存dao All Rights Reserved.
 * 
 * @version 1.0 2015年1月10日 下午4:07:35 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IActivityCacheDao {

	/**
	 * 
	 * Description: 获取活动缓存，如果没有从数据库里读取
	 * 
	 * @Version1.0 2015年1月10日 下午3:12:53 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param activityId
	 * @return
	 */
	public ActivityCacheVo getActivityCacheVo(Integer activityId);

	/**
	 * 
	 * Description: 删除互动缓存
	 * 
	 * @Version1.0 2015年1月10日 下午3:57:28 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param activityId
	 */
	public void deleteActivityCacheVo(Integer activityId);

	/**
	 * 
	 * Description: 批量获取活动缓存
	 * 
	 * @Version1.0 2015年1月10日 下午3:58:06 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param activityIds
	 * @return
	 */
	public List<ActivityCacheVo> mGetActivityCacheVo(List<Integer> activityIds);

	/**
	 * 
	 * Description: 批量删除缓存
	 * 
	 * @Version1.0 2015年1月10日 下午4:00:18 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param activityIds
	 */
	public void mDeleteCacheBasicVo(List<Integer> activityIds);

	/**
	 * 
	 * Description: 清除活动缓存
	 * 
	 * @Version1.0 2015年1月10日 下午4:01:09 by 许文轩（xuwenxuan@dangdang.com）创建
	 */
	public void cleanActivityCache();

	/**
	 * 
	 * Description: 批量设置活动信息缓存
	 * 
	 * @Version1.0 2015年1月10日 下午5:42:46 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param activityIdList
	 */
	public void mSetActivityCacheVo(List<Integer> activityIdList);

}
