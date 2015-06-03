package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class CloudPushPlanStatus implements Serializable, Cloneable{
	
	private static final long serialVersionUID = 5504693350154226501L;

	private Long cloudPushPlanStatusId;

    private Long planId;

    private Integer planType;

    private Integer planJobStatus;

    private Integer planSendStatus;

    private Date sendTime;

    private Date creationDate;

    public Long getCloudPushPlanStatusId() {
        return cloudPushPlanStatusId;
    }

    public void setCloudPushPlanStatusId(Long cloudPushPlanStatusId) {
        this.cloudPushPlanStatusId = cloudPushPlanStatusId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public Integer getPlanJobStatus() {
        return planJobStatus;
    }

    public void setPlanJobStatus(Integer planJobStatus) {
        this.planJobStatus = planJobStatus;
    }

    public Integer getPlanSendStatus() {
        return planSendStatus;
    }

    public void setPlanSendStatus(Integer planSendStatus) {
        this.planSendStatus = planSendStatus;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    @Override  
    public Object clone() throws CloneNotSupportedException {  
        return (CloudPushPlanStatus)super.clone();  
    }  
}