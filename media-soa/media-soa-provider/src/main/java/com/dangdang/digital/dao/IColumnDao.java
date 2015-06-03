package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.Column;

public interface IColumnDao extends IBaseDao<Column> {
	
	List<Column> getAll();
	
	/**
	 * 
	 * Description: 根据栏目编号查询栏目实体
	 * @Version1.0 2014年12月13日 上午10:34:44 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param columnCode
	 * @return
	 */
	Column getColumnByCode(String columnCode);

}
