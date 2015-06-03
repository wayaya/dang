/**
 * Description: OrderDetailInfoVo.java
 * All Rights Reserved.
 * @version 1.0  2015年1月7日 下午4:09:39  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * Description: 充值订单明细信息vo
 * All Rights Reserved.
 * @version 1.0  2015年1月7日 下午4:09:39  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class OrderDetailInfoVo implements Serializable {

	/**
	   * @Fields serialVersionUID : TODO 2015年1月7日 下午4:09:45  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = 9100350847997073178L;

	/**
	 * 销售价格
	 */
	private Integer barginPrice;
	
	/**
	 * 商品数量
	 */
	private Integer orderQuantity;
	
	/**
	 * 原价
	 */
	private Integer originalPrice;
	
	/**
	 * 货品id
	 */
	private Long productId;
	
	/**
	 * 货品名称
	 */
	private String productName;

	public Integer getBarginPrice() {
		return barginPrice;
	}

	public void setBarginPrice(Integer barginPrice) {
		this.barginPrice = barginPrice;
	}

	public Integer getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public Integer getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
