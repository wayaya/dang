package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * Description: 用户的个人信息返回 vo All Rights Reserved.
 * 
 * @version 1.0 2014年12月24日 上午10:32:18 by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class ReturnUserVo implements Serializable {
	private static final long serialVersionUID = -412426463922463443L;
	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 用户头像
	 */
	private String userImgUrl;

	/**
	 * 主账户余额
	 */
	private Integer mainBalance;
	/**
	 * 副账户余额
	 */
	private Integer subBalance;
	
	/**
     * 可用抽奖次数
     */
    private Integer lotTimes;
	/**
	 * IOS主账户余额
	 */
	private Integer mainBalanceIOS;
	/**
	 * IOS副账户余额
	 */
	private Integer subBalanceIOS;		

	/**
	 * 包月截止时间
	 */
	private Long monthlyEndTime;

	/**
	 * 是否首次登陆送包月 1：是
	 */
	private String firstLoginGiving;

	/**
	 * 首次登陆送包月天数
	 */
    private Integer firstLoginGivingDays;
    /**
     * 首次分享送包月天数
     */
    private Integer firstShareGivingDays;
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getMainBalance() {
		return mainBalance;
	}

	public void setMainBalance(Integer mainBalance) {
		this.mainBalance = mainBalance;
	}

	public Integer getSubBalance() {
		return subBalance;
	}

	public void setSubBalance(Integer subBalance) {
		this.subBalance = subBalance;
	}

	public Long getMonthlyEndTime() {
		return monthlyEndTime;
	}

	public Integer getFirstLoginGivingDays() {
		return firstLoginGivingDays;
	}

	public void setFirstLoginGivingDays(Integer firstLoginGivingDays) {
		this.firstLoginGivingDays = firstLoginGivingDays;
	}

	public Integer getFirstShareGivingDays() {
		return firstShareGivingDays;
	}

	public void setFirstShareGivingDays(Integer firstShareGivingDays) {
		this.firstShareGivingDays = firstShareGivingDays;
	}

	public void setMonthlyEndTime(Long monthlyEndTime) {
		this.monthlyEndTime = monthlyEndTime;
	}

	public String getUserImgUrl() {
		return userImgUrl;
	}

	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}

	public String getFirstLoginGiving() {
		return firstLoginGiving;
	}

	public void setFirstLoginGiving(String firstLoginGiving) {
		this.firstLoginGiving = firstLoginGiving;
	}

	public Integer getLotTimes() {
		return lotTimes;
	}

	public void setLotTimes(Integer lotTimes) {
		this.lotTimes = lotTimes;
	}

	public Integer getMainBalanceIOS() {
		return mainBalanceIOS;
	}

	public void setMainBalanceIOS(Integer mainBalanceIOS) {
		this.mainBalanceIOS = mainBalanceIOS;
	}

	public Integer getSubBalanceIOS() {
		return subBalanceIOS;
	}

	public void setSubBalanceIOS(Integer subBalanceIOS) {
		this.subBalanceIOS = subBalanceIOS;
	}

}
