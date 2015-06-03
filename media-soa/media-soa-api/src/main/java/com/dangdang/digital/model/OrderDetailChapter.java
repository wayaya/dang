package com.dangdang.digital.model;

import java.io.Serializable;

/**
 * 
 * Description: 订单章节明细实体
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午3:29:19  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class OrderDetailChapter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7773270033009268991L;
	/**
	 * 订单章节明细id
	 */
    private Long orderDetailChapterId;
	/**
	 * 订单号
	 */
    private String orderNo;
	/**
	 * 订单明细id
	 */
    private Long orderDetailId;
	/**
	 * 销售实体关联id
	 */
    private Long saleInfoRelationId;
	/**
	 * 媒体名称
	 */
    private String mediaName;
    /**
     * 媒体id
     */
    private Long mediaId;
	/**
	 * 章节编码
	 */
    private Integer chapterNo;
    /**
     * 章节id
     */
    private Long chapterId;
	/**
	 * 章节价格
	 */
    private Integer chapterPrice;
	/**
	 * 主账户支付金额
	 */
    private Integer payMainPrice;
	/**
	 * 副账户支付金额
	 */
    private Integer paySubPrice;
	/**
	 * 优惠金额
	 */
    private Integer prePrice;
	/**
	 * 供应商参与分成金额
	 */
    private Integer vspPrice;
	/**
	 * 参加活动id
	 */
    private Integer activeId;
	/**
	 * 使用礼券金额
	 */
    private Integer couponPrice;
	/**
	 * 赠送积分
	 */
    private Integer givingPoint;
    /**
     * 下浮折扣
     */
    private Integer downRation;

    public Long getOrderDetailChapterId() {
        return orderDetailChapterId;
    }

    public void setOrderDetailChapterId(Long orderDetailChapterId) {
        this.orderDetailChapterId = orderDetailChapterId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Long getSaleInfoRelationId() {
        return saleInfoRelationId;
    }

    public void setSaleInfoRelationId(Long saleInfoRelationId) {
        this.saleInfoRelationId = saleInfoRelationId;
    }

    public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public Integer getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(Integer chapterNo) {
        this.chapterNo = chapterNo;
    }

    public Integer getChapterPrice() {
        return chapterPrice;
    }

    public void setChapterPrice(Integer chapterPrice) {
        this.chapterPrice = chapterPrice;
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

    public Integer getActiveId() {
        return activeId;
    }

    public void setActiveId(Integer activeId) {
        this.activeId = activeId;
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

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public Integer getDownRation() {
		return downRation;
	}

	public void setDownRation(Integer downRation) {
		this.downRation = downRation;
	}
}