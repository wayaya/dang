package com.dangdang.digital.model;

import java.io.Serializable;

public class PushConditionParam implements Serializable{
	
	private static final long serialVersionUID = -1907929983159305969L;
	
	private Long pushConditionParamsId;

    private Long pushPlanId;

    private Long conditionId;

    private String keyInputName;

    private String keyName;

    private String keyValue;

    public Long getPushConditionParamsId() {
        return pushConditionParamsId;
    }

    public void setPushConditionParamsId(Long pushConditionParamsId) {
        this.pushConditionParamsId = pushConditionParamsId;
    }

    public Long getPushPlanId() {
        return pushPlanId;
    }

    public void setPushPlanId(Long pushPlanId) {
        this.pushPlanId = pushPlanId;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }

    public String getKeyInputName() {
        return keyInputName;
    }

    public void setKeyInputName(String keyInputName) {
        this.keyInputName = keyInputName == null ? null : keyInputName.trim();
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName == null ? null : keyName.trim();
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue == null ? null : keyValue.trim();
    }
}