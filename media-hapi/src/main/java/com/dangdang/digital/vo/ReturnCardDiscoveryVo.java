package com.dangdang.digital.vo;

import java.io.Serializable;


/**
 * 
 * Description: 返回card discovery vo All Rights Reserved.
 * 
 * @version 1.0 2014年12月22日 下午2:36:09 by 杜亚锋（duyafeng@dangdang.com）创建
 */
public class ReturnCardDiscoveryVo implements Serializable{

	//主键ID
	private Long id;
	//卡片标题
	private String cardTitle;
	//卡片摘要
	private String cardRemark;
	//图片1
	private String pic1;
	//图片2
	private String pic2;
	//图片3
	private String pic3;
	//卡片类型
	private Integer cardType;
	//评论数
	private Integer reviewCnt;
	//一级分类名称
	private String firstCatetoryName;
	//二级分类名称
	private String secondCatetoryName;
	//显示时间
	private String showTime;
	
	private Long saleId;
	//置顶排序
	private Integer topOrder;
	
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
	public String getPic1() {
		return pic1;
	}
	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}
	public String getPic2() {
		return pic2;
	}
	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}
	public String getPic3() {
		return pic3;
	}
	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}
	public Integer getCardType() {
		return cardType;
	}
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}
	public Integer getReviewCnt() {
		return reviewCnt;
	}
	public void setReviewCnt(Integer reviewCnt) {
		this.reviewCnt = reviewCnt;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
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
	public Long getSaleId() {
		return saleId;
	}
	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}
	public String getShowTime() {
		return showTime;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	public Integer getTopOrder() {
		return topOrder;
	}
	public void setTopOrder(Integer topOrder) {
		this.topOrder = topOrder;
	}
	
	
}
