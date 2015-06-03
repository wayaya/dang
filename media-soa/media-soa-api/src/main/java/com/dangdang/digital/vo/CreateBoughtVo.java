package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * Description: 创建已购记录vo
 * All Rights Reserved.
 * @version 1.0  2015年3月12日 上午9:56:14  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class CreateBoughtVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8563184982323388842L;
	
	/**
	 * 主账户支付金额
	 */
	private Integer payMainPrice;
	
	/**
	 * 子账户支付金额
	 */
	private Integer paySubPrice;
	
	/**
	 * 用户id
	 */
	private Long custId;
	
	/**
	 * 来源平台 PayFromPaltform
	 */
	private String fromPaltform;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 下单时间
	 */
	private Date orderTime;
	
	private List<CreateBoughtDetailVo> createBoughtDetailVos = new ArrayList<CreateBoughtDetailVo>();

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

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getFromPaltform() {
		return fromPaltform;
	}

	public void setFromPaltform(String fromPaltform) {
		this.fromPaltform = fromPaltform;
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

	public List<CreateBoughtDetailVo> getCreateBoughtDetailVos() {
		return createBoughtDetailVos;
	}

	public void setCreateBoughtDetailVos(
			List<CreateBoughtDetailVo> createBoughtDetailVos) {
		this.createBoughtDetailVos = createBoughtDetailVos;
	}

}
