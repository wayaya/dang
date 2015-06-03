package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.ColumnContent;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

public interface IColumnContentService extends IBaseService<ColumnContent, Integer> {
	List<ColumnContent> getAll();
	
	void insertBatch(List<ColumnContent> contents);
	
	void updateBatch(List<ColumnContent> contents);
	
	PageFinder<Map<?, ?>> getSingelSales(Object params, Query query);
	
	void auditByIds(Object objParam);
	
	/**
	 * 
	 * Description: 批量更新栏目内容状态
	 * @Version1.0 2014年11月21日 下午3:33:39 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param objParam
	 */
	void updateStatusByIds(Object objParam);
	
	void updateEffectiveDate(Object objParam);
	
	
	void updateStatus(Object objParam);
	
}
