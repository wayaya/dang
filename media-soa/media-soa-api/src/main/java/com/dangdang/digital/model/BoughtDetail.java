package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * Description: 已购信息详情 All Rights Reserved.
 * 
 * @version 1.0 2014年12月25日 下午5:08:33 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class BoughtDetail implements Serializable {

	/**
	 * 多章购买
	 */
	public static Integer ORDER_TYPE_MULTI = 0;
	/**
	 * 单章购买
	 */
	public static Integer ORDER_TYPE_SINGLE = 1;

	private static final long serialVersionUID = 6511812054940746747L;

	/**
	 * 主键
	 */
	private Long boughtDetailId;

	/**
	 * 已购信息id
	 */
	private Long boughtId;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 下单时间
	 */
	private Date orderTime;

	/**
	 * 订单内容
	 */
	private String orderContent;

	/**
	 * 订单类型（1：单章购买；0：多章购买）
	 */
	private Integer orderType;

	/**
	 * 主账户支付金额
	 */
	private Integer payMainPrice;
	/**
	 * 子账户支付金额
	 */
	private Integer paySubPrice;

	/**
	 * 章节Id，如果单章存储在这里
	 */
	private Long chapterId;

	/**
	 * 已购类型 购买、抽奖等
	 */
	private String boughtType;
	private String mediaTitle;
	private String authorPenName;
	private Long mediaId;
	private Integer unitPrice;
	/**
	 * 已购章节列表
	 */
	private List<BoughtChapter> boughtChapterList;

	public Long getBoughtDetailId() {
		return boughtDetailId;
	}

	public void setBoughtDetailId(Long boughtDetailId) {
		this.boughtDetailId = boughtDetailId;
	}

	public Long getBoughtId() {
		return boughtId;
	}

	public void setBoughtId(Long boughtId) {
		this.boughtId = boughtId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderContent() {
		return orderContent;
	}

	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getPayMainPrice() {
		return payMainPrice;
	}

	public void setPayMainPrice(Integer payMainPrice) {
		this.payMainPrice = payMainPrice;
	}

	public Integer getPaySubPrice() {
		return paySubPrice;
	}

	public void setPaySubPrice(Integer paySubPrice) {
		this.paySubPrice = paySubPrice;
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public List<BoughtChapter> getBoughtChapterList() {
		return boughtChapterList;
	}

	public void setBoughtChapterList(List<BoughtChapter> boughtChapterList) {
		this.boughtChapterList = boughtChapterList;
	}

	public String getBoughtType() {
		return boughtType;
	}

	public void setBoughtType(String boughtType) {
		this.boughtType = boughtType;
	}

	public String getMediaTitle() {
		return mediaTitle;
	}

	public void setMediaTitle(String mediaTitle) {
		this.mediaTitle = mediaTitle;
	}

	public String getAuthorPenName() {
		return authorPenName;
	}

	public void setAuthorPenName(String authorPenName) {
		this.authorPenName = authorPenName;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

}
