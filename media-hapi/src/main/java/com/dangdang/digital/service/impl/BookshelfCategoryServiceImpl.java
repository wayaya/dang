package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.model.BookshelfCategory;
import com.dangdang.digital.dao.IBookshelfCategoryDao;
import com.dangdang.digital.service.IBookshelfCategoryService;
/**
 * BookshelfCategory Manager.
 */
@Service
public class BookshelfCategoryServiceImpl extends BaseServiceImpl<BookshelfCategory, Long> implements IBookshelfCategoryService {

	@Resource
	IBookshelfCategoryDao dao;
	
	public IBaseDao<BookshelfCategory> getBaseDao() {
		return dao;
	}
	
}
