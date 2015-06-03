package com.dangdang.digital.dao;
import com.dangdang.digital.model.ListCategory;

/**
 * 
 * Description: 榜单缓存接口
 * All Rights Reserved.
 * @version 1.0  2014年12月13日 上午10:38:59  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public interface IListCategoryCacheDao {
	
	/**
	 * 
	 * Description: 根据榜单标识,从缓存中读取榜单信息
	 * @Version1.0 2014年12月15日 上午10:07:46 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param code	榜单标识
	 * @return
	 */
	public ListCategory getListCategoryByCode(String code);
	/**
	 * 
	 * Description: 将Column加到缓存中
	 * @Version1.0 2014年12月13日 上午10:44:28 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param column
	 */
	public void setListCategoryCache(ListCategory listCategory);
	
}
