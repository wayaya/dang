package com.dangdang.digital.dao;

import java.util.List;
import java.util.Set;

import com.dangdang.digital.model.SpecialTopic;

/**
 * Description: All Rights Reserved.
 * 
 * @version 1.0 2015年3月19日 下午7:17:38 by
 *          yangzhenping（yangzhenping@dangdang.com）创建
 */
public interface ISpecialTopicCacheDao {

	/**
	 * Description: 通过专题Id从缓存中获取专题 All Rights Reserved.
	 * 
	 * @version 1.0 2015年3月19日 下午7:30:07 by
	 *          yangzhenping（yangzhenping@dangdang.com）创建
	 * @param stId
	 * @return
	 */
	public SpecialTopic getSpecialTopicFromCache(Integer stId);

	/**
	 * Description: 将专题放入缓存 All Rights Reserved.
	 * 
	 * @version 1.0 2015年3月19日 下午8:17:15 by
	 *          yangzhenping（yangzhenping@dangdang.com）创建
	 * @param specialTopic
	 */
	public void setSpecialTopicToCache(SpecialTopic specialTopic);

	/**
	 * Description: 通过id从缓存中删除专题 All Rights Reserved.
	 * 
	 * @version 1.0 2015年3月19日 下午8:20:47 by
	 *          yangzhenping（yangzhenping@dangdang.com）创建
	 * @param stIds
	 */
	public void deleteSpecialTopicFromCache(Set<String> cacheKeys);
	
	/**
	 * Description: 通过设备类型和频道获取首页专题列表
	 * All Rights Reserved.
	 * @version 1.0  2015年3月23日 下午2:56:15  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param deviceType
	 * @param channelType
	 * @return
	 */
	public List<SpecialTopic> getHomePageSTListFromCache(String deviceType, String channelType);
}
