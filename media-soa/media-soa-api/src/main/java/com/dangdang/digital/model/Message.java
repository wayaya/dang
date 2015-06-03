package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8164829638751355857L;

	    private Long messageId;

	    private Long receiverCustId;

	    private Long senderCustId;

	    private String appId;

	    private String title;

	    private String description;

	    private Date openDate;

	    private Date creationDate;

	    private Integer openShareType;

	    private Integer isRead;

	    private Long amount;

	    private String deviceType;

	    private Integer isAllDevice;

	    private String customContent;

	    public Long getMessageId() {
	        return messageId;
	    }

	    public void setMessageId(Long messageId) {
	        this.messageId = messageId;
	    }

	    public Long getReceiverCustId() {
	        return receiverCustId;
	    }

	    public void setReceiverCustId(Long receiverCustId) {
	        this.receiverCustId = receiverCustId;
	    }

	    public Long getSenderCustId() {
	        return senderCustId;
	    }

	    public void setSenderCustId(Long senderCustId) {
	        this.senderCustId = senderCustId;
	    }

	    public String getAppId() {
	        return appId;
	    }

	    public void setAppId(String appId) {
	        this.appId = appId;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title == null ? null : title.trim();
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description == null ? null : description.trim();
	    }

	    public Date getOpenDate() {
	        return openDate;
	    }

	    public void setOpenDate(Date openDate) {
	        this.openDate = openDate;
	    }

	    public Date getCreationDate() {
	        return creationDate;
	    }

	    public void setCreationDate(Date creationDate) {
	        this.creationDate = creationDate;
	    }

	    public Integer getOpenShareType() {
	        return openShareType;
	    }

	    public void setOpenShareType(Integer openShareType) {
	        this.openShareType = openShareType;
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

	    public String getDeviceType() {
	        return deviceType;
	    }

	    public void setDeviceType(String deviceType) {
	        this.deviceType = deviceType == null ? null : deviceType.trim();
	    }

	    public Integer getIsAllDevice() {
	        return isAllDevice;
	    }

	    public void setIsAllDevice(Integer isAllDevice) {
	        this.isAllDevice = isAllDevice;
	    }

	    public String getCustomContent() {
	        return customContent;
	    }

	    public void setCustomContent(String customContent) {
	        this.customContent = customContent == null ? null : customContent.trim();
	    }
}
