/**
 * Description: OrderInfoVo.java
 * All Rights Reserved.
 * @version 1.0  2015年1月7日 下午4:08:46  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description: 充值订单信息vo
 * All Rights Reserved.
 * @version 1.0  2015年1月7日 下午4:08:46  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class OrderInfoVo implements Serializable {

	/**
	   * @Fields serialVersionUID : TODO 2015年1月7日 下午4:08:51  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = 7540504950915414427L;

	/**
	 * 用户邮箱
	 */
	private String custEmail;
	
	/**
	 * 用户id
	 */
	private Long custId;
	
	/**
	 * 来源平台
	 */
	private Integer fromPlatform;
	
	/**
	 * 创建时间
	 */
	private Date orderCreationDate;
	
	/**
	 * 订单id
	 */
	private Long orderId;
	
	/**
	 * 订单来源
	 */
	private String orderSource;
	
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	
	/**
	 * 订单类型
	 */
	private Integer orderType;
	
	/**
	 * 订单明细
	 */
	private List<OrderDetailInfoVo> orderDetailInfoVos = new ArrayList<OrderDetailInfoVo>();

	public String getCustEmail() {
		return custEmail;
	}

	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Integer getFromPlatform() {
		return fromPlatform;
	}

	public void setFromPlatform(Integer fromPlatform) {
		this.fromPlatform = fromPlatform;
	}

	public Date getOrderCreationDate() {
		return orderCreationDate;
	}

	public void setOrderCreationDate(Date orderCreationDate) {
		this.orderCreationDate = orderCreationDate;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public List<OrderDetailInfoVo> getOrderDetailInfoVos() {
		return orderDetailInfoVos;
	}

	public void setOrderDetailInfoVos(List<OrderDetailInfoVo> orderDetailInfoVos) {
		this.orderDetailInfoVos = orderDetailInfoVos;
	}
	
}
