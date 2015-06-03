package com.dangdang.digital.model;

import java.util.Date;


/**
 * 
 * Description: 用户合并记录
 * All Rights Reserved.
 * @version 1.0  2015年5月11日 下午4:30:16  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class UserMergeRecord{
	
	//列信息
	private Long userMergeId;
	private Long oldCustId;
	private Long newCustId;
	private String consumerDepositId;
	private String boughtId;
	private Date lastUpdateTime;
	private Date createTime;

	public void setUserMergeId(Long value) {
		this.userMergeId = value;
	}
	
	public Long getUserMergeId() {
		return this.userMergeId;
	}
	public void setOldCustId(Long value) {
		this.oldCustId = value;
	}
	
	public Long getOldCustId() {
		return this.oldCustId;
	}
	public void setNewCustId(Long value) {
		this.newCustId = value;
	}
	
	public Long getNewCustId() {
		return this.newCustId;
	}
	public void setConsumerDepositId(String value) {
		this.consumerDepositId = value;
	}
	
	public String getConsumerDepositId() {
		return this.consumerDepositId;
	}
	public void setBoughtId(String value) {
		this.boughtId = value;
	}
	
	public String getBoughtId() {
		return this.boughtId;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

