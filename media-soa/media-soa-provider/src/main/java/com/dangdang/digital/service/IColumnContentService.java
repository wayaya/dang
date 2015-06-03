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
	
	/**
	 * 
	 * Description: 查询含的指定saleId的栏目内容(或者分类销售topN)信息
	 * @Version1.0 2015年1月7日 下午4:54:46 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param saleIdList
	 * @return
	 */
	public List<ColumnContent> getColumnContentBySaleIds(List<Long> saleIdList);
	
	/**
	 * 
	 * Description: 根据栏目标识查询该栏目下指定数量的单品销售主体编号
	 * @Version1.0 2014年12月10日 下午6:57:17 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param count　　　　　　查询数量
	 * @param columnCode　　栏目标识
	 * @return
	 */
	List<Long> getSaleIdsByColumnCode(int offset,int count,String columnCode);
	

	
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
