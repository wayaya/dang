package com.dangdang.digital.model;

import java.io.Serializable;


/**
 * 
 * Description: 膜拜关系记录
 * All Rights Reserved.
 * @version 1.0  2015年3月16日 上午11:19:22  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public class WorshipRecord implements Serializable{
	private static final long serialVersionUID = -6078614449658984699L;
	public static Integer STATUS_SOFT_DEL = 0;//软删除
	public static Integer STATUS_OK = 0;
	
	//我膜拜过的用户列表
	public static Integer Worship_OF_ME =0;
	//膜拜过我的用户列表
	public static Integer Worship_TO_ME =1;
	/**
	 * 删除我膜拜过的用户记录,双方都看不到
	 */
	public static Integer Worship_DEL_ALL = 0;
	
	/*
	 * 删除膜拜过我的记录,膜拜者还可以看到这个记录,但被膜拜者看不到
	 */
	public static Integer Worship_DEL_TO_ME = 2;
	//列信息
	private Long recordId;
	private Long custId;
	private String username;
	private Long worshipedCustId;
	private String worshipedUsername;
	private String creationDate;
	private String lastTime;
	private Integer amount;
	private Long times;
	private Long sum;


	private Integer status;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	public void setRecordId(Long value) {
		this.recordId = value;
	}
	
	public Long getRecordId() {
		return this.recordId;
	}
	public void setCustId(Long value) {
		this.custId = value;
	}
	
	public Long getCustId() {
		return this.custId;
	}
	public void setUsername(String value) {
		this.username = value;
	}
	
	public String getUsername() {
		return this.username;
	}
	public void setWorshipedCustId(Long value) {
		this.worshipedCustId = value;
	}
	
	public Long getWorshipedCustId() {
		return this.worshipedCustId;
	}
	public void setWorshipedUsername(String value) {
		this.worshipedUsername = value;
	}
	
	public String getWorshipedUsername() {
		return this.worshipedUsername;
	}
	public void setCreationDate(String value) {
		this.creationDate = value;
	}
	
	public String getCreationDate() {
		return this.creationDate;
	}
	public void setLastTime(String value) {
		this.lastTime = value;
	}
	
	public String getLastTime() {
		return this.lastTime;
	}
	public void setAmount(Integer value) {
		this.amount = value;
	}
	
	public Integer getAmount() {
		return this.amount;
	}
	public void setTimes(Long value) {
		this.times = value;
	}
	
	public Long getTimes() {
		return this.times;
	}
	public void setSum(Long value) {
		this.sum = value;
	}
	
	public Long getSum() {
		return this.sum;
	}
}

