package com.dangdang.digital.model;

import java.util.List;


/**
 * BookshelfCategory Entity.
 */
public class BookshelfCategory{
	
	//date formats
	
	//列信息
	private Long cid;
	
	private Long custId;
	
	private Long lastChangedDate;
	
	private String categoryName;
	
	private List<BookshelfBook> books;
		
	public void setCid(Long value) {
		this.cid = value;
	}
	
	public Long getCid() {
		return this.cid;
	}
		
		
	public void setCustId(Long value) {
		this.custId = value;
	}
	
	public Long getCustId() {
		return this.custId;
	}
		
		
	public void setLastChangedDate(Long value) {
		this.lastChangedDate = value;
	}
	
	public Long getLastChangedDate() {
		return this.lastChangedDate;
	}
		
		
	public void setCategoryName(String value) {
		this.categoryName = value;
	}
	
	public String getCategoryName() {
		return this.categoryName;
	}

	public List<BookshelfBook> getBooks() {
		return books;
	}

	public void setBooks(List<BookshelfBook> books) {
		this.books = books;
	}
		
}

