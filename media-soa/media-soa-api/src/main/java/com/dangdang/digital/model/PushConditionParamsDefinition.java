package com.dangdang.digital.model;

import java.io.Serializable;

public class PushConditionParamsDefinition implements Serializable{
	private static final long serialVersionUID = 8392385912263338169L;

	private Long pushConditionParamsDefinitionId;

    private Long conditionId;

    private Integer keyType;

    private String keyInputName;

    private String keyName;

    public Long getPushConditionParamsDefinitionId() {
        return pushConditionParamsDefinitionId;
    }

    public void setPushConditionParamsDefinitionId(Long pushConditionParamsDefinitionId) {
        this.pushConditionParamsDefinitionId = pushConditionParamsDefinitionId;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }

    public Integer getKeyType() {
        return keyType;
    }

    public void setKeyType(Integer keyType) {
        this.keyType = keyType;
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
}