package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.Author;
import com.dangdang.digital.model.BookAuthor;

public interface IAuthorService extends IBaseService<Author, Long> {
	public List<Author> selectByAuthorName(Author author);
	
	public Author getAuthorByName(String authorName);
	
	int saveBookAuthor(BookAuthor ba);
}
