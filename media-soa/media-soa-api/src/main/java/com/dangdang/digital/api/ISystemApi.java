package com.dangdang.digital.api;
/**
 * 
 * Description: 根据参数代码查询得到对应的值
 * All Rights Reserved.
 * @version 1.0  2014年12月3日 下午3:26:10  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public interface ISystemApi {
	
	String getProperty(String key, String defaultValue);

	String getProperty(String key);

}
