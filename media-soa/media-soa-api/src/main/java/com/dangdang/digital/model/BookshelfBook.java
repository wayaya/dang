package com.dangdang.digital.model;


/**
 * BookshelfBook Entity.
 */
public class BookshelfBook{
	
	//date formats
	
	//列信息
	private Long bbId;
	
	private Long custId;
	
	private Long lastChangedDate;
	
	private Long productId;
	
	private Long categoryId;
	
	private String title;
	
	private String coverPic;
		
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public void setBbId(Long value) {
		this.bbId = value;
	}
	
	public Long getBbId() {
		return this.bbId;
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
		
		
	public void setProductId(Long value) {
		this.productId = value;
	}
	
	public Long getProductId() {
		return this.productId;
	}
		
		
	public void setCategoryId(Long value) {
		this.categoryId = value;
	}
	
	public Long getCategoryId() {
		return this.categoryId;
	}
		
}

