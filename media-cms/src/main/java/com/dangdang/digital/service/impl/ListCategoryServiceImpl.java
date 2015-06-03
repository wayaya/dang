package com.dangdang.digital.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IListCategoryDao;
import com.dangdang.digital.model.ListCategory;
import com.dangdang.digital.service.IListCategoryService;


/**
 * ListCategory Manager.
 */
@Service
public class ListCategoryServiceImpl extends BaseServiceImpl<ListCategory, Integer> implements IListCategoryService {

	@Resource
	IListCategoryDao dao;
	
	public IBaseDao<ListCategory> getBaseDao() {
		return dao;
	}
	
	public List<ListCategory> getAll() {
		return dao.getAll();
	}

	@Override
	public ListCategory getListCategoryByCategoryCode(String categoryCode) {
		return dao.getListCategoryByCode(categoryCode);
	}
}
