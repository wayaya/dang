package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 书单基本信息
 * @author weisong
 * @date   2015年5月15日 下午3:55:25
 */
public class ReturnBookListVo implements Serializable{
	
	private static final long serialVersionUID = -7519614165946415692L;

	private Long booklistId;

    private Long channelId;

    private String name;

    private String imageUrl;

    private String description;

    private Integer bookNumber;

	public Long getBooklistId() {
		return booklistId;
	}

	public void setBooklistId(Long booklistId) {
		this.booklistId = booklistId;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(Integer bookNumber) {
		this.bookNumber = bookNumber;
	}
	
}
