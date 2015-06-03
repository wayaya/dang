package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class CloudPushPlan implements Serializable, Cloneable{
	
	private static final long serialVersionUID = 6691086799572611593L;

	private Long cloudPushPlanId;

    private Integer appId;

    private String planName;

    private Integer planType;

    private Long pushedNumber;

    private Long openedNumber;

    private Integer planStatus;

    private Long planCondition;
    
    private Long pushStrategyId;
    
    private String versionAbove;
    
    private String iosVersionAbove;

    private Integer messageType;

    private String messageTitle;

    private String messageDescription;

    private Boolean deviceTypeAndroid;

    private Boolean deviceTypeIos;

    private Integer pushScope;

    private Integer sendMode;

    private Date sendTime;

    private Integer openType;

    private String openUrl;

    private String customContent;
    
    private String customContentIos;

    private Date startDate;

    private Date endDate;

    private Integer sendFrequency;

    private String sendFrequencyValue;

    private String sendTimeBegin;

    private String sendTimeEnd;

    private String creator;

    private Date creationDate;

    private String modifier;

    private Date lastChangedDate;

    private String userIds;

    public Long getCloudPushPlanId() {
        return cloudPushPlanId;
    }

    public void setCloudPushPlanId(Long cloudPushPlanId) {
        this.cloudPushPlanId = cloudPushPlanId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public String getIosVersionAbove() {
		return iosVersionAbove;
	}

	public void setIosVersionAbove(String iosVersionAbove) {
		this.iosVersionAbove = iosVersionAbove;
	}

	public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public Long getPushedNumber() {
        return pushedNumber;
    }

    public void setPushedNumber(Long pushedNumber) {
        this.pushedNumber = pushedNumber;
    }

    public Long getOpenedNumber() {
        return openedNumber;
    }

    public void setOpenedNumber(Long openedNumber) {
        this.openedNumber = openedNumber;
    }

    public Integer getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Integer planStatus) {
        this.planStatus = planStatus;
    }

    public Long getPlanCondition() {
        return planCondition;
    }

    public void setPlanCondition(Long planCondition) {
        this.planCondition = planCondition;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle == null ? null : messageTitle.trim();
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public void setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription == null ? null : messageDescription.trim();
    }

    public Boolean getDeviceTypeAndroid() {
        return deviceTypeAndroid;
    }

    public void setDeviceTypeAndroid(Boolean deviceTypeAndroid) {
        this.deviceTypeAndroid = deviceTypeAndroid;
    }

    public Boolean getDeviceTypeIos() {
        return deviceTypeIos;
    }

    public void setDeviceTypeIos(Boolean deviceTypeIos) {
        this.deviceTypeIos = deviceTypeIos;
    }

    public Integer getPushScope() {
        return pushScope;
    }

    public void setPushScope(Integer pushScope) {
        this.pushScope = pushScope;
    }

    public Integer getSendMode() {
        return sendMode;
    }

    public void setSendMode(Integer sendMode) {
        this.sendMode = sendMode;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getOpenType() {
        return openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public String getOpenUrl() {
        return openUrl;
    }

    public void setOpenUrl(String openUrl) {
        this.openUrl = openUrl == null ? null : openUrl.trim();
    }

    public String getCustomContent() {
        return customContent;
    }

    public void setCustomContent(String customContent) {
        this.customContent = customContent == null ? null : customContent.trim();
    }

    public String getCustomContentIos() {
		return customContentIos;
	}

	public void setCustomContentIos(String customContentIos) {
		this.customContentIos = customContentIos;
	}

	public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getSendFrequency() {
        return sendFrequency;
    }

    public void setSendFrequency(Integer sendFrequency) {
        this.sendFrequency = sendFrequency;
    }

    public String getSendFrequencyValue() {
        return sendFrequencyValue;
    }

    public void setSendFrequencyValue(String sendFrequencyValue) {
        this.sendFrequencyValue = sendFrequencyValue == null ? null : sendFrequencyValue.trim();
    }

    public String getSendTimeBegin() {
        return sendTimeBegin;
    }

    public void setSendTimeBegin(String sendTimeBegin) {
        this.sendTimeBegin = sendTimeBegin == null ? null : sendTimeBegin.trim();
    }

    public String getSendTimeEnd() {
        return sendTimeEnd;
    }

    public void setSendTimeEnd(String sendTimeEnd) {
        this.sendTimeEnd = sendTimeEnd == null ? null : sendTimeEnd.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getLastChangedDate() {
        return lastChangedDate;
    }

    public void setLastChangedDate(Date lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds == null ? null : userIds.trim();
    }
    
    @Override  
    public Object clone() throws CloneNotSupportedException {  
        return (CloudPushPlan)super.clone();  
    }  

	public Long getPushStrategyId() {
		return pushStrategyId;
	}

	public void setPushStrategyId(Long pushStrategyId) {
		this.pushStrategyId = pushStrategyId;
	}

	public String getVersionAbove() {
		return versionAbove;
	}

	public void setVersionAbove(String versionAbove) {
		this.versionAbove = versionAbove;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
		result = prime * result
				+ ((cloudPushPlanId == null) ? 0 : cloudPushPlanId.hashCode());
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result
				+ ((customContent == null) ? 0 : customContent.hashCode());
		result = prime * result
				+ ((customContentIos == null) ? 0 : customContentIos.hashCode());
		result = prime
				* result
				+ ((deviceTypeAndroid == null) ? 0 : deviceTypeAndroid
						.hashCode());
		result = prime * result
				+ ((deviceTypeIos == null) ? 0 : deviceTypeIos.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((lastChangedDate == null) ? 0 : lastChangedDate.hashCode());
		result = prime
				* result
				+ ((messageDescription == null) ? 0 : messageDescription
						.hashCode());
		result = prime * result
				+ ((messageTitle == null) ? 0 : messageTitle.hashCode());
		result = prime * result
				+ ((messageType == null) ? 0 : messageType.hashCode());
		result = prime * result
				+ ((modifier == null) ? 0 : modifier.hashCode());
		result = prime * result
				+ ((openType == null) ? 0 : openType.hashCode());
		result = prime * result + ((openUrl == null) ? 0 : openUrl.hashCode());
		result = prime * result
				+ ((openedNumber == null) ? 0 : openedNumber.hashCode());
		result = prime * result
				+ ((planCondition == null) ? 0 : planCondition.hashCode());
		result = prime * result
				+ ((planName == null) ? 0 : planName.hashCode());
		result = prime * result
				+ ((planStatus == null) ? 0 : planStatus.hashCode());
		result = prime * result
				+ ((planType == null) ? 0 : planType.hashCode());
		result = prime * result
				+ ((pushScope == null) ? 0 : pushScope.hashCode());
		result = prime * result
				+ ((pushStrategyId == null) ? 0 : pushStrategyId.hashCode());
		result = prime * result
				+ ((pushedNumber == null) ? 0 : pushedNumber.hashCode());
		result = prime * result
				+ ((sendFrequency == null) ? 0 : sendFrequency.hashCode());
		result = prime
				* result
				+ ((sendFrequencyValue == null) ? 0 : sendFrequencyValue
						.hashCode());
		result = prime * result
				+ ((sendMode == null) ? 0 : sendMode.hashCode());
		result = prime * result
				+ ((sendTime == null) ? 0 : sendTime.hashCode());
		result = prime * result
				+ ((sendTimeBegin == null) ? 0 : sendTimeBegin.hashCode());
		result = prime * result
				+ ((sendTimeEnd == null) ? 0 : sendTimeEnd.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((userIds == null) ? 0 : userIds.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CloudPushPlan other = (CloudPushPlan) obj;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		if (cloudPushPlanId == null) {
			if (other.cloudPushPlanId != null)
				return false;
		} else if (!cloudPushPlanId.equals(other.cloudPushPlanId))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (customContent == null) {
			if (other.customContent != null)
				return false;
		} else if (!customContent.equals(other.customContent))
			return false;
		if (customContentIos == null) {
			if (other.customContentIos != null)
				return false;
		} else if (!customContentIos.equals(other.customContentIos))
			return false;
		if (deviceTypeAndroid == null) {
			if (other.deviceTypeAndroid != null)
				return false;
		} else if (!deviceTypeAndroid.equals(other.deviceTypeAndroid))
			return false;
		if (deviceTypeIos == null) {
			if (other.deviceTypeIos != null)
				return false;
		} else if (!deviceTypeIos.equals(other.deviceTypeIos))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (lastChangedDate == null) {
			if (other.lastChangedDate != null)
				return false;
		} else if (!lastChangedDate.equals(other.lastChangedDate))
			return false;
		if (messageDescription == null) {
			if (other.messageDescription != null)
				return false;
		} else if (!messageDescription.equals(other.messageDescription))
			return false;
		if (messageTitle == null) {
			if (other.messageTitle != null)
				return false;
		} else if (!messageTitle.equals(other.messageTitle))
			return false;
		if (messageType == null) {
			if (other.messageType != null)
				return false;
		} else if (!messageType.equals(other.messageType))
			return false;
		if (modifier == null) {
			if (other.modifier != null)
				return false;
		} else if (!modifier.equals(other.modifier))
			return false;
		if (openType == null) {
			if (other.openType != null)
				return false;
		} else if (!openType.equals(other.openType))
			return false;
		if (openUrl == null) {
			if (other.openUrl != null)
				return false;
		} else if (!openUrl.equals(other.openUrl))
			return false;
		if (openedNumber == null) {
			if (other.openedNumber != null)
				return false;
		} else if (!openedNumber.equals(other.openedNumber))
			return false;
		if (planCondition == null) {
			if (other.planCondition != null)
				return false;
		} else if (!planCondition.equals(other.planCondition))
			return false;
		if (planName == null) {
			if (other.planName != null)
				return false;
		} else if (!planName.equals(other.planName))
			return false;
		if (planStatus == null) {
			if (other.planStatus != null)
				return false;
		} else if (!planStatus.equals(other.planStatus))
			return false;
		if (planType == null) {
			if (other.planType != null)
				return false;
		} else if (!planType.equals(other.planType))
			return false;
		if (pushScope == null) {
			if (other.pushScope != null)
				return false;
		} else if (!pushScope.equals(other.pushScope))
			return false;
		if (pushStrategyId == null) {
			if (other.pushStrategyId != null)
				return false;
		} else if (!pushStrategyId.equals(other.pushStrategyId))
			return false;
		if (pushedNumber == null) {
			if (other.pushedNumber != null)
				return false;
		} else if (!pushedNumber.equals(other.pushedNumber))
			return false;
		if (sendFrequency == null) {
			if (other.sendFrequency != null)
				return false;
		} else if (!sendFrequency.equals(other.sendFrequency))
			return false;
		if (sendFrequencyValue == null) {
			if (other.sendFrequencyValue != null)
				return false;
		} else if (!sendFrequencyValue.equals(other.sendFrequencyValue))
			return false;
		if (sendMode == null) {
			if (other.sendMode != null)
				return false;
		} else if (!sendMode.equals(other.sendMode))
			return false;
		if (sendTime == null) {
			if (other.sendTime != null)
				return false;
		} else if (!sendTime.equals(other.sendTime))
			return false;
		if (sendTimeBegin == null) {
			if (other.sendTimeBegin != null)
				return false;
		} else if (!sendTimeBegin.equals(other.sendTimeBegin))
			return false;
		if (sendTimeEnd == null) {
			if (other.sendTimeEnd != null)
				return false;
		} else if (!sendTimeEnd.equals(other.sendTimeEnd))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (userIds == null) {
			if (other.userIds != null)
				return false;
		} else if (!userIds.equals(other.userIds))
			return false;
		return true;
	}
}