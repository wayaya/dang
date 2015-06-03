package com.dangdang.digital.vo;

import java.io.Serializable;

public class ReturnDiscoveryVo implements Serializable {
	//主键ID
	public Long id;
	//标题
	public String title;
	//作者
	public String author;
	//内容
	public String content;
	//栏目id
	public Long columnId;
	//栏目名称
	public String columnName;
	//一级分类名称
	public String firstCatetoryName;
	//二级分类名称
	public String secondCatetoryName;
	//评论数
	public Integer reviewCnt;
	//分享数
	public Integer shareCnt;
	//收藏数
	public Integer collectCnt;
	//标签名称
	public String signName;
	//评星
	public Integer stars;
	//关联sale
	public ReturnSaleVo sale;
	//是否收藏 0:未收藏  1:已收藏
	public Integer isStore;
	//卡片标题
	private String cardTitle;
	//卡片摘要
	private String cardRemark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getFirstCatetoryName() {
		return firstCatetoryName;
	}

	public void setFirstCatetoryName(String firstCatetoryName) {
		this.firstCatetoryName = firstCatetoryName;
	}

	public String getSecondCatetoryName() {
		return secondCatetoryName;
	}

	public void setSecondCatetoryName(String secondCatetoryName) {
		this.secondCatetoryName = secondCatetoryName;
	}

	public Integer getReviewCnt() {
		return reviewCnt;
	}

	public void setReviewCnt(Integer reviewCnt) {
		this.reviewCnt = reviewCnt;
	}

	public Integer getShareCnt() {
		return shareCnt;
	}

	public void setShareCnt(Integer shareCnt) {
		this.shareCnt = shareCnt;
	}

	public Integer getCollectCnt() {
		return collectCnt;
	}

	public void setCollectCnt(Integer collectCnt) {
		this.collectCnt = collectCnt;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public ReturnSaleVo getSale() {
		return sale;
	}

	public void setSale(ReturnSaleVo sale) {
		this.sale = sale;
	}

	public Integer getIsStore() {
		return isStore;
	}

	public void setIsStore(Integer isStore) {
		this.isStore = isStore;
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public void setCardTitle(String cardTitle) {
		this.cardTitle = cardTitle;
	}

	public String getCardRemark() {
		return cardRemark;
	}

	public void setCardRemark(String cardRemark) {
		this.cardRemark = cardRemark;
	}
	
}
