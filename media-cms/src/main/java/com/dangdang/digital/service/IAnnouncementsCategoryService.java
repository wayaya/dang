package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.AnnouncementsCategory;
import com.dangdang.digital.service.IBaseService;


/**
 * AnnouncementsCategory Manager.
 */
public interface IAnnouncementsCategoryService extends IBaseService<AnnouncementsCategory, Long> {
	
	List<AnnouncementsCategory> getAll();
	
	/**
	 * 
	 * Description: 删除公告分类和该分类下的内容
	 * @Version1.0 2015年1月6日 下午1:40:45 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param ac
	 */
	public void deleteAnnouncementsCategory(final AnnouncementsCategory ac);
}
