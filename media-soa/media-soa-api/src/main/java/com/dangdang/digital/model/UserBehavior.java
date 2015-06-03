package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description: 用户行为 All Rights Reserved.
 * 
 * @version 1.0 2015年1月30日 上午9:52:25 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class UserBehavior implements Serializable {

	private static final long serialVersionUID = -6676733272368938441L;

	private Long id;
	private Long saleId;
	private Long mediaId;
	/**
	 * 用户行为 BehaviorTypeEnum
	 */
	private String behaviorType;
	/**
	 * 次数
	 */
	private Integer times;
	private Long custId;
	private Date createTime;
	/**
	 * 平台来源 PlatformSourceEnum
	 */
	private String platform;

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserBehavior [id=" + id + ", saleId=" + saleId + ", mediaId=" + mediaId + ", behaviorType="
				+ behaviorType + ", times=" + times + ", custId=" + custId + ", createTime=" + createTime
				+ ", platform=" + platform + "]";
	}

}
