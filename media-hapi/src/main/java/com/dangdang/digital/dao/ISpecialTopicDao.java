package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.SpecialTopic;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月8日 下午6:43:07  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public interface ISpecialTopicDao extends IBaseDao<SpecialTopic>{
	/**
	 * 
	 * Description: 查询需要在首页显示的专题列表
	 * @Version1.0 2015年3月11日 下午6:05:34 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @return
	 */
	public List<SpecialTopic> getHomePageSpecialTopicList(final String deviceType,final String channelType);
 	/**
	 * Description: 根据设备类型和频道类型获取最近并且有效的专题（一个）
	 * @Version1.0 2015年1月10日 下午5:47:53 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param deviceType
	 * @param channelTypes
	 * @return
	 */
	public SpecialTopic getLastValidSpecialTopic(String deviceType, String channelTypes);
	
	/**
	 * Description: 根据设备类型和频道类型获取历史专题总个数
	 * @Version1.0 2015年1月10日 下午5:47:53 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param deviceType
	 * @param channelTypes
	 * @return
	 */
	public Integer getHistoryValidSpecialTopicPageCount(String deviceType, String channelTypes);
	
	/**
	 * Description: 根据设备类型和频道类型获取历史专题
	 * @Version1.0 2015年1月10日 下午5:47:53 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param deviceType
	 * @param channelTypes
	 * @return
	 */
	public List<SpecialTopic> getHistoryValidSpecialTopicPageData(String deviceType, String channelTypes, Integer start, Integer end);
}