package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable{
	private static final long serialVersionUID = -341208528513535388L;

	/**
	 * 主键
	 */
    private Long notificationId;

    /**
     * App id, 1 当当读书， 2 当读小说 ， 3 精品阅读
     */
    private Integer appId;

    /**
     * 客户端app的version
     */
    private String appClientVersion;

    /**
     * 设备序列号
     */
    private String deviceNo;

    /**
     * 安卓还是ios 1：浏览器设备；2：PC设备；3：Andriod设备；4：iOS设备；5：Windows Phone设备；
     */
    private Integer deviceType;

    /**
     * 当当custId （0表示guest，未登录）
     */
    private Long custId;

    /**
     * 百度userId
     */
    private String extUserId;

    /**
     * 百度channelId(就是百度认为的设备ID)
     */
    private String extChannelId;

    /**
     * 当当渠道ID
     */
    private String channelId;
    
    /**
     * 通知开关状态：0 关 ; 1 开
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 此设备最后一次启动应用/登录 的时间
     */
    private Date lastChangedDate;

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppClientVersion() {
        return appClientVersion;
    }

    public void setAppClientVersion(String appClientVersion) {
        this.appClientVersion = appClientVersion == null ? null : appClientVersion.trim();
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo == null ? null : deviceNo.trim();
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getExtUserId() {
        return extUserId;
    }

    public void setExtUserId(String extUserId) {
        this.extUserId = extUserId == null ? null : extUserId.trim();
    }

    public String getExtChannelId() {
        return extChannelId;
    }

    public void setExtChannelId(String extChannelId) {
        this.extChannelId = extChannelId == null ? null : extChannelId.trim();
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId == null ? null : channelId.trim();
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}