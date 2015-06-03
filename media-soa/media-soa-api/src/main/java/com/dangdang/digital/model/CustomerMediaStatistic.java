package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class CustomerMediaStatistic implements Serializable{

	private static final long serialVersionUID = 1969823925165336932L;

	private Long customerMediaStatisticId;

    private Long custId;

    private Long mediaId;

    private Byte relationType;

    private Date relationDate;

    private Date creationDate;

    public Long getCustomerMediaStatisticId() {
        return customerMediaStatisticId;
    }

    public void setCustomerMediaStatisticId(Long customerMediaStatisticId) {
        this.customerMediaStatisticId = customerMediaStatisticId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Byte getRelationType() {
        return relationType;
    }

    public void setRelationType(Byte relationType) {
        this.relationType = relationType;
    }

    public Date getRelationDate() {
        return relationDate;
    }

    public void setRelationDate(Date relationDate) {
        this.relationDate = relationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}