package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * Description: 与前台交互的榜单主体vo 
 * All Rights Reserved.
 * @version 1.0  2014年12月19日 下午5:29:12  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class ReturnRankConsVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2989739684830468407L;
	/**
	 * 销售主体主键id
	 */
	private Long saleId;
	/**
	 * custId 加密后的
	 */
	private String custId;


	/**
	 * 书名
	 */
	private String mediaName;

	/**
	 * 书的id
	 */
	private Long mediaId;
	
	/**
	 * 封面图
	 */
	private String coverPic;
	/**
	 * 用户头像地址
	 */
	private String userImgUrl;
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 显示的消费的金币
	 */
	private Long showCons;

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public String getUserImgUrl() {
		return userImgUrl;
	}

	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Long getShowCons() {
		return showCons;
	}

	public void setShowCons(Long showCons) {
		this.showCons = showCons;
	}
}
