package com.dangdang.digital.dao;


import java.util.List;

import com.dangdang.digital.model.AnnouncementsCategory;
import com.dangdang.digital.model.AnnouncementsContent;

/**
 * 
 * Description: 公告类型和内容缓存
 * All Rights Reserved.
 * @version 1.0  2015年1月6日 下午7:36:27  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public interface IAnnouncementCacheDao {

	/**
	 * 
	 * Description: 根据公告类型标识获取类型实体
	 * @Version1.0 2015年1月6日 下午7:39:05 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param categoryCode
	 * @return
	 */
	public AnnouncementsCategory getAnnouncementsCategoryByCode(String categoryCode);
	
	
	
	/**
	 * 
	 * Description: 根据公告类型标识,获取类型下所有公告内容实体
	 * @Version1.0 2014年12月26日 下午12:11:36 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param categoryCode
	 * @return
	 */
	public List<AnnouncementsContent> getAnnouncementsContentByCode(String categoryCode);
	
	
}
