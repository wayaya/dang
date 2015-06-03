package com.dangdang.digital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.model.BookshelfBook;
import com.dangdang.digital.model.BookshelfCategory;
import com.dangdang.digital.service.IBaseService;


/**
 * BookshelfBook Manager.
 */
public interface IBookshelfBookService extends IBaseService<BookshelfBook, Long> {
	
	public void insertBookshelf(final List<BookshelfCategory> categorys,
			Long custId);
	
	public List<BookshelfCategory> searchBookList(Long custId);
	
	public List<BookshelfBook> searchRecentlyBooks(Long custId, int bookLimit);
	
	public Integer countUserBooks(Long custId);
	
}
