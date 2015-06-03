package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IAuthorDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IBookAuthorDao;
import com.dangdang.digital.model.BookAuthor;
import com.dangdang.digital.service.IBookAuthorService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Service
public class BookAuthorServiceImpl extends BaseServiceImpl<BookAuthor, Long> implements
		IBookAuthorService {
	@Resource
	private IBookAuthorDao bookAuthorDao;
	@Override
	public IBaseDao<BookAuthor> getBaseDao() {
		// TODO Auto-generated method stub
		return bookAuthorDao;
	}

	

}
