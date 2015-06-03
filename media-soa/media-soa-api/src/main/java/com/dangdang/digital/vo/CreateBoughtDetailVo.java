package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * 
 * Description: 创建已购记录详情vo
 * All Rights Reserved.
 * @version 1.0  2015年3月12日 上午9:56:14  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class CreateBoughtDetailVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 412087998354789820L;

	/**
	 * 电子书商品id
	 */
	private Long productId;
	
	/**
	 * 电子书标题
	 */
	private String title;
	
	/**
	 * 作者笔名
	 */
	private String authorPenName;
	
	/**
	 * 主账户支付金额
	 */
	private Integer payMainPrice;
	
	/**
	 * 子账户支付金额
	 */
	private Integer paySubPrice;
	
	/**
	 * 商品单价
	 */
	private Integer unitPrice;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorPenName() {
		return authorPenName;
	}

	public void setAuthorPenName(String authorPenName) {
		this.authorPenName = authorPenName;
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

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

}
