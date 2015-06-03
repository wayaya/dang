/**
 * Description: DigestEnum.java
 * All Rights Reserved.
 * @version 1.0  2015年1月26日 下午4:29:56  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 精品白天黑夜枚举
 * All Rights Reserved.
 * @version 1.0  2015年1月26日 下午4:29:56  by 代鹏（daipeng@dangdang.com）创建
 */
public enum DigestDayOrNightEnum {
	
	DAY(0,"day"),
	NIGHT(1, "night");
	
	private int key;
	
	private String name;
	
	private DigestDayOrNightEnum(int key, String name) {
		this.key = key;
		this.name = name;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static DigestDayOrNightEnum getByKey(int key){
		for(DigestDayOrNightEnum dg:DigestDayOrNightEnum.values()){
			if(dg.getKey() == key){
				return dg;
			}
		}
		return null;
	}
	
	public static DigestDayOrNightEnum getByName(String name){
		for(DigestDayOrNightEnum dg:DigestDayOrNightEnum.values()){
			if(dg.getName().equals(name)){
				return dg;
			}
		}
		return null;
	}
	
	public static List<String> getDayOrNightNames(){
		List<String> list = new ArrayList<String>();
		for(DigestDayOrNightEnum dg:DigestDayOrNightEnum.values()){
			list.add(dg.getName());
		}
		return list;
	}
	

}
