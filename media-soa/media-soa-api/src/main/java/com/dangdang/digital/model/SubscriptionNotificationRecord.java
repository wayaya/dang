package com.dangdang.digital.model;

import java.util.Date;

public class SubscriptionNotificationRecord {
    private Long customerSubscriptionNotificationRecordId;

    private String deviceNo;

    private Long custId;

    private Date creationDate;

    private Long mediaId;

    private String title;

    private Date lastPullChapterDate;

    private Integer deviceType;

    private String extUserId;

    private String extChannelId;

    public Long getCustomerSubscriptionNotificationRecordId() {
        return customerSubscriptionNotificationRecordId;
    }

    public void setCustomerSubscriptionNotificationRecordId(Long customerSubscriptionNotificationRecordId) {
        this.customerSubscriptionNotificationRecordId = customerSubscriptionNotificationRecordId;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getLastPullChapterDate() {
        return lastPullChapterDate;
    }

    public void setLastPullChapterDate(Date lastPullChapterDate) {
        this.lastPullChapterDate = lastPullChapterDate;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getExtUserId() {
        return extUserId;
    }

    public void setExtUserId(String extUserId) {
        this.extUserId = extUserId == null ? null : extUserId.trim();
    }

    public String getExtChannelId() {
        return extChannelId;
    }

    public void setExtChannelId(String extChannelId) {
        this.extChannelId = extChannelId == null ? null : extChannelId.trim();
    }
}