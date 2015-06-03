package com.dangdang.digital.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Description: 订单来源平台枚举类
 * All Rights Reserved.
 * @version 1.0  2014年12月2日 上午10:00:57  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public enum OrderFromPlatformEnum {
	
	WIRELESS_SHOPPING_WAP("1","android","安卓","A"),
	PC_SIMPLE_PAY("2","IOS","IOS","I"),
	PC_COMMON_WEB("3","PC","网站","P"),
	OTHER("0","OT","其他","O");
	
	private String key;
	private String platform;
	private String platformName;
	private String prefix;
	
	private OrderFromPlatformEnum(String key,String platform,String platformName,String prefix){
		this.key = key;
		this.platform = platform;
		this.platformName = platformName;
		this.prefix = prefix;
	}

	public String getKey() {
		return key;
	}

	public String getPlatform() {
		return platform;
	}

	public String getPlatformName() {
		return platformName;
	}

	public String getPrefix() {
		return prefix;
	}

	public static OrderFromPlatformEnum getValueByKey(String key){
		for(OrderFromPlatformEnum platform : OrderFromPlatformEnum.values()){
			if(platform.getKey().equals(key)){
				return platform;
			}
		}
		return null;
	}
	
	public static Map<String,OrderFromPlatformEnum> getPlatformMap(){
		Map<String,OrderFromPlatformEnum> platformMap = new HashMap<String,OrderFromPlatformEnum>();
		for(OrderFromPlatformEnum platform : OrderFromPlatformEnum.values()){
			if(!platformMap.containsKey(platform.getKey())){
				platformMap.put(platform.getKey(), platform);
			}
		}
		return platformMap;
	}
	
}
