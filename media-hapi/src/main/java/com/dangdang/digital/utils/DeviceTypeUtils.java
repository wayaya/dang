package com.dangdang.digital.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DeviceTypeUtils {
//	private static String CACHE_KEY = "mobile_key_devicetype_app_map";
	private static String DEVICETYPE_APPID_STR = ConfigPropertieUtils.getString("devicetype.appid.key");
//	private static String DEVICETYPE_APPID_CHANGED = ConfigReader.get("devicetype.appid.key.changed");
	private static final Logger log = LoggerFactory.getLogger(DeviceTypeUtils.class);
	private static Map<String, Integer> deviceMap;
	
	private DeviceTypeUtils() {
	}
		
	public static Integer getAppIdByDevicetype(String deviceType) {
		try {
			Map<String, Integer> deviceMap = getDeviceMap();
			if (deviceMap.containsKey(deviceType.toLowerCase())) {
				return deviceMap.get(deviceType.toLowerCase());
			} 
		} catch (Exception ex) {
			log.error("获取设备appid出现问题", ex);
		}
		return null;
	}
	
	private static Map<String, Integer> getDeviceMap() {
//		if (StringUtils.isNotEmpty(DEVICETYPE_APPID_CHANGED) && "true".equals(DEVICETYPE_APPID_CHANGED)) {
//			MemcachedManager.delete(CACHE_KEY);
//		}
//		Map<String, Integer> deviceMap = MemcachedManager.get(CACHE_KEY);
		if (deviceMap == null) {
			deviceMap = new HashMap<String, Integer>();
			if (StringUtils.isNotEmpty(DEVICETYPE_APPID_STR)) {
				String[] strs = DEVICETYPE_APPID_STR.split("/");
				for (int i=0;i<strs.length;i++) {
					if (StringUtils.isNotBlank(strs[i])) {
						int num = strs[i].indexOf("|");
						if (num > 0) {
							deviceMap.put(strs[i].substring(0, num), Integer.valueOf(strs[i].substring(num + 1)));
						}
					}
				}
//				if (deviceMap.size() > 0) {
//					MemcachedManager.set(CACHE_KEY, deviceMap);
//				}
			}
		}
		return deviceMap;
	}
	
}
