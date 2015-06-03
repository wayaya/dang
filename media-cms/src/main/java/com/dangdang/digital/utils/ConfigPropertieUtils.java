package com.dangdang.digital.utils;
import java.util.Properties;
  
/**  
* 读取Properties常用方法
* User: wang.zw  
* Date: 2014-11-11 18:38:40  
*/   
public class ConfigPropertieUtils {   
	public static Properties prop = new Properties();
    
    public static String getString(final String key) {
		return prop.getProperty(key);
	}

	public static String getString(final String key, final String defaultValue) {
		return prop.getProperty(key, defaultValue);
	}
	
	public static Integer getInteger(final String key, final Integer defaultValue) {
		Integer returnValue;
		
		String value = prop.getProperty(key);
		
		if(value != null && !value.trim().equals("")){
			try {
				returnValue = Integer.parseInt(value);
			} catch (Exception e) {
				returnValue = defaultValue;
			}
		}else{
			returnValue = defaultValue;
		}
		
		return returnValue;
	}
} 
