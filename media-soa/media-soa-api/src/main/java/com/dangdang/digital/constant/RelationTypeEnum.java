package com.dangdang.digital.constant;

/**
 * 
 * Description: 权限类型的枚举
 * All Rights Reserved.
 * @version 1.0  2014-6-6 上午9:58:48  by 王星皓（wangxinghao@dangdang.com）创建
 */
public enum RelationTypeEnum {
	/**
	 * 枚举元素
	 */
	RELATION_TYPE_BUY("1001","用户自主购买", 1), 
	RELATION_TYPE_GIVE("1002","纸电打通赠送", 2),
	RELATION_TYPE_BORROW("1003","免费借阅",3);

	// 成员变量
	private String value;
	private String desc;
	private int index;

	// 构造方法
	private RelationTypeEnum(String value, String desc, int index) {
		this.value = value;
		this.desc = desc;
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public static String getValue(int index) {
		for (RelationTypeEnum c : RelationTypeEnum.values()) {
			if (c.getIndex() == index) {
				return c.value;
			}
		}
		return null;
	}
}
