package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * 
 * Description: 与前端交互书评vo All Rights Reserved.
 * 
 * @version 1.0 2014年12月18日 下午7:38:26 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ReturnBookReviewVo implements Serializable {

	private static final long serialVersionUID = -6827394606069082229L;

	/**
	 * 书评id
	 */
	private Long bookReviewId;

	/**
	 * 售后主体Id（发现id）
	 */
	private Long saleId;
	
	/**
	 * 销售主体名称（发现名称）
	 */
	private String saleName;
	
	/**
	 * 用户Id
	 */
	private String custId;
	/**
	 * 评论标题
	 */
	private String title;
	/**
	 * 评论内容
	 */
	private String comment;
	/**
	 * 评论时间
	 */
	private Long createTime;
	/**
	 * 评论类型 0：精华；1：推荐；2：热门；3：名人 ；-1:普通
	 */
	private Integer reviewType;

	/**
	 * 回复数量
	 */
	private Integer replyCount;
	/**
	 * 点赞数量
	 */
	private Integer praiseCount;
	/**
	 * 用户头像
	 */
	private String headPic;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 星级
	 */
	private Float score;

	/**
	 * 评论类型 1、书；2、发现；
	 */
	private Integer subjectType;

	/**
	 * 是否可以点赞 1：可以点赞；0：不可点赞；
	 */
	private Integer canPraise = 1;

	/**
	 * 回复内容
	 */
	private ReturnBookReviewReplyVo reply;

	/**
	 * 封面图
	 */
	private String coverPic;
	
	private String author;
	
	private String description;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getReviewType() {
		return reviewType;
	}

	public void setReviewType(Integer reviewType) {
		this.reviewType = reviewType;
	}

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Long getBookReviewId() {
		return bookReviewId;
	}

	public void setBookReviewId(Long bookReviewId) {
		this.bookReviewId = bookReviewId;
	}

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	public ReturnBookReviewReplyVo getReply() {
		return reply;
	}

	public void setReply(ReturnBookReviewReplyVo reply) {
		this.reply = reply;
	}

	public Integer getCanPraise() {
		return canPraise;
	}

	public void setCanPraise(Integer canPraise) {
		this.canPraise = canPraise;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

}
