package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * Description: 精品文章对应评论列表Vo
 * All Rights Reserved.
 * @version 1.0  2015年2月28日 上午10:23:44  by 代鹏（daipeng@dangdang.com）创建
 */
public class ReturnDigestBookReviewVo implements Serializable {

	private static final long serialVersionUID = 1203649597370371512L;
	
	/**
	 * 评论reviewId
	 */
	private Long bookReviewId;
	
	/**
	 * 评论内容
	 */
	private String comment;
	
	/**
	 * 评论创建时间long型
	 */
	private long createDateLong;
	
	/**
	 * 发表评论用户昵称
	 */
	private String userNickName;
	
	/**
	 * 发表评论用户头像url
	 */
	private String userImg;
	
	/**
	 * 发表评论用户id
	 */
	private String custId;
	
	/**
	 * 评论点赞数量
	 */
	private Integer priaseCount;
	
	/**
	 * 当前登录用户是否可以赞
	 */
	private boolean canPriase;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getCreateDateLong() {
		return createDateLong;
	}

	public void setCreateDateLong(long createDateLong) {
		this.createDateLong = createDateLong;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public Integer getPriaseCount() {
		return priaseCount;
	}

	public void setPriaseCount(Integer priaseCount) {
		this.priaseCount = priaseCount;
	}

	public boolean isCanPriase() {
		return canPriase;
	}

	public void setCanPriase(boolean canPriase) {
		this.canPriase = canPriase;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Long getBookReviewId() {
		return bookReviewId;
	}

	public void setBookReviewId(Long bookReviewId) {
		this.bookReviewId = bookReviewId;
	}
	
}
