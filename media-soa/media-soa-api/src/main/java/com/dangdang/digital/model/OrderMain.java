package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * Description: 主订单实体
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午4:18:12  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class OrderMain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7955933210896101409L;
	/**
	 * 订单id
	 */
    private Long orderId;
	/**
	 * 订单号
	 */
    private String orderNo;
	/**
	 * 用户custId
	 */
    private Long custId;
	/**
	 * 订单总价格
	 */
    private Integer totalPrice;
	/**
	 * 主账户阅读币支付总金额
	 */
    private Integer payMainPrice;
	/**
	 * 副账户阅读币支付总金额
	 */
    private Integer paySubPrice;
	/**
	 * 优惠总金额
	 */
    private Integer prePrice;
	/**
	 * 供应商分成总金额
	 */
    private Integer vspPrice;
	/**
	 * 参加活动id
	 */
    private String activeId;
	/**
	 * 订单状态
	 */
    private Integer orderStatus;
	/**
	 * 订单类型
	 */
    private Integer orderType;
    /**
     * 全本购买标志（1：全本购买，0：非全本购买）
     */
    private Short wholeFlag;
	/**
	 * 支付方式
	 */
    private String payment;
	/**
	 * 赠送积分
	 */
    private Integer givingPoint;
	/**
	 * 使用优惠券金额
	 */
    private Integer couponPrice;
	/**
	 * 支付时间
	 */
    private Date payTime;
	/**
	 * 创建时间
	 */
    private Date creationDate;
	/**
	 * 设备版本号
	 */
    private String deviceVersion;
    /**
     * 渠道编号
     */
    private String chanelCode;
    private String fromPaltform;
    /**
     * 下浮金额
     */
    private Integer downRationPrice;
    /**
     * 订单明细
     */
    private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>(0);

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
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

    public Integer getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(Integer prePrice) {
        this.prePrice = prePrice;
    }

    public Integer getVspPrice() {
        return vspPrice;
    }

    public void setVspPrice(Integer vspPrice) {
        this.vspPrice = vspPrice;
    }

    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId;
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment == null ? null : payment.trim();
    }

    public Integer getGivingPoint() {
        return givingPoint;
    }

    public void setGivingPoint(Integer givingPoint) {
        this.givingPoint = givingPoint;
    }

    public Integer getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(Integer couponPrice) {
        this.couponPrice = couponPrice;
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

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion == null ? null : deviceVersion.trim();
    }

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getChanelCode() {
		return chanelCode;
	}

	public void setChanelCode(String chanelCode) {
		this.chanelCode = chanelCode;
	}

	public Short getWholeFlag() {
		return wholeFlag;
	}

	public void setWholeFlag(Short wholeFlag) {
		this.wholeFlag = wholeFlag;
	}

	public String getFromPaltform() {
		return fromPaltform;
	}

	public void setFromPaltform(String fromPaltform) {
		this.fromPaltform = fromPaltform;
	}

	public Integer getDownRationPrice() {
		return downRationPrice;
	}

	public void setDownRationPrice(Integer downRationPrice) {
		this.downRationPrice = downRationPrice;
	}
}