package com.dangdang.digital.constant;

/**
 * 
 * Description: IOS充值支付校验失败记录操作状态枚举
 * All Rights Reserved.
 * @version 1.0  2015年3月20日 下午2:54:31  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public enum IOSDepositFailStatusEnum {
	UNTREATED(0,"未处理"),
	PROCESSED(1,"已处理"),
	FAILURE(2,"已失效");
	
	private int key;
	private String name;

	
	private IOSDepositFailStatusEnum(int key,String name){
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
