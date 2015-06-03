package com.dangdang.digital.model;

import java.io.Serializable;

public class PushConditionVariable implements Serializable{
	
	private static final long serialVersionUID = 2829956619123594406L;

	private Long pushConditionVariableId;

    private Long conditionId;

    private String variableName;

    private String variableDescription;

    public Long getPushConditionVariableId() {
        return pushConditionVariableId;
    }

    public void setPushConditionVariableId(Long pushConditionVariableId) {
        this.pushConditionVariableId = pushConditionVariableId;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName == null ? null : variableName.trim();
    }

    public String getVariableDescription() {
        return variableDescription;
    }

    public void setVariableDescription(String variableDescription) {
        this.variableDescription = variableDescription == null ? null : variableDescription.trim();
    }
}