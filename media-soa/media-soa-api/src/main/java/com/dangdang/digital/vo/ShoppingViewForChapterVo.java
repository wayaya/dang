/**
 * Description: ShoppingViewForChapterVo.java
 * All Rights Reserved.
 * @version 1.0  2014年12月4日 下午2:09:40  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * Description: 单章购买view
 * All Rights Reserved.
 * @version 1.0  2014年12月4日 下午2:09:40  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class ShoppingViewForChapterVo implements Serializable {

	/**
	   * @Fields serialVersionUID : TODO 2014年12月4日 下午2:10:00  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = 7304905690980093194L;
	/**
	 * 章节id
	 */
	private Long mediaChapterId;
	private Long mediaId;
	/**
	 * 章节名称
	 */
	private String mediaChapterTitle;
	/**
	 * 章节价格
	 */
	private Integer mediaChapterPrice;
	/**
	 * 字节数
	 */
	private Long wordCnt;
	/**
	 * 销售实体名称
	 */
	private String saleName;
	/**
	 * 销售实体id
	 */
	private Long saleId;	
	/**
	 *销售实体价格 
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
	 * 用户id
	 */
	private Long custId;
	/**
	 * 主账户余额
	 */
	private Integer mainBalance;
	/**
	 * 副账户余额
	 */
	private Integer subBalance;
	/**
	 * 主账户余额IOS
	 */
	private Integer mainBalanceIOS;
	/**
	 * 副账户余额IOS
	 */
	private Integer subBalanceIOS;
	
	public Long getMediaChapterId() {
		return mediaChapterId;
	}
	public void setMediaChapterId(Long mediaChapterId) {
		this.mediaChapterId = mediaChapterId;
	}
	public String getMediaChapterTitle() {
		return mediaChapterTitle;
	}
	public void setMediaChapterTitle(String mediaChapterTitle) {
		this.mediaChapterTitle = mediaChapterTitle;
	}
	public Integer getMediaChapterPrice() {
		return mediaChapterPrice;
	}
	public void setMediaChapterPrice(Integer mediaChapterPrice) {
		this.mediaChapterPrice = mediaChapterPrice;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
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
	public Long getWordCnt() {
		return wordCnt;
	}
	public void setWordCnt(Long wordCnt) {
		this.wordCnt = wordCnt;
	}
	public Long getMediaId() {
		return mediaId;
	}
	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
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
	public Long getMediaWordCnt() {
		return mediaWordCnt;
	}
	public void setMediaWordCnt(Long mediaWordCnt) {
		this.mediaWordCnt = mediaWordCnt;
	}
	public Integer getIsSupportFullBuy() {
		return isSupportFullBuy;
	}
	public void setIsSupportFullBuy(Integer isSupportFullBuy) {
		this.isSupportFullBuy = isSupportFullBuy;
	}
	public Integer getSubBalanceIOS() {
		return subBalanceIOS;
	}
	public void setSubBalanceIOS(Integer subBalanceIOS) {
		this.subBalanceIOS = subBalanceIOS;
	}
	public Integer getMainBalanceIOS() {
		return mainBalanceIOS;
	}
	public void setMainBalanceIOS(Integer mainBalanceIOS) {
		this.mainBalanceIOS = mainBalanceIOS;
	}

}
