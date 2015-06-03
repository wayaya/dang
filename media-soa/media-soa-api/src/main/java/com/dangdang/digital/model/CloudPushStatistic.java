package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class CloudPushStatistic implements Serializable{
	private static final long serialVersionUID = 868313091747467055L;

	private Long cloudPushStatisticsId;

    private Long cloudPushPlanId;

    private Long pushedNumber;

    private Long openedNumber;

    private Date statisticDay;

    public Long getCloudPushStatisticsId() {
        return cloudPushStatisticsId;
    }

    public void setCloudPushStatisticsId(Long cloudPushStatisticsId) {
        this.cloudPushStatisticsId = cloudPushStatisticsId;
    }

    public Long getCloudPushPlanId() {
        return cloudPushPlanId;
    }

    public void setCloudPushPlanId(Long cloudPushPlanId) {
        this.cloudPushPlanId = cloudPushPlanId;
    }

    public Long getPushedNumber() {
        return pushedNumber;
    }

    public void setPushedNumber(Long pushedNumber) {
        this.pushedNumber = pushedNumber;
    }

    public Long getOpenedNumber() {
        return openedNumber;
    }

    public void setOpenedNumber(Long openedNumber) {
        this.openedNumber = openedNumber;
    }

    public Date getStatisticDay() {
        return statisticDay;
    }

    public void setStatisticDay(Date statisticDay) {
        this.statisticDay = statisticDay;
    }
}