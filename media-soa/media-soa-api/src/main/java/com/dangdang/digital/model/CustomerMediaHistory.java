package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class CustomerMediaHistory implements Serializable{

	private static final long serialVersionUID = 6422986548520787246L;

	private Long customerMediaHistoryId;

    private Long custId;

    private Byte type;

    private Date creationDate;

    private String mediaData;

    public Long getCustomerMediaHistoryId() {
        return customerMediaHistoryId;
    }

    public void setCustomerMediaHistoryId(Long customerMediaHistoryId) {
        this.customerMediaHistoryId = customerMediaHistoryId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getMediaData() {
        return mediaData;
    }

    public void setMediaData(String mediaData) {
        this.mediaData = mediaData == null ? null : mediaData.trim();
    }
}