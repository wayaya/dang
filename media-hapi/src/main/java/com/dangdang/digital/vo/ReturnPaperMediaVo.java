package com.dangdang.digital.vo;


/**
 * 
 * Description: 单品页返回basevo All Rights Reserved.
 * 
 * @version 1.0 2015年5月20日 下午5:52:54 by 魏嵩（weisong@dangdang.com）创建
 */
public class ReturnPaperMediaVo extends ReturnMediaBaseVo {

	private static final long serialVersionUID = -6273454730753659059L;

	/**
	 * 纸书的productId
	 */
	private String productId;
	/**
	 * 出版社名称
	 */
	private String publisher;
	/**
	 * 出版日期
	 */
	private Long publishDate;
	/**
	 * ISBN
	 */
	private String isbn;
	/**
	 * 纸书价格
	 */
	private Long price;
	
	/**
	 * 纸书原价
	 */
	private Long originalPrice;
	
	/**
	 * 章节数
	 */
	private Integer chapterCnt;
	
	/**
	 * 是否有电子书
	 */
	private Integer hasEbook;
	
	/**
	 * 电子书saleId
	 */
	private Long ebookSaleId;
	
	/**
	 * 装订类型 (精装1、简装0)
	 */
	private Integer bindingType;
	
	/**
	 * 是否收藏 0：未收藏，1：收藏
	 */
	private int isStore;
	
	private ReturnPreOrderInfoVo orderInfo = new ReturnPreOrderInfoVo();

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Long originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Integer getHasEbook() {
		return hasEbook;
	}

	public void setHasEbook(Integer hasEbook) {
		this.hasEbook = hasEbook;
	}

	public Long getEbookSaleId() {
		return ebookSaleId;
	}

	public void setEbookSaleId(Long ebookSaleId) {
		this.ebookSaleId = ebookSaleId;
	}

	public Integer getBindingType() {
		return bindingType;
	}

	public void setBindingType(Integer bindingType) {
		this.bindingType = bindingType;
	}

	public ReturnPreOrderInfoVo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(ReturnPreOrderInfoVo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public Integer getChapterCnt() {
		return chapterCnt;
	}

	public void setChapterCnt(Integer chapterCnt) {
		this.chapterCnt = chapterCnt;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Long getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Long publishDate) {
		this.publishDate = publishDate;
	}

	public int getIsStore() {
		return isStore;
	}

	public void setIsStore(int isStore) {
		this.isStore = isStore;
	}


}
