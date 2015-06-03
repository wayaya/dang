package com.dangdang.digital.vo;

/**
 * Description: 活动VO
 * All Rights Reserved.
 * @version 1.0  2014年12月2日 下午2:29:25  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class ActivityVo {
	

	/**
	 * custid
	 */
	private long custId;
	
	/**
	 * 用户名字
	 */
	private String username;
	
	/**
	 * 昵称
	 */
	private String nickname;
	
	/**
	 * 设备类型
	 */
	private String deviceType;
	
	/**
	 * 活动类型
	 */
	private Integer activityType;
	
	/**
	 * 增加的抽奖次数
	 */
	private Integer lotTimes;
	
	/**
	 * 增加的已抽次数
	 */
	private Integer lottedTimes;
	
	/**
	 * 需要增加的辅账户的钱数
	 */
	private Integer cons;
	
	/**
	 * 被膜拜的用户名
	 */
	private String worshipedUsername;
	
	/**
	 * 被膜拜的人的custid
	 */
	private long worshipedCustId;

	
	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}


	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	public String getUsername() {
		return username;
	}

	public String getWorshipedUsername() {
		return worshipedUsername;
	}

	public void setWorshipedUsername(String worshipedUsername) {
		this.worshipedUsername = worshipedUsername;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getWorshipedCustId() {
		return worshipedCustId;
	}

	public void setWorshipedCustId(long worshipedCustId) {
		this.worshipedCustId = worshipedCustId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Integer getLotTimes() {
		return lotTimes;
	}

	public void setLotTimes(Integer lotTimes) {
		this.lotTimes = lotTimes;
	}

	public Integer getLottedTimes() {
		return lottedTimes;
	}

	public void setLottedTimes(Integer lottedTimes) {
		this.lottedTimes = lottedTimes;
	}

	public Integer getCons() {
		return cons;
	}

	public void setCons(Integer cons) {
		this.cons = cons;
	}

	
}
