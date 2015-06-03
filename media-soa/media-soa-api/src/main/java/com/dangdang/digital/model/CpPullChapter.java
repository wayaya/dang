package com.dangdang.digital.model;

import java.util.Date;

public class CpPullChapter {
    private Long id;

    private String cpChapterId;

    private Long batchId;

    private Long ddChapterId;

    private Long ddMediaId;

    private Integer orderNum;

    private String cpCode;

    private String cpMediaId;

    private String chapterName;

    private String tomId;

    private Date creationDate;

    private Date lastModifyDate;

    private Integer status;

    private String content;
    
    private Integer indexOrder;
    
    private Integer priceUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpChapterId() {
        return cpChapterId;
    }

    public void setCpChapterId(String cpChapterId) {
        this.cpChapterId = cpChapterId == null ? null : cpChapterId.trim();
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Long getDdChapterId() {
        return ddChapterId;
    }

    public void setDdChapterId(Long ddChapterId) {
        this.ddChapterId = ddChapterId;
    }

    public Long getDdMediaId() {
        return ddMediaId;
    }

    public void setDdMediaId(Long ddMediaId) {
        this.ddMediaId = ddMediaId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode == null ? null : cpCode.trim();
    }

    public String getCpMediaId() {
        return cpMediaId;
    }

    public void setCpMediaId(String cpMediaId) {
        this.cpMediaId = cpMediaId == null ? null : cpMediaId.trim();
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName == null ? null : chapterName.trim();
    }

    public String getTomId() {
        return tomId;
    }

    public void setTomId(String tomId) {
        this.tomId = tomId == null ? null : tomId.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
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

	public Integer getIndexOrder() {
		return indexOrder;
	}

	public void setIndexOrder(Integer indexOrder) {
		this.indexOrder = indexOrder;
	}

	public Integer getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(Integer priceUnit) {
		this.priceUnit = priceUnit;
	}
    
}