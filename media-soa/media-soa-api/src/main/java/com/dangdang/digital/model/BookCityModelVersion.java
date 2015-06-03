package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class BookCityModelVersion implements Serializable {

	private static final long serialVersionUID = -521135740591484312L;

	private Long bookcityModelVersionId;

    private String modelCode;

    private Long version;

    private Date lastChangedDate;

    public Long getBookcityModelVersionId() {
        return bookcityModelVersionId;
    }

    public void setBookcityModelVersionId(Long bookcityModelVersionId) {
        this.bookcityModelVersionId = bookcityModelVersionId;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode == null ? null : modelCode.trim();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getLastChangedDate() {
        return lastChangedDate;
    }

    public void setLastChangedDate(Date lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }
}