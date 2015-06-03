package com.dangdang.digital.vo;

import java.io.Serializable;

public class ReturnBookReviewReplyVo implements Serializable {

	private static final long serialVersionUID = -9118023020249013564L;

	/**
	 * 回复Id
	 */
	private Long replyId;
	/**
	 * 书评Id
	 */
	private Long bookReviewId;
	/**
	 * 用户Id
	 */
	private String custId;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 用户头像
	 */
	private String headPic;
	/**
	 * 回复内容
	 */
	private String replyContent;

	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 是否名人
	 */
	private boolean isStar;

	/**
	 * 封面图
	 */
	private String coverPic;

	public Long getBookReviewId() {
		return bookReviewId;
	}

	public void setBookReviewId(Long bookReviewId) {
		this.bookReviewId = bookReviewId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public boolean isStar() {
		return isStar;
	}

	public void setStar(boolean isStar) {
		this.isStar = isStar;
	}

	public Long getReplyId() {
		return replyId;
	}

	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

}
