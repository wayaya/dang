package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class CustomerSubscription implements Serializable{

	private static final long serialVersionUID = 2731484348950081839L;

	private Long customerSubscriptionId;

    private Integer appId;

    private String deviceNo;

    private Long custId;

    private Long mediaId;

    private Integer subscribeRelation;

    private Integer status;

    private Date creationDate;

    public Long getCustomerSubscriptionId() {
        return customerSubscriptionId;
    }

    public void setCustomerSubscriptionId(Long customerSubscriptionId) {
        this.customerSubscriptionId = customerSubscriptionId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo == null ? null : deviceNo.trim();
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getSubscribeRelation() {
        return subscribeRelation;
    }

    public void setSubscribeRelation(Integer subscribeRelation) {
        this.subscribeRelation = subscribeRelation;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}