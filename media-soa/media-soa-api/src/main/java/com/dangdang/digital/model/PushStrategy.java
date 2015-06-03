package com.dangdang.digital.model;

import java.io.Serializable;

public class PushStrategy implements Serializable{

	private static final long serialVersionUID = -8647499024934276306L;

	private Long cloudPushStrategyId;

    private Integer appId;

    private Integer strategyType;

    private String strategyName;

    public Long getCloudPushStrategyId() {
        return cloudPushStrategyId;
    }

    public void setCloudPushStrategyId(Long cloudPushStrategyId) {
        this.cloudPushStrategyId = cloudPushStrategyId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(Integer strategyType) {
        this.strategyType = strategyType;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName == null ? null : strategyName.trim();
    }
}