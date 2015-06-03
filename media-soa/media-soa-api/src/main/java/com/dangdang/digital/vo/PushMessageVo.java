package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.Map;
/**
 * 
 * Description: 这个类是当当读书的Push消息发送成功后，组织一个实力发送到Rabbit MQ，通知当当读书发送内容
 * All Rights Reserved.
 * @version 1.0  2015年1月28日 下午8:02:30  by 魏嵩（weisong@dangdang.com）创建
 */
public class PushMessageVo implements Serializable{

	private static final long serialVersionUID = -6103650727664762048L;
	/**
	 * 应用id
	 */
	private int appId;
	/**
	 * pushType 3表示所有用户  1表示单个用户
	 */
	private int pushType;
	/**
	 * deviceType 3 Android 4 IOS
	 */
	private int deviceType;
	
	/**
	 * pushType是3的时候，custId是null，如果该device没有登录过，也会是null
	 */
	private Long custId;
	private String messageTitle;
	private String messageDescription;
	/**
	 * 设备号 pushType是3的时候 为null
	 */
	private String deviceSerialNo;
	
	/**
	 * Push中的参数
	 */
	private Map<String, Object> customContent;
	
	
	public Map<String, Object> getCustomContent() {
		return customContent;
	}
	public void setCustomContent(Map<String, Object> customContent) {
		this.customContent = customContent;
	}
	public int getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getMessageDescription() {
		return messageDescription;
	}
	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}
	public int getPushType() {
		return pushType;
	}
	public void setPushType(int pushType) {
		this.pushType = pushType;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getDeviceSerialNo() {
		return deviceSerialNo;
	}
	public void setDeviceSerialNo(String deviceSerialNo) {
		this.deviceSerialNo = deviceSerialNo;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}

}
