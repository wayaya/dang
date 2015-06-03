package com.dangdang.digital.model;

import java.io.Serializable;

public class CloudPushCondition implements Serializable{
	
	private static final long serialVersionUID = 1957174060783012504L;

    private Long cloudPushConditionId;

    private Integer appId;

    private String conditionName;

    public Long getCloudPushConditionId() {
        return cloudPushConditionId;
    }

    public void setCloudPushConditionId(Long cloudPushConditionId) {
        this.cloudPushConditionId = cloudPushConditionId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName == null ? null : conditionName.trim();
    }
}