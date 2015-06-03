package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.Author;
import com.dangdang.digital.model.BookAuthor;

public interface IAuthorDao extends IBaseDao<Author> {
	public List<Author> selectByAuthorName(Author author);
	
	public Author getAuthorByName(String authorName);

	int saveBookAuthor(BookAuthor ba);
}
