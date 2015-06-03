package com.dangdang.digital.model;

import java.io.Serializable;


/**
 * NoticeType Entity.
 */
public class NoticeType implements Serializable{
	
	private static final long serialVersionUID = 8649954742201068481L;
	//列信息
	private Integer noticeTypeId;
	private String name;
	private Integer type;
	private String creator;
	private String createDate;
	private String lastChangeDate;
	private String modifer;

	public void setNoticeTypeId(Integer value) {
		this.noticeTypeId = value;
	}
	
	public Integer getNoticeTypeId() {
		return this.noticeTypeId;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setCreator(String value) {
		this.creator = value;
	}
	
	public String getCreator() {
		return this.creator;
	}
	public void setCreateDate(String value) {
		this.createDate = value;
	}
	
	public String getCreateDate() {
		return this.createDate;
	}
	public void setLastChangeDate(String value) {
		this.lastChangeDate = value;
	}
	
	public String getLastChangeDate() {
		return this.lastChangeDate;
	}
	public void setModifer(String value) {
		this.modifer = value;
	}
	
	public String getModifer() {
		return this.modifer;
	}
}

