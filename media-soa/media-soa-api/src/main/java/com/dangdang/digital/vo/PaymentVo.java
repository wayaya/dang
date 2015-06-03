package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * 
 * Description: 充值方式vo
 * All Rights Reserved.
 * @version 1.0  2015年5月18日 下午3:18:36  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class PaymentVo implements Serializable {

	private static final long serialVersionUID = 539293853645232810L;
	
	/**
	 * 支付方式id
	 */
	private Integer paymentId;
	
	/**
	 * 支付方式名称
	 */
	private String paymentName;
	
	/**
	 * 最大赠送数量
	 */
	private Integer maxGiving;

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public Integer getMaxGiving() {
		return maxGiving;
	}

	public void setMaxGiving(Integer maxGiving) {
		this.maxGiving = maxGiving;
	}
	

}
