package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;


/**
 * Channel Entity.
 */
public class Channel implements Serializable{
	
	//date formats
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5625662458344492516L;
	//频道未审核通过(默认)
	public static int channel_unaudit_pass__status =0;
	//频道审核未通过
	public static int channel_audit_fail__status =1;
	//频道审核通过
	public static int channel_audit_pass__status =2;
	//列信息
	private Long channelId;
	
	private Long ownerId;
	
	private Long custId;
	
	private String title;
	
	private String icon;
	
	private String description;
	
	private Long subNumber;
	
	private Integer status;
	
	private Date createDate;
	
	private Date modifyDate;
	
	private Integer isCompleted;
	
	private Integer modifyTimes;
	
	private Integer shelfStatus;
	
	/**
	 * 频道所有者信息
	 */
	private ChannelOwner channelOwner;
	
		
	public ChannelOwner getChannelOwner() {
		return channelOwner;
	}

	public void setChannelOwner(ChannelOwner channelOwner) {
		this.channelOwner = channelOwner;
	}

	public void setChannelId(Long value) {
		this.channelId = value;
	}
	
	public Long getChannelId() {
		return this.channelId;
	}
		
		
	public void setOwnerId(Long value) {
		this.ownerId = value;
	}
	
	public Long getOwnerId() {
		return this.ownerId;
	}
		
		
	public void setCustId(Long value) {
		this.custId = value;
	}
	
	public Long getCustId() {
		return this.custId;
	}
		
		
	public void setTitle(String value) {
		this.title = value;
	}
	
	public String getTitle() {
		return this.title;
	}
		
		
	public void setIcon(String value) {
		this.icon = value;
	}
	
	public String getIcon() {
		return this.icon;
	}
		
		
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return this.description;
	}
		
		
	public void setSubNumber(Long value) {
		this.subNumber = value;
	}
	
	public Long getSubNumber() {
		return this.subNumber;
	}
		
		
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
		
		
	public void setCreateDate(Date value) {
		this.createDate = value;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
		
		
	public void setModifyDate(Date value) {
		this.modifyDate = value;
	}
	
	public Date getModifyDate() {
		return this.modifyDate;
	}
		
		
	public void setIsCompleted(Integer value) {
		this.isCompleted = value;
	}
	
	public Integer getIsCompleted() {
		return this.isCompleted;
	}
		
		
	public void setModifyTimes(Integer value) {
		this.modifyTimes = value;
	}
	
	public Integer getModifyTimes() {
		return this.modifyTimes;
	}
		
		
	public void setShelfStatus(Integer value) {
		this.shelfStatus = value;
	}
	
	public Integer getShelfStatus() {
		return this.shelfStatus;
	}
		
}

