package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class CustomerGiftRecord implements Serializable{
	
	private static final long serialVersionUID = -768002653028649367L;

	private Long customerGiftRecordId;

    private String platformSource;

    private Long senderCustId;

    private Long receiverCustId;

    private Integer type;

    private String deviceType;

    private Long amount;

    private String content;

    private Integer status;

    private Date lastChangedDate;

    private Date creationDate;

    private String extField1;

    private String extField2;

    private String extField3;

    public Long getCustomerGiftRecordId() {
        return customerGiftRecordId;
    }

    public void setCustomerGiftRecordId(Long customerGiftRecordId) {
        this.customerGiftRecordId = customerGiftRecordId;
    }

    public String getPlatformSource() {
        return platformSource;
    }

    public void setPlatformSource(String platformSource) {
        this.platformSource = platformSource == null ? null : platformSource.trim();
    }

    public Long getSenderCustId() {
        return senderCustId;
    }

    public void setSenderCustId(Long senderCustId) {
        this.senderCustId = senderCustId;
    }

    public Long getReceiverCustId() {
        return receiverCustId;
    }

    public void setReceiverCustId(Long receiverCustId) {
        this.receiverCustId = receiverCustId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastChangedDate() {
        return lastChangedDate;
    }

    public void setLastChangedDate(Date lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getExtField1() {
        return extField1;
    }

    public void setExtField1(String extField1) {
        this.extField1 = extField1 == null ? null : extField1.trim();
    }

    public String getExtField2() {
        return extField2;
    }

    public void setExtField2(String extField2) {
        this.extField2 = extField2 == null ? null : extField2.trim();
    }

    public String getExtField3() {
        return extField3;
    }

    public void setExtField3(String extField3) {
        this.extField3 = extField3 == null ? null : extField3.trim();
    }
}