package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IColumnDao;
import com.dangdang.digital.model.Column;
@Repository
public class ColumnDaoImpl extends BaseDaoImpl<Column> implements IColumnDao {

	public List<Column> getAll() {
		return this.selectList("ColumnMapper.getAll");
	}}
