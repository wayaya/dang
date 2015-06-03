package com.dangdang.digital.model;

import java.io.Serializable;

public class DailyStatisticReadAlsoRead implements Serializable{
	
	private static final long serialVersionUID = 2343648911020379215L;

	private Long readalsoreadDailyStatisticId;

    private String resultKey;

    public Long getReadalsoreadDailyStatisticId() {
        return readalsoreadDailyStatisticId;
    }

    public void setReadalsoreadDailyStatisticId(Long readalsoreadDailyStatisticId) {
        this.readalsoreadDailyStatisticId = readalsoreadDailyStatisticId;
    }

    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey == null ? null : resultKey.trim();
    }
}