/**
 * Description: IDigestCacheDao.java
 * All Rights Reserved.
 * @version 1.0  2015年1月26日 下午4:55:18  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.Digest;

/**
 * Description: 精品首页内容缓存处理
 * All Rights Reserved.
 * @version 1.0  2015年1月26日 下午4:55:18  by 代鹏（daipeng@dangdang.com）创建
 */
public interface IDigestCacheDao {
	
	/**
	 * Description: 某天白天或黑夜对应的精品内容入缓存
	 * @Version1.0 2015年1月26日 下午5:00:30 by 代鹏（daipeng@dangdang.com）创建
	 * @param dayOrNight
	 * @param currentPageDate format:(yyyy-MM-dd)
	 * @param digestList
	 */
	public void setDigestListToCache(String dayOrNight, String currentPageDate, List<Digest> digestList);
	
	/**
	 * Description: 根据白天或者黑夜以及日期取精品列表
	 * @Version1.0 2015年1月26日 下午5:02:05 by 代鹏（daipeng@dangdang.com）创建
	 * @param dayOrNight
	 * @param currentPageDate
	 * @return
	 */
	public List<Digest> getDigestListToCache(String dayOrNight, String currentPageDate);

}
