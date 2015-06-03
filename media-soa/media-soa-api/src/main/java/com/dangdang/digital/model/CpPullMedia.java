package com.dangdang.digital.model;

import java.util.Date;

public class CpPullMedia {
    private Long id;

    private Date lastModifyDate;

    private String cpMediaId;

    private Long ddMediaId;

    private Integer isFull;

    private String cpCode;

    private Long batchId;

    private String mediaName;

    private Date creationDate;

    private Integer status;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getCpMediaId() {
        return cpMediaId;
    }

    public void setCpMediaId(String cpMediaId) {
        this.cpMediaId = cpMediaId == null ? null : cpMediaId.trim();
    }

    public Long getDdMediaId() {
        return ddMediaId;
    }

    public void setDdMediaId(Long ddMediaId) {
        this.ddMediaId = ddMediaId;
    }

    public Integer getIsFull() {
        return isFull;
    }

    public void setIsFull(Integer isFull) {
        this.isFull = isFull;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode == null ? null : cpCode.trim();
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName == null ? null : mediaName.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}