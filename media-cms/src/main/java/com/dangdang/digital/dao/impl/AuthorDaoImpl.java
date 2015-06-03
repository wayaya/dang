package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IAuthorDao;
import com.dangdang.digital.model.Author;
import com.dangdang.digital.model.BookAuthor;

@Repository
public class AuthorDaoImpl extends BaseDaoImpl<Author> implements IAuthorDao {

	@Override
	public List<Author> selectByAuthorName(Author author) {
		return this.selectList("AuthorMapper.selectByAuthorName");
	}
	
	@Override
	public Author getAuthorByName(String authorName) {

		return this.selectOne("AuthorMapper.getAuthorByName", authorName);
	}

	@Override
	public int saveBookAuthor(BookAuthor ba) {
		return this.getSqlSessionTemplate().insert("BookAuthorMapper.insertSelective", ba);
	}
}
