package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * 
 * Description: 弹幕交互vo All Rights Reserved.
 * 
 * @version 1.0 2015年1月6日 上午11:41:03 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ReturnBarrageCommentVo implements Serializable {

	private static final long serialVersionUID = 1479976720545116381L;

	/**
	 * 弹幕评论表唯一标识
	 */
	private Long barrageCommentId;

	/**
	 * 书籍编号
	 */
	private Long mediaId;

	/**
	 * 用户编号
	 */
	private String custId;

	/**
	 * 章节编号
	 */
	private Long chapterId;

	/**
	 * 字符起始位置
	 */
	private Long characterStartIndex;

	/**
	 * 字符结束位置
	 */
	private Long characterEndIndex;

	/**
	 * 评论内容
	 */
	private String content;

	/**
	 * 发布时间
	 */
	private Long publishDate;

	/**
	 * 评论状态(0-删除；1-正常；2-包含敏感词)
	 */
	private String status;

	/**
	 * 审核状态(1-未审核；2-审核中；3-审核通过；4-审核不通过)
	 */
	private String reviewStatus;

	/**
	 * 设备类型
	 */
	private String deviceType;

	/**
	 * 审核失败原因
	 */
	private String reviewFailReason;

	/**
	 * 用户头像
	 */
	private String headPic;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 卷名
	 */
	private String volumeTitle;

	/**
	 * 书名
	 */
	private String mediaTitle;

	/**
	 * 章节名
	 */
	private String chapterTitle;

	/**
	 * 是否匿名评论（0：否；1：是）
	 */
	private String isAnonymous;

	public Long getBarrageCommentId() {
		return barrageCommentId;
	}

	public void setBarrageCommentId(Long barrageCommentId) {
		this.barrageCommentId = barrageCommentId;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public Long getCharacterStartIndex() {
		return characterStartIndex;
	}

	public void setCharacterStartIndex(Long characterStartIndex) {
		this.characterStartIndex = characterStartIndex;
	}

	public Long getCharacterEndIndex() {
		return characterEndIndex;
	}

	public void setCharacterEndIndex(Long characterEndIndex) {
		this.characterEndIndex = characterEndIndex;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Long publishDate) {
		this.publishDate = publishDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getReviewFailReason() {
		return reviewFailReason;
	}

	public void setReviewFailReason(String reviewFailReason) {
		this.reviewFailReason = reviewFailReason;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getVolumeTitle() {
		return volumeTitle;
	}

	public void setVolumeTitle(String volumeTitle) {
		this.volumeTitle = volumeTitle;
	}

	public String getMediaTitle() {
		return mediaTitle;
	}

	public void setMediaTitle(String mediaTitle) {
		this.mediaTitle = mediaTitle;
	}

	public String getChapterTitle() {
		return chapterTitle;
	}

	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

}
