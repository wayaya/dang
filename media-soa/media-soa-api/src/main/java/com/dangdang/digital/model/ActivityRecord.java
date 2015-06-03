package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 活动参与记录 主题
 * All Rights Reserved.
 * @version 1.0  2014年12月5日 下午2:24:44  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class ActivityRecord implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9113201809681455740L;

	private Long mediaActivityRecordId;

    /**
     *  用户id
     */
    private Long custId;

    /**
     * 用户显示名字
     */
    private String username;

    /**
     * 奖品id
     */
    private Long prizeId;

    /**
     * 活动类型
     */
    private Integer activityType;

    /**
     * 奖品类型
     */
    private Integer prizeType;
    /**
     * 奖品数量
     */
    private Integer amount;
    /**
     * 归属类型，比如福袋
     */
    private Integer prizeVestType;

    /**
     * 奖品名称
     */
    private String prizeName;

    /**
     * 被膜拜的用户id
     */
    private Long worshipedCustId;

    /**
     * 被膜拜的显示名字
     */
    private String worshipedUsername;

    /**
     * 创建时间
     */
    private Date creationDate;

    public ActivityRecord(){}
    //分享记录的构造方法
    public ActivityRecord(Integer activityType,Long custId,String username,Date creationDate){
    	this.activityType = activityType;
    	this.custId = custId;
    	this.username = username;
    	this.creationDate = creationDate;
    }
    //膜拜的构造方法
    public ActivityRecord(Integer activityType,Long custId,Long worshipedCustId, String username,String worshipedUsername,Date creationDate){
    	this.activityType = activityType;
    	this.custId = custId;
    	this.worshipedCustId = worshipedCustId;
    	this.username = username;
    	this.worshipedUsername = worshipedUsername;
    	this.creationDate = creationDate;
    }
    //奖品的构造方法
    public ActivityRecord(Integer activityType,Integer prizeType,Long custId,String username,Long prizeId,String prizeName,Integer amount,Integer prizeVestType,Date creationDate){
    	this.activityType = activityType;
    	this.prizeType = prizeType;
    	this.custId = custId;
    	this.username = username;
    	this.prizeId = prizeId;
    	this.prizeName = prizeName;
    	this.amount = amount;
    	this.prizeVestType = prizeVestType;
    	this.creationDate = creationDate;
    }
    
    //红包构造方法
    public ActivityRecord(Integer activityType,Long custId,String username,String prizeName,Integer amount,Date creationDate){
    	this.activityType = activityType;
    	this.custId = custId;
    	this.username = username;
    	this.prizeName = prizeName;
    	this.amount = amount;
    	this.creationDate = creationDate;
    }
    
    public Long getMediaActivityRecordId() {
        return mediaActivityRecordId;
    }

    public void setMediaActivityRecordId(Long mediaActivityRecordId) {
        this.mediaActivityRecordId = mediaActivityRecordId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Long getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Long prizeId) {
        this.prizeId = prizeId;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Integer getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(Integer prizeType) {
        this.prizeType = prizeType;
    }

    public Integer getPrizeVestType() {
        return prizeVestType;
    }

    public void setPrizeVestType(Integer prizeVestType) {
        this.prizeVestType = prizeVestType;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName == null ? null : prizeName.trim();
    }

    public Long getWorshipedCustId() {
        return worshipedCustId;
    }

    public void setWorshipedCustId(Long worshipedCustId) {
        this.worshipedCustId = worshipedCustId;
    }

    public String getWorshipedUsername() {
        return worshipedUsername;
    }

    public void setWorshipedUsername(String worshipedUsername) {
        this.worshipedUsername = worshipedUsername == null ? null : worshipedUsername.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "ActivityRecord [mediaActivityRecordId=" + mediaActivityRecordId
				+ ", custId=" + custId + ", username=" + username
				+ ", prizeId=" + prizeId + ", activityType=" + activityType
				+ ", prizeType=" + prizeType + ", amount=" + amount
				+ ", prizeVestType=" + prizeVestType + ", prizeName="
				+ prizeName + ", worshipedCustId=" + worshipedCustId
				+ ", worshipedUsername=" + worshipedUsername
				+ ", creationDate=" + creationDate + "]";
	}
    
	
}