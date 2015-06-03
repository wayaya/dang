package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description: 活动缓存vo All Rights Reserved.
 * 
 * @version 1.0 2015年1月9日 上午9:57:26 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ActivityCacheVo implements Serializable {

	private static final long serialVersionUID = -4966484416306593670L;

	/**
	 * 主键ID 促销ID
	 */
	private Integer activityId;

	/**
	 * 促销名称
	 */
	private String activityName;

	/**
	 * 活动开始时间
	 */
	private Date startTime;

	/**
	 * 活动结束时间
	 */
	private Date endTime;

	/**
	 * 方案ID
	 */
	private Integer activityTypeId;

	/**
	 * 方案编号
	 */
	private String activityTypeCode;

	/**
	 * 方案名称
	 */
	private String activityTypeName;

	/**
	 * 是否为全本
	 */
	private Integer isWholeMedia;

	/**
	 * 每次消费满的章节数量
	 */
	private Integer consumeChapter;

	/**
	 * 打折比例
	 */
	private Integer discount;
	/**
	 * 是否为一口价（0：不是一口价, 1:是一口价）
	 */
	private Integer isFixedPrice;

	/**
	 * 一口价金额
	 */
	private Integer fixedPrice;

	/**
	 * 类型状态(0：无效 1:有效，)
	 */
	private Integer status;

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getActivityTypeCode() {
		return activityTypeCode;
	}

	public void setActivityTypeCode(String activityTypeCode) {
		this.activityTypeCode = activityTypeCode;
	}

	public Integer getIsWholeMedia() {
		return isWholeMedia;
	}

	public void setIsWholeMedia(Integer isWholeMedia) {
		this.isWholeMedia = isWholeMedia;
	}

	public Integer getConsumeChapter() {
		return consumeChapter;
	}

	public void setConsumeChapter(Integer consumeChapter) {
		this.consumeChapter = consumeChapter;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getIsFixedPrice() {
		return isFixedPrice;
	}

	public void setIsFixedPrice(Integer isFixedPrice) {
		this.isFixedPrice = isFixedPrice;
	}

	public Integer getFixedPrice() {
		return fixedPrice;
	}

	public void setFixedPrice(Integer fixedPrice) {
		this.fixedPrice = fixedPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getActivityTypeName() {
		return activityTypeName;
	}

	public void setActivityTypeName(String activityTypeName) {
		this.activityTypeName = activityTypeName;
	}

	public Integer getActivityTypeId() {
		return activityTypeId;
	}

	public void setActivityTypeId(Integer activityTypeId) {
		this.activityTypeId = activityTypeId;
	}

}