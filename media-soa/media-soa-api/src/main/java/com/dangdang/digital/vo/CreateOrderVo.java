/**
 * Description: CreateOrderVo.java
 * All Rights Reserved.
 * @version 1.0  2014年12月1日 上午11:39:42  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dangdang.digital.model.OrderMain;

/**
 * Description: 创建订单vo
 * All Rights Reserved.
 * @version 1.0  2014年12月1日 上午11:39:42  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class CreateOrderVo implements Serializable {

	/**
	   * @Fields serialVersionUID : TODO 2014年12月1日 上午11:39:55  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = -489053060407151206L;
	
	/**
	 * 来源平台
	 */
	private String platform;
	/**
	 * 销售实体id
	 */
	private Long saleId;
	/**
	 * 用户custId
	 */
    private Long custId;	
	/**
	 * 设备版本号
	 */
    private String deviceVersion;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 渠道编码
     */
    private String chanelCode;
	/**
	 * 支付方式
	 */
    private String payment;
	/**
	 * 章节id
	 */
	private List<Long> chapterIds = new ArrayList<Long>(0);
	/**
	 * 需要支付的章节id
	 */
	private List<Long> needPayChapterIds = new ArrayList<Long>(0);
	
	/**
	 * 媒体id
	 */
	private List<Long> mediaIds = new ArrayList<Long>(0);
	/**
	 * 参加活动id
	 */
	private List<Integer> activityIds = new ArrayList<Integer>(0);
	/**
	 * 创建的订单信息
	 */
	private OrderMain orderMain = null;
	/**
	 * 创建订单错误信息
	 */
	private String errorMsg = null;
	/**
	 * 创建订单错误编码
	 */
	private String errorCode = null;
	/**
	 * 异常回滚点
	 */
	private Integer rollbackPoint = 0;
	private String username;
	/**
	 * 支付平台来源 PayFromPaltform
	 */
	private String fromPaltform;
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public List<Long> getChapterIds() {
		return chapterIds;
	}

	public void setChapterIds(List<Long> chapterIds) {
		this.chapterIds = chapterIds;
	}

	public List<Long> getMediaIds() {
		return mediaIds;
	}

	public void setMediaIds(List<Long> mediaIds) {
		this.mediaIds = mediaIds;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getChanelCode() {
		return chanelCode;
	}

	public void setChanelCode(String chanelCode) {
		this.chanelCode = chanelCode;
	}

	public List<Integer> getActivityIds() {
		return activityIds;
	}

	public void setActivityIds(List<Integer> activityIds) {
		this.activityIds = activityIds;
	}

	public OrderMain getOrderMain() {
		return orderMain;
	}

	public void setOrderMain(OrderMain orderMain) {
		this.orderMain = orderMain;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getRollbackPoint() {
		return rollbackPoint;
	}

	public void setRollbackPoint(Integer rollbackPoint) {
		this.rollbackPoint = rollbackPoint;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Long> getNeedPayChapterIds() {
		return needPayChapterIds;
	}

	public void setNeedPayChapterIds(List<Long> needPayChapterIds) {
		this.needPayChapterIds = needPayChapterIds;
	}

	public String getFromPaltform() {
		return fromPaltform;
	}

	public void setFromPaltform(String fromPaltform) {
		this.fromPaltform = fromPaltform;
	}

}
