package com.dangdang.digital.model;

/**
 * DsPayProduct Entity.
 */
public class DsPayProduct {

	// date formats

	// 列信息
	private Integer id;
	private String productId;
	private Integer money;
	private Integer status;

	public void setProductId(String value) {
		this.productId = value;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setMoney(Integer value) {
		this.money = value;
	}

	public Integer getMoney() {
		return this.money;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
