/**
 * Description: ActivityTypeEnum.java
 * All Rights Reserved.
 * @version 1.0  2014年12月5日 下午6:41:34  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.constant;

/**
 * Description: 活动枚举类【类型以及类型下】
 * All Rights Reserved.
 * @version 1.0  2014年12月5日 下午6:41:34  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public enum ActivityEnum {
	//本来想起名ActivityTypeEnum了。。。。已经被使用了。
	//奖品类型:1,抽奖，2膜拜，3分享，4红包，5每日送福袋，6撒金币
	LOT_TYPE(1,"抽奖类型"),
	WORSHIP_TYPE(2,"膜拜类型"),
	SHARE_TYPE(3,"分享类型"),
	REDPACKET_TYPE(4,"红包类型"),
	DAYGIVELOT_TYPE(5,"每日送福袋类型"),
	SPREADCOINS_TYPE(6,"撒金币类型"),
	FIRSTLOGIN_TYPE(7,"首次登陆送包月类型"),
	FIRSTSHARE_TYPE(8,"首次分享送包月类型"),
	TIMESHARE_TYPE(9,"限时分享送包月类型");
	
	private int key;
	private String name;

	
	private ActivityEnum(int key,String name){
		this.key = key;
		this.name = name;
	}

	public int getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public static ActivityEnum getByKey(int key){
		for(ActivityEnum activityType : ActivityEnum.values()){
			if(activityType.getKey() == key){
				return activityType;
			}
		}
		return null;
	}
}
