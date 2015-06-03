package com.dangdang.digital.model;

import java.io.Serializable;

public class AnnouncementsCategory implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long categoryId;

    private Long parentId;

    
    private String categoryName;

    private String categoryCode;

    private String position;

    private String startDate;

    private String endDate;

    private Boolean isactiverForever;

    private String creator;

    private String createDate;

    private String lastChangeDate;

    private String modifer;

    private String icon;


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null:startDate.trim().isEmpty()?null:startDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = startDate == null ? null:endDate.trim().isEmpty()?null:endDate.trim();
    }

    public Boolean getIsactiverForever() {
        return isactiverForever;
    }

    public void setIsactiverForever(Boolean isactiverForever) {
        this.isactiverForever = isactiverForever;
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
        this.createDate = createDate == null ? null:createDate.trim();
    }

    public String getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(String lastChangeDate) {
        this.lastChangeDate = lastChangeDate == null ? null:lastChangeDate.trim();
    }

    public String getModifer() {
        return modifer;
    }

    public void setModifer(String modifer) {
        this.modifer = modifer == null ? null : modifer.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }
}