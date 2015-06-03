package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * Description: 与前台交互的活动记录vo 
 * All Rights Reserved.
 * @version 1.0  2014年12月19日 下午5:29:12  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class ReturnActivityVo implements Serializable {

	private static final long serialVersionUID = -5823459576162812918L;
	
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 活动类型 1，抽奖，2膜拜，3分享
	 */
	private Integer activityType;
	
	/**
	 * 奖品类型,1:金币，2：章节，3：道具，4：包月
	 */
	private Integer prizeType;
	/**
	 * 奖品名称
	 */
	private String prizeName;
	
	
	/**
	 * 奖品id
	 */
	private Long prizeId;
	
	/**
	 * 作品id
	 */
	private Long mediaId;
	
	/**
	 * 奖品数量
	 */
	private Integer amount;
	
	/**
	 * 参与时间
	 */
	private String creationDate;
	

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

	public String getPrizeName() {
		return prizeName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	} 
	
	
	
}
