package com.dangdang.digital.model;

import java.io.Serializable;

public class NotificationApikey implements Serializable{
	private static final long serialVersionUID = 6410219532028477780L;

	private Long notificationApikeyId;

    private Integer appid;

    private String appname;

    private String apiKey;

    private String secret;

    public Long getNotificationApikeyId() {
        return notificationApikeyId;
    }

    public void setNotificationApikeyId(Long notificationApikeyId) {
        this.notificationApikeyId = notificationApikeyId;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname == null ? null : appname.trim();
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey == null ? null : apiKey.trim();
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret == null ? null : secret.trim();
    }
}