package com.dangdang.digital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IListCategoryDao;
import com.dangdang.digital.dao.impl.BaseDaoImpl;
import com.dangdang.digital.model.ListCategory;

/**
 * ListCategory DAO.
 */
@Repository
public class ListCategoryDaoImpl extends BaseDaoImpl<ListCategory> implements IListCategoryDao{
	@Override
	public List<ListCategory> getAll() {
		return this.selectList("ListCategoryMapper.getAll");
	}

	public ListCategory getListCategoryByCode(String categoryCode){
		Map<String,Object> paramObj = new HashMap<String,Object>(2);
		paramObj.put("category_code", categoryCode);
		return this.selectOne("ListCategoryMapper.getListCategoryByCode", paramObj);
	}
}