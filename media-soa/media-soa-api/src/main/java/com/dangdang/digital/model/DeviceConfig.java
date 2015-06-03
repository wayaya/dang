package com.dangdang.digital.model;

import java.util.Date;

public class DeviceConfig {
    private Long mediaDeviceId;

    private Long deviceTypeId;

    private String deviceTypeCode;

    private String deviceTypeName;

    private Date createTime;

    private Long mediaId;

    public Long getMediaDeviceId() {
        return mediaDeviceId;
    }

    public void setMediaDeviceId(Long mediaDeviceId) {
        this.mediaDeviceId = mediaDeviceId;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceTypeCode() {
        return deviceTypeCode;
    }

    public void setDeviceTypeCode(String deviceTypeCode) {
        this.deviceTypeCode = deviceTypeCode == null ? null : deviceTypeCode.trim();
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName == null ? null : deviceTypeName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }
}