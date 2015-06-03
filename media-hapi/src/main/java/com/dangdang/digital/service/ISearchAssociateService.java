package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.SearchAssociate;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月27日 下午7:00:44  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public interface ISearchAssociateService extends IBaseService<SearchAssociate, Integer> {
	
	/**
	 * Description: 
	 * @Version1.0 2014年12月27日 下午7:00:34 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param key
	 * @param searchSource
	 * @return
	 */
	List<SearchAssociate> associateSearch(String keyword, String searchSource);

	/**
	 * Description: 
	 * @Version1.0 2014年12月27日 下午7:00:38 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param key
	 * @param count
	 * @param searchSource
	 */
	void saveSearchUpshot(String keyword, Integer count, String searchSource);
}
