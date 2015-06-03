/**
 * Description: OrderQueryVo.java
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午3:33:55  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.vo;


/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午3:33:55  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class OrderQueryVo {
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
	 * 订单状态
	 */
    private Integer orderStatus;
	/**
	 * 订单类型
	 */
    private Integer orderType;
	/**
	 * 订单来源
	 */
    private String orderSource;
	/**
	 * 支付方式
	 */
    private String payment;
	/**
	 * 创建时间开始
	 */
    private String creationDateStart;
	/**
	 * 创建时间结束
	 */
    private String creationDateEnd;
	/**
	 * 设备版本号
	 */
    private String deviceVersion;
	/**
	 * 销售实体id
	 */
    private Long saleInfoId;
	/**
	 * 销售实体名称
	 */
    private String saleInfoName;
	/**
	 * 媒体名称
	 */
    private String mediaName;
	/**
	 * 编号章节
	 */
    private Integer chapterNo;
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
		this.orderNo = orderNo;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
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
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getCreationDateStart() {
		return creationDateStart;
	}
	public void setCreationDateStart(String creationDateStart) {
		this.creationDateStart = creationDateStart;
	}
	public String getCreationDateEnd() {
		return creationDateEnd;
	}
	public void setCreationDateEnd(String creationDateEnd) {
		this.creationDateEnd = creationDateEnd;
	}
	public String getDeviceVersion() {
		return deviceVersion;
	}
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
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
		this.saleInfoName = saleInfoName;
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

}
