package com.dangdang.digital.constant;

/**
 * 
 * Description: 充值银铃铛的活动码
 * All Rights Reserved.
 * @version 1.0  2015年4月25日 下午12:12:31  by 魏嵩（weisong@dangdang.com）创建
 */
public enum RechargeActivityCodeEnum {
	LUCKY_BAG("fudai_1001","当读小说福袋抽奖银铃铛"),
	SPREAD_COINS("diaoqiandai_1001","当读小说撒金币"),
	WORSHIP("mobai_1001","当读小说膜拜"),
	RED_BAG("hongbao_1001","当读小说抢红包"),
	SINGIN("signin_1002","当当读书签到红包"),
	LOTTERY("lottery_1002","当当读书摇一摇"),
	CHONGJIN_SONGYIN("chongjinsongyin_1002","当当读书充金送银"),
	BUDAN("budan","补单");
	
	private String code;
	private String name;
	
	private RechargeActivityCodeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static RechargeActivityCodeEnum getByCode(String code){
		for(RechargeActivityCodeEnum activityCode : RechargeActivityCodeEnum.values()){
			if(activityCode.getCode().equals(code)){
				return activityCode;
			}
		}
		return null;
	}
}
