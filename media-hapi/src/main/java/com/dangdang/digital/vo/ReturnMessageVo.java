package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.Date;

public class ReturnMessageVo implements Serializable {

	private static final long serialVersionUID = 6384809848823863501L;

	private String senderCustId;
	
	private String imagUrl;
	
	private String nickname;
	
	private String appId;
	
	private String title;
	
	private String description;
	
	private Integer openSharetype;
	
	private Integer isRead;
	
	private Long amount;
    
    private String customContent;
    
    private Date creationDate;
    
    private Date openDate;
    
    private String deviceType;

	private Integer isAllDevice;
    
	
	public String getImagUrl() {
		return imagUrl;
	}

	public void setImagUrl(String imagUrl) {
		this.imagUrl = imagUrl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOpenSharetype() {
		return openSharetype;
	}

	public void setOpenSharetype(int openSharetype) {
		this.openSharetype = openSharetype;
	}

	public String getCustomContent() {
		return customContent;
	}

	public void setCustomContent(String customContent) {
		this.customContent = customContent;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public void setOpenSharetype(Integer openSharetype) {
		this.openSharetype = openSharetype;
	}
    public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Integer getIsAllDevice() {
		return isAllDevice;
	}

	public void setIsAllDevice(Integer isAllDevice) {
		this.isAllDevice = isAllDevice;
	}

	public String getSenderCustId() {
		return senderCustId;
	}

	public void setSenderCustId(String senderCustId) {
		this.senderCustId = senderCustId;
	}	
}
