/**
 * Description: DigestMoodEnum.java
 * All Rights Reserved.
 * @version 1.0  2015年1月28日 下午3:41:26  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.constant;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月28日 下午3:41:26  by 代鹏（daipeng@dangdang.com）创建
 */
public enum DigestMoodEnum {
	
	EXCITING(1, "振奋"),
	LUCIDITY(2, "明朗"),
	HAPPY(3, "开心"),
	RELAX(4, "放松"),
	SURPRISED(5, "惊喜"),
	ABUSE(6, "虐心");
	
	private int key;
	
	private String name;

	private DigestMoodEnum(int key, String name) {
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
	
	public static DigestMoodEnum getDigestMoodEnumByKey(int key){
		for(DigestMoodEnum item:DigestMoodEnum.values()){
			if(item.getKey() == key){
				return item;
			}
		}
		return null;
	}

}
