package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.ColumnContent;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
/**
 * 
 * Description: 栏目内容
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午3:19:32  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public interface IColumnContentDao extends IBaseDao<ColumnContent> {
	
	List<ColumnContent> getAll();
	/**
	 * 
	 * Description: 批量插入栏目内容
	 * @Version1.0 2014年11月21日 上午11:34:31 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param contents
	 */
	void insertBatch(List<ColumnContent> contents);
	
	
	PageFinder<Map<?, ?>> getSingelSales(Object params, Query query);
	
 	/**
 	 * 
 	 * Description: 批量审核销售主体
 	 * @Version1.0 2014年11月21日 下午2:59:30 by 吕翔  (lvxiang@dangdang.com) 创建
 	 * @param parameter 
 	 */
	void batchAuditSaleContent(Object parameter);
	
	/**
	 * Description: 批量更新销售主体的开始和结束时间
	 * @Version1.0 2014年11月21日 下午3:25:44 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param parameter
	 */
	void batchUpdateEffectiveDate(Object parameter);
	
	/**
	 * 
	 * Description: 批量更新栏目销售主体的状态
	 * @Version1.0 2014年11月21日 下午3:31:18 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param parameter
	 */
	void batchUpdateStatus(Object parameter);
	
}
