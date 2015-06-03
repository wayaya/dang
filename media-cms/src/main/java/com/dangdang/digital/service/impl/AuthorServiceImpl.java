package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IAuthorDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.model.Author;
import com.dangdang.digital.model.BookAuthor;
import com.dangdang.digital.service.IAuthorService;

@Service
public class AuthorServiceImpl extends BaseServiceImpl<Author, Long> implements
		IAuthorService {

	@Resource
	private IAuthorDao authorDao;
	@Override
	public IBaseDao<Author> getBaseDao() {
		return authorDao;
	}
	
	@Override
	public List<Author> selectByAuthorName(Author author) {
		return authorDao.selectByAuthorName(author);
	}
	
	@Override
	public Author getAuthorByName(String authorName) {
		return authorDao.getAuthorByName(authorName);
	}
	
	@Override
	public int saveBookAuthor(BookAuthor ba) {
		return getBaseDao().insert("BookAuthorMapper.insertSelective", ba);
	}

}
