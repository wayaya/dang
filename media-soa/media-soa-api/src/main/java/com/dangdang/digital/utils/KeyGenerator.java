package com.dangdang.digital.utils;

import java.util.UUID;

/**
 * UUID生成器
 * @author wangzhiwei
 */
public class KeyGenerator {
	
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String key = uuid.toString();
		key = key.replaceAll("-", "");
		return key;
	}
}
