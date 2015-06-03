package com.dangdang.digital.vo;


/**
 * 
 * Description: 单品页返回basevo All Rights Reserved.
 * 
 * @version 1.0 2015年5月20日 下午5:52:54 by 魏嵩（weisong@dangdang.com）创建
 */
public class ReturnPublishedMediaVo extends ReturnMediaVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3431728822622126743L;

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
	private Long paperMediaPrice;
	/**
	 * 纸书的productId
	 */
	private String paperMediaProductId;
	/**
	 * 此书可借阅时长（单位毫秒）
	 */
	private Integer borrowDuration;
	/**
	 * 用户是否已经借阅
	 */
	private Integer isBorrowed;
	/**
	 * 用户借阅开始的时间
	 */
	private Long borrowStartTime;
	/**
	 * 用户借阅持续时间（单位毫秒）
	 */
	private Long borrowDurationTime;
	/**
	 * 用户续借开始的时间
	 */
	private Long renewStartTime;
	/**
	 * 用户续借持续时间（单位毫秒）
	 */
	private Long renewDurationTime;
	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Long getPaperMediaPrice() {
		return paperMediaPrice;
	}
	public void setPaperMediaPrice(Long paperMediaPrice) {
		this.paperMediaPrice = paperMediaPrice;
	}
	public String getPaperMediaProductId() {
		return paperMediaProductId;
	}
	public void setPaperMediaProductId(String paperMediaProductId) {
		this.paperMediaProductId = paperMediaProductId;
	}
	public Integer getBorrowDuration() {
		return borrowDuration;
	}
	public void setBorrowDuration(Integer borrowDuration) {
		this.borrowDuration = borrowDuration;
	}
	public Integer getIsBorrowed() {
		return isBorrowed;
	}
	public void setIsBorrowed(Integer isBorrowed) {
		this.isBorrowed = isBorrowed;
	}
	public Long getBorrowStartTime() {
		return borrowStartTime;
	}
	public void setBorrowStartTime(Long borrowStartTime) {
		this.borrowStartTime = borrowStartTime;
	}
	public Long getBorrowDurationTime() {
		return borrowDurationTime;
	}
	public void setBorrowDurationTime(Long borrowDurationTime) {
		this.borrowDurationTime = borrowDurationTime;
	}
	public Long getRenewStartTime() {
		return renewStartTime;
	}
	public void setRenewStartTime(Long renewStartTime) {
		this.renewStartTime = renewStartTime;
	}
	public Long getRenewDurationTime() {
		return renewDurationTime;
	}
	public void setRenewDurationTime(Long renewDurationTime) {
		this.renewDurationTime = renewDurationTime;
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
}
