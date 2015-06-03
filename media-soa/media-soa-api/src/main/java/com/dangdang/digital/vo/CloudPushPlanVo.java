package com.dangdang.digital.vo;

import java.io.Serializable;

public class CloudPushPlanVo implements Serializable{
	
	private static final long serialVersionUID = -1735959405668311719L;

	private Long cloudPushPlanId;
	
    private Integer planStatus;

    private Long planCondition;
    
    private String messageTitle;

    private String messageDescription;
    
    private Boolean deviceTypeAndroid;

    private Boolean deviceTypeIos;

	public Boolean getDeviceTypeAndroid() {
		return deviceTypeAndroid;
	}

	public void setDeviceTypeAndroid(Boolean deviceTypeAndroid) {
		this.deviceTypeAndroid = deviceTypeAndroid;
	}

	public Boolean getDeviceTypeIos() {
		return deviceTypeIos;
	}

	public void setDeviceTypeIos(Boolean deviceTypeIos) {
		this.deviceTypeIos = deviceTypeIos;
	}

	public Long getCloudPushPlanId() {
		return cloudPushPlanId;
	}

	public void setCloudPushPlanId(Long cloudPushPlanId) {
		this.cloudPushPlanId = cloudPushPlanId;
	}

	public Integer getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(Integer planStatus) {
		this.planStatus = planStatus;
	}

	public Long getPlanCondition() {
		return planCondition;
	}

	public void setPlanCondition(Long planCondition) {
		this.planCondition = planCondition;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageDescription() {
		return messageDescription;
	}

	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}

}
