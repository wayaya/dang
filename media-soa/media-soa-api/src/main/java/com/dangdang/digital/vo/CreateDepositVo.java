package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年3月27日 下午6:07:45  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class CreateDepositVo implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -496248812094294796L;
	/**
     * 用户custId
     */
    private Long custId;
    /**
     * 充值订单号
     */
    private String depositOrderNo;
    /**
     * 充值方式
     */
    private String payment;
    /**
     * 支付虚拟商品id
     */
    private String relationProductId;
    /**
     * 充值时间
     */
    private Date payTime;
    /**
     * 设备类型
     */
    private String deviceType;
    
    private String userName;
    
    /**
	 * 来源平台 PayFromPaltform
	 */
    private String fromPaltform;

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getDepositOrderNo() {
		return depositOrderNo;
	}

	public void setDepositOrderNo(String depositOrderNo) {
		this.depositOrderNo = depositOrderNo;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getRelationProductId() {
		return relationProductId;
	}

	public void setRelationProductId(String relationProductId) {
		this.relationProductId = relationProductId;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFromPaltform() {
		return fromPaltform;
	}

	public void setFromPaltform(String fromPaltform) {
		this.fromPaltform = fromPaltform;
	}
    
}
