package com.dangdang.digital.dao;

import java.util.Set;

import com.dangdang.digital.model.Catetory;

public interface ICatetoryCacheDao {
	/**
	 * 
	 * Description: 根据media分类编号查询media分类信息
	 * @Version1.0 2014年12月15日 上午11:50:07 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param code
	 * @return
	 */
	public Catetory getCatetoryByCode(String code);
	
	
	public Catetory getCatetoryByCacheKey(String cacheKey);
	/**
	 * 
	 * Description: 通过父分类编号,查出缓存中所有子分类的cache_key列表
	 * @Version1.0 2014年12月25日 上午11:56:41 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param parentCode 父分类编号
	 * @return
	 */
	public Set<String> getCatetoryListByParentCode(String parentCode);
 	
	/**
	 * 
	 * Description: 缓存分类信息
	 * @Version1.0 2014年12月15日 上午11:50:46 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param catetory
	 */
	public void setCatetoryCache(Catetory catetory);
	
}
