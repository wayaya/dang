package com.dangdang.digital.model;


/**
 * ChannelBooklist Entity.
 */
public class ChannelBooklist{
	
	//date formats
	
	//列信息
	private Long id;
	
	private Long channelId;
	
	private Long bookListId;
	
	private Integer isShow;
	
	private Integer status;
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setChannelId(Long value) {
		this.channelId = value;
	}
	
	public Long getChannelId() {
		return this.channelId;
	}
		
		
	public void setBookListId(Long value) {
		this.bookListId = value;
	}
	
	public Long getBookListId() {
		return this.bookListId;
	}
		
		
	public void setIsShow(Integer value) {
		this.isShow = value;
	}
	
	public Integer getIsShow() {
		return this.isShow;
	}
		
		
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
		
}

