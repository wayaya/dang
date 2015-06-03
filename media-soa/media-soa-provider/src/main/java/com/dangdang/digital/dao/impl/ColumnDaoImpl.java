package com.dangdang.digital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IColumnDao;
import com.dangdang.digital.model.Column;
@Repository
public class ColumnDaoImpl extends BaseDaoImpl<Column> implements IColumnDao {

	public List<Column> getAll() {
		return this.selectList("ColumnMapper.getAll");
	}
	
	public Column getColumnByCode(String columnCode){
		Map<String,Object> paramObj = new HashMap<String,Object>(2);
		paramObj.put("column_code",columnCode);
		return this.selectOne("ColumnMapper.getColumnByCode", paramObj);
	}

}
