package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * 
 * Description: 与前端交互的购买信息 All Rights Reserved.
 * 
 * @version 1.0 2014年12月29日 下午1:28:16 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ReturnBuyInfo implements Serializable {

	private static final long serialVersionUID = -5500111917549133577L;
	/**
	 * 章节id
	 */
	private Long chapterId;
	/**
	 * 书籍id
	 */
	private Long mediaId;
	/**
	 * 章节名称
	 */
	private String chapterTitle;
	/**
	 * 章节价格
	 */
	private Integer chapterPrice;
	/**
	 * 字节数
	 */
	private Long wordCnt;
	/**
	 * 用户id
	 */
	private String custId;
	/**
	 * 主账户余额
	 */
	private Integer mainBalance;
	/**
	 * 副账户余额
	 */
	private Integer subBalance;

	/**
	 * 销售实体名称
	 */
	private String saleName;
	/**
	 * 销售实体id
	 */
	private Long saleId;
	/**
	 * 销售实体价格
	 */
	private Long salePrice;
	/**
	 * 是否支持全本购买 0:否 1:是
	 */
	private Integer isSupportFullBuy;
	/**
	 * 总字数
	 */
	private Long mediaWordCnt;
	
	/**
	 * 主账户余额IOS
	 */
	private Integer mainBalanceIOS;
	/**
	 * 副账户余额IOS
	 */
	private Integer subBalanceIOS;

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public String getChapterTitle() {
		return chapterTitle;
	}

	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}

	public Integer getChapterPrice() {
		return chapterPrice;
	}

	public void setChapterPrice(Integer chapterPrice) {
		this.chapterPrice = chapterPrice;
	}

	public Long getWordCnt() {
		return wordCnt;
	}

	public void setWordCnt(Long wordCnt) {
		this.wordCnt = wordCnt;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Integer getMainBalance() {
		return mainBalance;
	}

	public void setMainBalance(Integer mainBalance) {
		this.mainBalance = mainBalance;
	}

	public Integer getSubBalance() {
		return subBalance;
	}

	public void setSubBalance(Integer subBalance) {
		this.subBalance = subBalance;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public Long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getIsSupportFullBuy() {
		return isSupportFullBuy;
	}

	public void setIsSupportFullBuy(Integer isSupportFullBuy) {
		this.isSupportFullBuy = isSupportFullBuy;
	}

	public Long getMediaWordCnt() {
		return mediaWordCnt;
	}

	public void setMediaWordCnt(Long mediaWordCnt) {
		this.mediaWordCnt = mediaWordCnt;
	}

	public Integer getMainBalanceIOS() {
		return mainBalanceIOS;
	}

	public void setMainBalanceIOS(Integer mainBalanceIOS) {
		this.mainBalanceIOS = mainBalanceIOS;
	}

	public Integer getSubBalanceIOS() {
		return subBalanceIOS;
	}

	public void setSubBalanceIOS(Integer subBalanceIOS) {
		this.subBalanceIOS = subBalanceIOS;
	}

}
