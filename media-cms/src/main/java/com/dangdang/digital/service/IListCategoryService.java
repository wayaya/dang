package com.dangdang.digital.service;


import java.util.List;

import com.dangdang.digital.model.ListCategory;
import com.dangdang.digital.service.IBaseService;


/**
 * ListCategory Manager.
 */
public interface IListCategoryService extends IBaseService<ListCategory, Integer> {
	List<ListCategory> getAll();
	/**
	 * 
	 * Description: 通过榜单类型标识查询榜单基本信息
	 * @Version1.0 2014年12月3日 下午1:55:27 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param categoryCode
	 * @return
	 */
	ListCategory getListCategoryByCategoryCode(String categoryCode);
	
}
