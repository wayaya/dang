package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 活动用户表主体
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午3:37:51  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class ActivityUser implements Serializable{
	private static final long serialVersionUID = 579642315087346209L;

	private Long mediaActivityUserId;

    /**
     * 用户名【昵称】
     */
    private String username;

    /**
     * 用户id
     */
    private Long custId;

    /**
     * 可用抽奖次数
     */
    private Integer lotTimes;

    /**
     * 已用抽奖次数
     */
    private Integer lottedTimes;

    /**
     * 分享次数
     */
    private Integer shareTimes;

    /**
     * 膜拜次数
     */
    private Long worshipTimes;
  
	/**
     * 被膜拜次数
     */
    private Long worshipedTimes;
    
    /**
     * 壕赏的总钱数
     */
    private Long rewardCons;

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

    /**
     * 创建时间
     */
    private Date creationDate;

    private Date lastModifiedDate;

    public Long getMediaActivityUserId() {
        return mediaActivityUserId;
    }

    public void setMediaActivityUserId(Long mediaActivityUserId) {
        this.mediaActivityUserId = mediaActivityUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
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

    public Integer getShareTimes() {
        return shareTimes;
    }

    public void setShareTimes(Integer shareTimes) {
        this.shareTimes = shareTimes;
    }

    public Long getWorshipTimes() {
  		return worshipTimes;
  	}

  	public void setWorshipTimes(Long worshipTimes) {
  		this.worshipTimes = worshipTimes;
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

    public Long getRewardCons() {
		return rewardCons;
	}

	public void setRewardCons(Long rewardCons) {
		this.rewardCons = rewardCons;
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

    public Integer getTotalTopRank() {
        return totalTopRank;
    }

    public void setTotalTopRank(Integer totalTopRank) {
        this.totalTopRank = totalTopRank;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}