package com.dangdang.digital.mock.model;

import java.io.Serializable;

public class Channel implements Serializable{
	/** 频道标识**/
	private Long channelId;

	/** 频道标题　**/
	private String title;
	
	/** 频道图标 **/
	private String icon;
	
	/** 频道描述 **/
	private String description;
	
	/**订阅数 **/
	private Integer subNumber;
	
	/** 关联书单**/
	private Long bookListId;
	
	
	/** 频道所有者(运营者为公司类型时)*/
	private String ownder;
	
	/**
	 * 是 否有文集1:表示有,0表示没有
	 */
	private  Integer hasArtical;
	
	
	public Integer getHasArtical() {
		return hasArtical;
	}

	public void setHasArtical(Integer hasArtical) {
		this.hasArtical = hasArtical;
	}

	public String getOwnder() {
		return ownder;
	}

	public void setOwnder(String ownder) {
		this.ownder = ownder;
	}

	public Long getBookListId() {
		return bookListId;
	}

	public void setBookListId(Long bookListId) {
		this.bookListId = bookListId;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSubNumber() {
		return subNumber;
	}

	public void setSubNumber(Integer subNumber) {
		this.subNumber = subNumber;
	}


	
}