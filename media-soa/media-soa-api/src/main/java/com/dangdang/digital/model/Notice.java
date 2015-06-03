package com.dangdang.digital.model;

import java.io.Serializable;


/**
 * Notice Entity.
 */
public class Notice implements Serializable{
	private static final long serialVersionUID = -6402370599660267314L;
	public static class Parameter{
		private Long id;
		private String code;
		private String dimension;
		

		private String name;
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code == null? null:code.trim();
		}

		public String getDimension() {
			return dimension;
		}

		public void setDimension(String dimension) {
			this.dimension = dimension == null? null:dimension.trim();
		}
	}
	//列信息
	private Long noticeId;
	private String title;
	private String creator;
	private String createTime;
	private String startTime;
	private String endTime;
	private String url;
	private Integer type;
	private String parameter;
	private String lastChangeTime;
	private String modifer;
	private String channelType;
	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	
	
	public void setNoticeId(Long value) {
		this.noticeId = value;
	}
	
	public Long getNoticeId() {
		return this.noticeId;
	}
	public void setTitle(String value) {
		this.title = value;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setCreator(String value) {
		this.creator = value;
	}
	
	public String getCreator() {
		return this.creator;
	}
	public void setCreateTime(String value) {
		this.createTime = value == null? null:value.trim();
	}
	
	public String getCreateTime() {
		return this.createTime;
	}
	public void setStartTime(String value) {
		this.startTime =   value == null? null:value.trim();
	}
	
	public String getStartTime() {
		return this.startTime;
	}
	public void setEndTime(String value) {
		this.endTime = value;
	}
	
	public String getEndTime() {
		return this.endTime;
	}
	public void setUrl(String value) {
		this.url = value  == null? null:value.trim();;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setParameter(String value) {
		this.parameter = value;
	}
	
	public String getParameter() {
		return this.parameter;
	}
	public void setLastChangeTime(String value) {
		this.lastChangeTime = value;
	}
	
	public String getLastChangeTime() {
		return this.lastChangeTime;
	}
	public void setModifer(String value) {
		this.modifer = value;
	}
	
	public String getModifer() {
		return this.modifer;
	}
}

