package com.dangdang.digital.vo;

import java.io.Serializable;

public class ReturnDDHotFreeVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5470044359408345507L;
	
	private Long mediaId;

	private Long saleId;
	
	private String imagUrl;
	
	private String mediaName;
	
	private String authorName;
	
	private String description;
	

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public String getImagUrl() {
		return imagUrl;
	}

	public void setImagUrl(String imagUrl) {
		this.imagUrl = imagUrl;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
}
