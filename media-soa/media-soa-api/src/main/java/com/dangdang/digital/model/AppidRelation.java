package com.dangdang.digital.model;

import java.io.Serializable;

/**
 * Description: appid和appkey和device_type和device_type编号的关系
 * All Rights Reserved.
 * @version 1.0  2015年4月13日 下午5:41:08  by yangzhenping（yangzhenping@dangdang.com）创建
 */
public class AppidRelation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer appidRelationId;	
	/**
	 * 应用编号
	 */
	private Integer appid;
	/**
	 * 应用名称(大写)
	 */
	private String appkey;
	/**
	 * 设备编号
	 */
	private Integer deviceType;
	/**
	 * 设备名称（小写）
	 */
	private String deviceKey;
	public Integer getAppidRelationId() {
		return appidRelationId;
	}
	public void setAppidRelationId(Integer appidRelationId) {
		this.appidRelationId = appidRelationId;
	}
	public Integer getAppid() {
		return appid;
	}
	public void setAppid(Integer appid) {
		this.appid = appid;
	}
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceKey() {
		return deviceKey;
	}
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}
	
}
