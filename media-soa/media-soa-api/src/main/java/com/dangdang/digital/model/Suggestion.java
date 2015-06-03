package com.dangdang.digital.model;


public class Suggestion {
    private Long suggestionId;

    private Long custId;

	private String deviceType;
    
    private String userName;

    private String nickName;

    private String suggestDate;

    private String advice;

    private String contactWay;

 

	private String clientOs;
    
    private String clientVersion;
    
    /**
     * 平台来源
     */
    private String platform;
    
    public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform == null ? null :platform.trim();
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType == null ? null:deviceType.trim();
	}
	
	
    public String getClientOs() {
 		return clientOs;
 	}

 	public void setClientOs(String clientOs) {
 		this.clientOs = clientOs == null ? null :clientOs.trim();
 	}

 	public String getClientVersion() {
 		return clientVersion;
 	}

 	public void setClientVersion(String clientVersion) {
 		this.clientVersion = clientVersion == null ? null :clientVersion.trim();
 	}
 	
    public Long getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(Long suggestionId) {
        this.suggestionId = suggestionId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getSuggestDate() {
        return suggestDate;
    }

    public void setSuggestDate(String suggestDate) {
        this.suggestDate = suggestDate== null ? null : suggestDate.trim();
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice == null ? null : advice.trim();
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay == null ? null : contactWay.trim();
    }
}