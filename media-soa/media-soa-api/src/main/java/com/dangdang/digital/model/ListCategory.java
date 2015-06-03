package com.dangdang.digital.model;

import java.io.Serializable;


public class ListCategory implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	//channel+"_"+listCode为成唯一的标识
	private String channel;//频道参数
	

	private String listCode;//标识
	
	private Integer parentId;

	private String path;
	
	private Integer categoryId;

    private String categoryName;

    private String categoryCode;

    private Integer minRecords;

    private Integer maxRecords;

    private Integer days;

    private String creator;

    private String createDate;

    private String modifer;

    private String lastChangeDate;

    private String orederDimension;

    private Long lastRecordId;

    private Integer lastListIssue;

    private String lastScheduleStartTime;

    private String lastScheduleEndTime;
    
    public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path == null ? null:path.trim().isEmpty()?null:path.trim().toLowerCase();
	}
	
    public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel == null ? null:channel.trim().toLowerCase();
	}

	public String getListCode() {
		return listCode;
	}

	public void setListCode(String listCode) {
		this.listCode = listCode == null ? null:listCode.trim().toLowerCase();
	}
	
	
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode == null ? null : categoryCode.trim();
    }

    public Integer getMinRecords() {
        return minRecords;
    }

    public void setMinRecords(Integer minRecords) {
        this.minRecords = minRecords;
    }

    public Integer getMaxRecords() {
        return maxRecords;
    }

    public void setMaxRecords(Integer maxRecords) {
        this.maxRecords = maxRecords;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifer() {
        return modifer;
    }

    public void setModifer(String modifer) {
        this.modifer = modifer == null ? null : modifer.trim();
    }

    public String getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(String lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public String getOrederDimension() {
        return orederDimension;
    }

    public void setOrederDimension(String orederDimension) {
        this.orederDimension = orederDimension == null ? null : orederDimension.trim();
    }

    public Long getLastRecordId() {
        return lastRecordId;
    }

    public void setLastRecordId(Long lastRecordId) {
        this.lastRecordId = lastRecordId;
    }

    public Integer getLastListIssue() {
        return lastListIssue;
    }

    public void setLastListIssue(Integer lastListIssue) {
        this.lastListIssue = lastListIssue;
    }

    public String getLastScheduleStartTime() {
        return lastScheduleStartTime;
    }

    public void setLastScheduleStartTime(String lastScheduleStartTime) {
        this.lastScheduleStartTime = lastScheduleStartTime;
    }

    public String getLastScheduleEndTime() {
        return lastScheduleEndTime;
    }

    public void setLastScheduleEndTime(String lastScheduleEndTime) {
        this.lastScheduleEndTime = lastScheduleEndTime;
    }
    
    public String toString(){
    	StringBuilder sb = new StringBuilder(getClass().getName());
    	sb.append("[categoryCode=").append(this.categoryCode).append(",");
    	sb.append(" categoryName=").append(this.categoryName).append(",");
    	sb.append(" createDate=").append(this.createDate).append(",");
    	sb.append(" lastListIssue=").append(this.lastListIssue).append(",");
    	sb.append(" lastScheduleEndTime=").append(this.lastScheduleEndTime).append("]");
    	return sb.toString();
    }
}