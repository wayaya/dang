package com.dangdang.digital.model;
/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年4月2日 上午10:29:35  by yangzhenping（yangzhenping@dangdang.com）创建
 */
public class RewardTop {
	/**
	 * 书名
	 */
	private String mediaName;
	/**
	 * 总额
	 */
	private Long totalCons;
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public Long getTotalCons() {
		return totalCons;
	}
	public void setTotalCons(Long totalCons) {
		this.totalCons = totalCons;
	}
}
