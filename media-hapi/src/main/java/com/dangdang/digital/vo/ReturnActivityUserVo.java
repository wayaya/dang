package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * Description: 活动用户返回的vo
 * All Rights Reserved.
 * @version 1.0  2014年12月24日 上午10:32:18  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class ReturnActivityUserVo implements Serializable {
	private static final long serialVersionUID = 5357310037648538314L;

	/**
	 * 用户头像地址
	 */
	private String userImgUrl;
	/**
	 * 昵称
	 */
	private String nickname;
	
	/**
     * 壕赏总钱数
     */
    private Long reward;

    /**
     * 可用抽奖次数
     */
    private Integer lotTimes;

  
	/**
     * 被膜拜次数
     */
    private Long worshipedTimes;

    /**
     * 日最高排名
     */
    private Integer dayTopRank;

    /**
     * 星期最高排名
     */
    private Integer weekTopRank;

    /**
     * 月最高排名
     */
    private Integer mouthTopRank;

    /**
     * 总最高排名
     */
    private Integer totalTopRank;

	public String getUserImgUrl() {
		return userImgUrl;
	}

	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}

	public Integer getLotTimes() {
		return lotTimes;
	}

	public void setLotTimes(Integer lotTimes) {
		this.lotTimes = lotTimes;
	}

	public Long getWorshipedTimes() {
		return worshipedTimes;
	}

	public void setWorshipedTimes(Long worshipedTimes) {
		this.worshipedTimes = worshipedTimes;
	}

	public Integer getDayTopRank() {
		return dayTopRank;
	}

	public void setDayTopRank(Integer dayTopRank) {
		this.dayTopRank = dayTopRank;
	}

	public Integer getWeekTopRank() {
		return weekTopRank;
	}

	public void setWeekTopRank(Integer weekTopRank) {
		this.weekTopRank = weekTopRank;
	}

	public Integer getMouthTopRank() {
		return mouthTopRank;
	}

	public void setMouthTopRank(Integer mouthTopRank) {
		this.mouthTopRank = mouthTopRank;
	}
	
	public Long getReward() {
		return reward;
	}

	public void setReward(Long reward) {
		this.reward = reward;
	}

	public Integer getTotalTopRank() {
		return totalTopRank;
	}

	public void setTotalTopRank(Integer totalTopRank) {
		this.totalTopRank = totalTopRank;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
    
}
