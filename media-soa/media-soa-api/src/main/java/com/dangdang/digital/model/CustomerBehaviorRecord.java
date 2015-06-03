package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class CustomerBehaviorRecord implements Serializable{

	private static final long serialVersionUID = -5089173935597754550L;

	private Long customerBehaviorRecordId;

    private Long custId;

    private Byte behaviorType;

    private Date createDate;

    private String custBehaviorData;

    public Long getCustomerBehaviorRecordId() {
        return customerBehaviorRecordId;
    }

    public void setCustomerBehaviorRecordId(Long customerBehaviorRecordId) {
        this.customerBehaviorRecordId = customerBehaviorRecordId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Byte getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(Byte behaviorType) {
        this.behaviorType = behaviorType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCustBehaviorData() {
        return custBehaviorData;
    }

    public void setCustBehaviorData(String custBehaviorData) {
        this.custBehaviorData = custBehaviorData == null ? null : custBehaviorData.trim();
    }
}