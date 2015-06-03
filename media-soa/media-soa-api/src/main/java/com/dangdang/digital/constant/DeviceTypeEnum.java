package com.dangdang.digital.constant;

/**
 * Description: 设备类型枚举类
 * All Rights Reserved.
 * @version 1.0  2015年1月10日 下午1:40:41  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public enum DeviceTypeEnum {
	YC_ANDROID("YC_Android","原创android"),
	YC_IPHONE("YC_Iphone","原创iphone");
	
	private String deviceTypeNo;
	private String deviceTypeName;

	private DeviceTypeEnum(String deviceTypeNo,String deviceTypeName){
		this.deviceTypeNo = deviceTypeNo;
		this.deviceTypeName = deviceTypeName;
	}

	public String getDeviceTypeNo() {
		return deviceTypeNo;
	}

	public void setDeviceTypeNo(String deviceTypeNo) {
		this.deviceTypeNo = deviceTypeNo;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public static DeviceTypeEnum getDeviceType(String deviceTypeNo){
		for(DeviceTypeEnum deviceType : DeviceTypeEnum.values()){
			if(deviceType.getDeviceTypeNo().equals(deviceTypeNo)){
				return deviceType;
			}
		}
		return null;
	}
}