package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

import javacommon.util.DateTimeUtils;

/**
 * @author dangdang
 */
public class UserDevice implements Serializable {

	private static final long serialVersionUID = -2564028529778837005L;

	// alias
	public static final String TABLE_ALIAS = "UserDevice";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_DEVICE_NO = "deviceNo";
	public static final String ALIAS_DEVICE_TYPE = "deviceType";
	public static final String ALIAS_USERNAME = "username";
	public static final String ALIAS_CREATED = "created";
	public static final String ALLAS_STATE = "状态";

	public static final int STATE_DEAD = 0;
	public static final int STATE_LIVE = 1;


	protected static final String DATE_FORMAT = "yyyy-MM-dd";

	protected static final String TIME_FORMAT = "HH:mm:ss";

	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	// date formats
	public static final String FORMAT_CREATED = DATE_TIME_FORMAT;

	// columns START
	private Long id;
	private String deviceNo;
	private String deviceType;
	private String username;
	private java.util.Date created;
	private Integer state;
	private String publicKey;
	private String publicKey_pdf;
	private Long custId;
	
	//用户登录token
	private String loginToken;
	//用户上次登录时间
	private Date lastLoginTime;
	// columns END

	public UserDevice() {
	}

	public UserDevice(long custId, String username, String deviceNo,
			String deviceType, String publicKey, int state, String publicKey_pdf) {
		this.custId = custId;
		this.deviceNo = deviceNo;
		this.deviceType = deviceType;
		this.username = username;
		this.created = new Date();
		this.state = state;
		this.publicKey = publicKey;
		this.publicKey_pdf = publicKey_pdf;
	}
	
	public String date2String(final java.util.Date date, final String dateFormat) {
		return DateTimeUtils.format(date, dateFormat);
	}

	public <T extends java.util.Date> T string2Date(
			final String dateString, final String dateFormat,
			final Class<T> targetResultType) {
		return DateTimeUtils.parse(dateString, dateFormat, targetResultType);
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Long getId() {
		return this.id;
	}

	public void setDeviceNo(String value) {
		this.deviceNo = value;
	}

	public String getDeviceNo() {
		return this.deviceNo;
	}

	public void setDeviceType(String value) {
		this.deviceType = value;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setUsername(String value) {
		this.username = value;
	}

	public String getUsername() {
		return this.username;
	}

	public String getCreatedString() {
		return date2String(getCreated(), FORMAT_CREATED);
	}

	public void setCreatedString(String value) {
		setCreated(string2Date(value, FORMAT_CREATED, java.util.Date.class));
	}

	public void setCreated(java.util.Date value) {
		this.created = value;
	}

	public java.util.Date getCreated() {
		return this.created;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPublicKey_pdf() {
		return publicKey_pdf;
	}

	public void setPublicKey_pdf(String publicKey_pdf) {
		this.publicKey_pdf = publicKey_pdf;
	}

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
}
