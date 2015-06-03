package com.dangdang.digital.vo;

import java.io.Serializable;


public class ReturnNoticeVo {
	public static class ReturnParameter implements Serializable{
		private String name;
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		//所有id转成字符串
		private String id;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getDimension() {
			return dimension;
		}
		public void setDimension(String dimension) {
			this.dimension = dimension;
		}
		private String code;
		private String dimension;
	}
	//date formats
	
	//列信息
	private  Long noticeId;
	private String title;
	private long createTime;
	private long startTime;
	private long endTime;
	private String url;
	private Integer type;
	
	private String channelType;
	
	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
	private  ReturnParameter parameter;
	
	
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
	
	public void setCreateTime(long value) {
		this.createTime = value;
	}
	
	public long getCreateTime() {
		return this.createTime;
	}
	public void setStartTime(long value) {
		this.startTime = value;
	}
	
	public long getStartTime() {
		return this.startTime;
	}
	public void setEndTime(long value) {
		this.endTime = value;
	}
	
	public long getEndTime() {
		return this.endTime;
	}
	public void setUrl(String value) {
		this.url = value;
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
	
	public ReturnParameter getParameter() {
		return this.parameter;
	}
	public void setParameter(ReturnParameter parameter) {
		this.parameter = parameter;
	}
}
