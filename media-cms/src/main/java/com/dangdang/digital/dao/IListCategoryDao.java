package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.ListCategory;

/**
 * 
 * Description: 榜单分类信息
 * All Rights Reserved.
 * @version 1.0  2014年12月2日 上午10:25:12  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public interface IListCategoryDao extends IBaseDao<ListCategory> {
	
	public List<ListCategory> getAll();
	
	/**
	 * 
	 * Description: 通过榜单标识获取榜单信息
	 * @Version1.0 2014年12月15日 上午11:28:06 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param categoryCode
	 * @return
	 */
	public ListCategory getListCategoryByCode(String categoryCode);
	
}