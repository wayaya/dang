/**
 * Description: ActivityTypeEnum.java
 * All Rights Reserved.
 * @version 1.0  2014年12月5日 下午6:41:34  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.constant;

/**
 * Description: 活动类型枚举类
 * All Rights Reserved.
 * @version 1.0  2014年12月5日 下午6:41:34  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public enum ActivityTypeEnum {
	THE_FIRST_TIME_SEND(1000,"首次充值送"),
	THE_FIRST_N_SEND(1001,"前N个充值送"),
	CONSUME_SEND(1002,"消费送"),
	UPGRADE_SEND(1003,"升级送"),
	WORSHIP_SEND(1004,"膜拜送"),
	MONTHLY(1005,"包月"),
	A_PRICE(1006,"一口价"),
	ALL_PURCHASE_DISCOUNT(1007,"全本购买优惠"),
	BUY_MORE_DISCOUNT_CHAPTER(1008,"多买多折（章节）"),
	LUCKY_BAG(1009,"福袋"),
	ALIPAY_RECHARGE(1010,"支付宝充值"),
	TENPAY_RECHARGE(1011,"财付通充值"),
	WECHAT_RECHARGE(1012,"微信支付充值"),
	ALIPAY_RECHARGE_1(1018,"支付宝充值"),
	TENPAY_RECHARGE_1(1016,"财付通充值"),
	WECHAT_RECHARGE_1(1017,"微信支付充值"),
	TIME_FREE(1013,"限免"),
	IOS_RECHARGE(1014,"IOS充值"),
	IPAD_RECHARGE(1015,"IPAD充值"),
	SMS_RECHARGE(1019,"短信支付充值");
	
	private int key;
	private String name;

	
	private ActivityTypeEnum(int key,String name){
		this.key = key;
		this.name = name;
	}

	public int getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public static ActivityTypeEnum getByKey(int key){
		for(ActivityTypeEnum activityType : ActivityTypeEnum.values()){
			if(activityType.getKey() == key){
				return activityType;
			}
		}
		return null;
	}
}
