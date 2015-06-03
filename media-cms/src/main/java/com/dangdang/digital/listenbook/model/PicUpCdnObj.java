package com.dangdang.digital.listenbook.model;

import java.io.Serializable;

public class PicUpCdnObj implements Serializable {
	private static final long serialVersionUID = 9194201827681455740L;

	private long mediaId;
	private String imgContent;
	private int retryNum;
	
	public long getMediaId() {
		return mediaId;
	}
	public void setMediaId(long mediaId) {
		this.mediaId = mediaId;
	}
	public String getImgContent() {
		return imgContent;
	}
	public void setImgContent(String imgContent) {
		this.imgContent = imgContent;
	}
	public int getRetryNum() {
		return retryNum;
	}
	public void setRetryNum(int retryNum) {
		this.retryNum = retryNum;
	}
	
}
