package com.dangdang.digital.model;

import java.io.Serializable;

public class DailyStatisticBuyAlsoBuy implements Serializable{
	
	private static final long serialVersionUID = -7325554383335845642L;

	private Long buyalsobuyDailyStatisticId;

    private String resultKey;

    public Long getBuyalsobuyDailyStatisticId() {
        return buyalsobuyDailyStatisticId;
    }

    public void setBuyalsobuyDailyStatisticId(Long buyalsobuyDailyStatisticId) {
        this.buyalsobuyDailyStatisticId = buyalsobuyDailyStatisticId;
    }

    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey == null ? null : resultKey.trim();
    }
}