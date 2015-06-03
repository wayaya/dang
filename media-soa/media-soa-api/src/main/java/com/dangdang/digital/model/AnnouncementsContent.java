package com.dangdang.digital.model;

import java.io.Serializable;


public class AnnouncementsContent implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long announcementId;

    private String categoryCode;

    private String creationDate;

    private String creator;

    private String startDate;

    private String endDate;

    private String content;

    private String url;

    private String lastChangeDate;

    private String modifer;

    private Integer status;

    private Integer orderValue;

    public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode == null ? null : categoryCode.trim();
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate  == null ? null : creationDate.trim().isEmpty()? null:creationDate.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim().isEmpty()? null:startDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim().isEmpty()? null:endDate.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(String lastChangeDate) {
        this.lastChangeDate = lastChangeDate == null ? null : lastChangeDate.trim().isEmpty()? null:lastChangeDate.trim();;
    }

    public String getModifer() {
        return modifer;
    }

    public void setModifer(String modifer) {
        this.modifer = modifer == null ? null : modifer.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }
}