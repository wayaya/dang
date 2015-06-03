package com.dangdang.digital.model;

import java.util.Date;

public class ActivityType {
	/**
     * 促销活动类型 主键ID
     */
    private Integer activityTypeId;

    /**
     * 促销活动类型 名称
     */
    private String activityTypeName;

    /**
     * 类型状态(0：无效  1:有效，)
     */
    private Integer status;

    /**
     * 活动类型编号
     */
    private String activityTypeCode;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 修改时间
     */
    private Date lastChangedDate;

    /**
     * 是否实时到账(1:实时到账，0：非实时到账)
     */
    private Integer isPromptlyToAccount;

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName == null ? null : activityTypeName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getActivityTypeCode() {
        return activityTypeCode;
    }

    public void setActivityTypeCode(String activityTypeCode) {
        this.activityTypeCode = activityTypeCode == null ? null : activityTypeCode.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastChangedDate() {
        return lastChangedDate;
    }

    public void setLastChangedDate(Date lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }

    public Integer getIsPromptlyToAccount() {
        return isPromptlyToAccount;
    }

    public void setIsPromptlyToAccount(Integer isPromptlyToAccount) {
        this.isPromptlyToAccount = isPromptlyToAccount;
    }

	@Override
	public String toString() {
		return "ActivityType [activityTypeId=" + activityTypeId
				+ ", activityTypeName=" + activityTypeName + ", status="
				+ status + ", activityTypeCode=" + activityTypeCode
				+ ", creator=" + creator + ", modifier=" + modifier
				+ ", creationDate=" + creationDate + ", lastChangedDate="
				+ lastChangedDate + ", isPromptlyToAccount="
				+ isPromptlyToAccount + "]";
	}
}