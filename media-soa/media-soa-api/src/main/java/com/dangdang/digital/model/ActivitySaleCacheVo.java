package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description: 活动与销售主体对应关系缓存 All Rights Reserved.
 * 
 * @version 1.0 2015年1月9日 上午10:36:26 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ActivitySaleCacheVo implements Serializable {
	private static final long serialVersionUID = 4111986656798227262L;

	private Integer activitySaleId;

	private Integer activityId;

	private Long saleId;

	private Date startTime;

	private Date endTime;

	private Integer status;

	private Long salePrice;

	private ActivityCacheVo activityCacheVo;

	public Integer getActivitySaleId() {
		return activitySaleId;
	}

	public void setActivitySaleId(Integer activitySaleId) {
		this.activitySaleId = activitySaleId;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}

	public ActivityCacheVo getActivityCacheVo() {
		return activityCacheVo;
	}

	public void setActivityCacheVo(ActivityCacheVo activityCacheVo) {
		this.activityCacheVo = activityCacheVo;
	}

}