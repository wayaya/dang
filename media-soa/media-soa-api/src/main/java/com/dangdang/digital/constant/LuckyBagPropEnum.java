/**
 * Description: LuckyBagPropEnum.java
 * All Rights Reserved.
 * @version 1.0  2014年12月17日 下午4:28:24  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.constant;

/**
 * Description: 福袋道具功能id枚举类
 * All Rights Reserved.
 * @version 1.0  2014年12月17日 下午4:28:24  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public enum LuckyBagPropEnum {
	PROP_PURPOSE_ID_LUCKY_BAG_ONE(1001,"一个福袋"),
	PROP_PURPOSE_ID_LUCKY_BAG_THREE(1003,"三个福袋"),
	PROP_PURPOSE_ID_LUCKY_BAG_FIVE(1005,"五个福袋");
	private int propPurposeId;
	private String propPurposeName;
	private LuckyBagPropEnum(int propPurposeId,String propPurposeName){
		this.propPurposeId = propPurposeId;
		this.propPurposeName = propPurposeName;
	}
	public int getPropPurposeId() {
		return propPurposeId;
	}
	public String getPropPurposeName() {
		return propPurposeName;
	}
	
}
