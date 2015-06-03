package com.dangdang.digital.model;

import java.io.Serializable;

public class PushStrategyParam implements Serializable{
	
	private static final long serialVersionUID = -6448921125721046326L;

	private Long cloudPushStrategyParamsId;

    private Long strategyId;

    private String paramName;
    
    private Integer deviceType;

    private String paramDefaultValue;

    private String paramDescription;

    public Long getCloudPushStrategyParamsId() {
        return cloudPushStrategyParamsId;
    }

    public void setCloudPushStrategyParamsId(Long cloudPushStrategyParamsId) {
        this.cloudPushStrategyParamsId = cloudPushStrategyParamsId;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    public String getParamDefaultValue() {
        return paramDefaultValue;
    }

    public void setParamDefaultValue(String paramDefaultValue) {
        this.paramDefaultValue = paramDefaultValue == null ? null : paramDefaultValue.trim();
    }

    public String getParamDescription() {
        return paramDescription;
    }

    public void setParamDescription(String paramDescription) {
        this.paramDescription = paramDescription == null ? null : paramDescription.trim();
    }

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
}