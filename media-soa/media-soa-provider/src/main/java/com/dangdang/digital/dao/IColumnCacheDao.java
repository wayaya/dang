package com.dangdang.digital.dao;


import java.util.List;

import com.dangdang.digital.model.Column;
import com.dangdang.digital.model.ColumnContent;

/**
 * 
 * Description: 栏目缓存接口(包含栏目内容)
 * All Rights Reserved.
 * @version 1.0  2014年12月13日 上午10:38:59  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public interface IColumnCacheDao {

	/**
	 * 
	 * Description: 根据栏目标识从缓存中读取栏目信息,如果没有从则数据里中加载到缓存中
	 * @Version1.0 2014年12月13日 上午10:43:10 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param code
	 * @return
	 */
	public Column getColumnByCode(String code);
	
	/**
	 * 
	 * Description: 将Column加到缓存中
	 * @Version1.0 2014年12月13日 上午10:44:28 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param column
	 */
	public void setColumnCache(Column column);
	
	
	/**
	 * 
	 * Description: 通过栏目标识,获取栏目下所有销售主体信息
	 * @Version1.0 2014年12月26日 下午12:11:36 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param columnCode
	 * @return
	 */
	public List<ColumnContent> getColumnContentByCode(String columnCode);
	
	
	/**
	 * 
	 * Description: 将栏目内容放到缓存中
	 * @Version1.0 2014年12月26日 下午12:13:12 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param columnCode			栏目标识
	 * @param columnContentList		栏目内容
	 */
	public void setColumnContentCache(String columnCode,List<ColumnContent> columnContentList);
	
}
