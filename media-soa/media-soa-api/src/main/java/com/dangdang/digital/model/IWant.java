package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description: 我想要 All Rights Reserved.
 * 
 * @version 1.0 2015年1月28日 下午8:29:08 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class IWant implements Serializable {

	private static final long serialVersionUID = -1861148362631213436L;
	private Long id;
	private Long custId;
	private Date createTime;

	/**
	 * 书名
	 */
	private String title;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 演讲者
	 */
	private String speaker;
	/**
	 * 联系方式
	 */
	private String contactInfo;
	/**
	 * 平台来源 PlatformSourceEnum
	 */
	private String platform;

	/** 以下为vo字段 **/

	private String platformName;

	private String userName;

	private String createTimeStart;

	private String createTimeEnd;

	private String custIdStr;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
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

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCustIdStr() {
		return custIdStr;
	}

	public void setCustIdStr(String custIdStr) {
		this.custIdStr = custIdStr;
	}

}
