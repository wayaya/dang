package com.dangdang.digital.model;

/**
 * 
 * Description: 专题类型树
 * All Rights Reserved.
 * @version 1.0  2015年3月4日 下午6:03:54  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public class SpecialTopicCategory{
	
	//date formats
	
	//列信息
	private Long stTypeId;
	private String categoryName;
	private String channel;
	private Long parentId;
	private String categoryCode;
	private String path;
	private String creator;
	private String modifier;
	private String creationDate;
	private String lastModifiedDate;
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public void setStTypeId(Long value) {
		this.stTypeId = value;
	}
	
	public Long getStTypeId() {
		return this.stTypeId;
	}
	public void setCategoryName(String value) {
		this.categoryName = value;
	}
	
	public String getCategoryName() {
		return this.categoryName;
	}
	public void setParentId(Long value) {
		this.parentId = value;
	}
	
	public Long getParentId() {
		return this.parentId;
	}
	public void setCategoryCode(String value) {
		this.categoryCode = value;
	}
	
	public String getCategoryCode() {
		return this.categoryCode;
	}
	public void setPath(String value) {
		this.path = value;
	}
	
	public String getPath() {
		return this.path;
	}
	public void setCreator(String value) {
		this.creator = value;
	}
	
	public String getCreator() {
		return this.creator;
	}
	public void setModifier(String value) {
		this.modifier = value;
	}
	
	public String getModifier() {
		return this.modifier;
	}
	public void setCreationDate(String value) {
		this.creationDate = value;
	}
	
	public String getCreationDate() {
		return this.creationDate;
	}
	public void setLastModifiedDate(String value) {
		this.lastModifiedDate = value;
	}
	
	public String getLastModifiedDate() {
		return this.lastModifiedDate;
	}
}

