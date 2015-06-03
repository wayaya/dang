package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.Column;

public interface IColumnDao extends IBaseDao<Column> {
	
	List<Column> getAll();

}
