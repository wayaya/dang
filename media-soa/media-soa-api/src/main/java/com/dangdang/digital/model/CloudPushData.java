package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class CloudPushData implements Serializable{
	private static final long serialVersionUID = -4031612477324033206L;

	private Long cloudPushDataId;

    private Long planId;

    private Integer appId;

    private Long custId;

    private String deviceNo;

    private Date creationDate;

    private String planParam;

    public Long getCloudPushDataId() {
        return cloudPushDataId;
    }

    public void setCloudPushDataId(Long cloudPushDataId) {
        this.cloudPushDataId = cloudPushDataId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo == null ? null : deviceNo.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPlanParam() {
        return planParam;
    }

    public void setPlanParam(String planParam) {
        this.planParam = planParam == null ? null : planParam.trim();
    }
}