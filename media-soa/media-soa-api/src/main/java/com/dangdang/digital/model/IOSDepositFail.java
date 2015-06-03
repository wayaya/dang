package com.dangdang.digital.model;

import java.util.Date;


/**
 * 
 * Description: IOS充值支付校验失败记录
 * All Rights Reserved.
 * @version 1.0  2015年3月20日 上午11:06:07  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class IOSDepositFail{
	
	//date formats
	
	//列信息
	private Long iosDepositFailId;
	private Long custId;
	private String userName;
	private String depositOrderNo;
	private String payment;
	private String relationProductId;
	private Integer money;
	private Integer mainGold;
	private Integer giveMainGold;
	private Integer subGold;
	private String deviceType;
	private Date payTime;
	private Date creationDate;
	private String fromPaltform;
	private Integer productCount;
	private String receiptData;
	private Short status;
	private Integer operateCount;

	public void setIosDepositFailId(Long value) {
		this.iosDepositFailId = value;
	}
	
	public Long getIosDepositFailId() {
		return this.iosDepositFailId;
	}
	public void setCustId(Long value) {
		this.custId = value;
	}
	
	public Long getCustId() {
		return this.custId;
	}
	public void setUserName(String value) {
		this.userName = value;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setDepositOrderNo(String value) {
		this.depositOrderNo = value;
	}
	
	public String getDepositOrderNo() {
		return this.depositOrderNo;
	}
	public void setPayment(String value) {
		this.payment = value;
	}
	
	public String getPayment() {
		return this.payment;
	}
	public void setRelationProductId(String value) {
		this.relationProductId = value;
	}
	
	public String getRelationProductId() {
		return this.relationProductId;
	}
	public void setMoney(Integer value) {
		this.money = value;
	}
	
	public Integer getMoney() {
		return this.money;
	}
	public void setMainGold(Integer value) {
		this.mainGold = value;
	}
	
	public Integer getMainGold() {
		return this.mainGold;
	}
	public void setGiveMainGold(Integer value) {
		this.giveMainGold = value;
	}
	
	public Integer getGiveMainGold() {
		return this.giveMainGold;
	}
	public void setSubGold(Integer value) {
		this.subGold = value;
	}
	
	public Integer getSubGold() {
		return this.subGold;
	}
	public void setDeviceType(String value) {
		this.deviceType = value;
	}
	
	public String getDeviceType() {
		return this.deviceType;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setFromPaltform(String value) {
		this.fromPaltform = value;
	}
	
	public String getFromPaltform() {
		return this.fromPaltform;
	}
	public void setProductCount(Integer value) {
		this.productCount = value;
	}
	
	public Integer getProductCount() {
		return this.productCount;
	}
	public void setReceiptData(String value) {
		this.receiptData = value;
	}
	
	public String getReceiptData() {
		return this.receiptData;
	}
	public void setStatus(Short value) {
		this.status = value;
	}
	
	public Short getStatus() {
		return this.status;
	}
	public void setOperateCount(Integer value) {
		this.operateCount = value;
	}
	
	public Integer getOperateCount() {
		return this.operateCount;
	}
}

