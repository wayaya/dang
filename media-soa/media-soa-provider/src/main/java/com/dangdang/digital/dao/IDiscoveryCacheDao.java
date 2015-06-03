package com.dangdang.digital.dao;

import java.lang.reflect.InvocationTargetException;

import com.dangdang.digital.model.Discovery;


/**
 * 
 * Description: Discovery缓存dao All Rights Reserved.
 * 
 * @version 1.0 2014年12月23日 上午10:55:44 by 杜亚锋（duyafeng@dangdang.com）创建
 */
public interface IDiscoveryCacheDao {
	
	/**
	 * Description: 获取Discovery 缓存，如果没有从数据库获取并加入缓存
	 * @Version1.0 2014年12月23日 上午11:24:19 by 杜亚锋（duyafeng@dangdang.com）创建
	 * @param discoveryId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception 
	 */
	public Discovery getDiscovery(Long discoveryId);

	/**
	 * 
	 * Description: 保存Discovery 缓存
	 * 
	 * @Version1.0 2014年12月23日 上午11:50:23 by 杜亚锋（duyafeng@dangdang.com）创建
	 * @param Discovery
	 * @return
	 * @throws Exception 
	 */
	public Discovery setDiscovery(Discovery dis) throws Exception;

	/**
	 * 
	 * Description: 删除mediaSale 缓存
	 * 
	 * @Version1.0 2014年12月23日 下午6:15:12 by 杜亚锋（duyafeng@dangdang.com）创建
	 * @param mediaSaleId
	 */
	public void deleteCacheVo(Long discoveryId);

}
