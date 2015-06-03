package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Description: 订单明细
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午3:23:29  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class OrderDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6501722737923969080L;
	/**
	 * 订单明细id
	 */
    private Long orderDetailId;
	/**
	 * 订单号		
	 */
    private String orderNo;
	/**
	 * 销售实体id
	 */
    private Long saleInfoId;
	/**
	 * 销售实体名称
	 */
    private String saleInfoName;
	/**
	 * 参加活动id
	 */
    private String activeId;
	/**
	 * 明细分摊总价格
	 */
    private Integer totalPrice;
	/**
	 * 明细分摊支付主账户阅读币数量
	 */
    private Integer payMainPrice;
	/**
	 * 明细分摊支付副账户阅读币数量
	 */
    private Integer paySubPrice;
	/**
	 * 明细分摊总优惠金额
	 */
    private Integer prePrice;
	/**
	 * 明细分摊总供应商分成金额
	 */
    private Integer vspPrice;
	/**
	 * 明细分摊优惠券总金额
	 */
    private Integer couponPrice;
	/**
	 * 明细分摊赠送积分数量
	 */
    private Integer givingPoint;
    
    /**
     * 下浮金额
     */
    private Integer downRationPrice;
    
    /**
     * 订单章节明细
     */
    private List<OrderDetailChapter> orderDetailChapters = new ArrayList<OrderDetailChapter>(0);

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getSaleInfoId() {
        return saleInfoId;
    }

    public void setSaleInfoId(Long saleInfoId) {
        this.saleInfoId = saleInfoId;
    }

    public String getSaleInfoName() {
        return saleInfoName;
    }

    public void setSaleInfoName(String saleInfoName) {
        this.saleInfoName = saleInfoName == null ? null : saleInfoName.trim();
    }

    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId;
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

    public Integer getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(Integer couponPrice) {
        this.couponPrice = couponPrice;
    }

    public Integer getGivingPoint() {
        return givingPoint;
    }

    public void setGivingPoint(Integer givingPoint) {
        this.givingPoint = givingPoint;
    }

	public List<OrderDetailChapter> getOrderDetailChapters() {
		return orderDetailChapters;
	}

	public void setOrderDetailChapters(List<OrderDetailChapter> orderDetailChapters) {
		this.orderDetailChapters = orderDetailChapters;
	}

	public Integer getDownRationPrice() {
		return downRationPrice;
	}

	public void setDownRationPrice(Integer downRationPrice) {
		this.downRationPrice = downRationPrice;
	}
}