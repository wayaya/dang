package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * 
 * Description: 单品页返回vo All Rights Reserved.
 * 
 * @version 1.0 2014年12月16日 下午5:52:54 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ReturnMediaVo extends ReturnMediaBaseVo {

	private static final long serialVersionUID = -334853097936647762L;

	/**
	 * 主键Id
	 */
	private Long mediaId;

	/**
	 * 章节数
	 */
	private Integer chapterCnt;
	/**
	 * 是否又更新
	 */
	private Integer hasNew;
	/**
	 * 是否全本
	 */
	private Integer isFull;

	/**
	 * 是否支持全本购买
	 */
	private Integer isSupportFullBuy;

	/**
	 * 章节最后更新时间
	 */
	private Long lastPullChapterDate;
	/**
	 * 价格单位（每千字金币数）
	 */

	private Integer priceUnit;
	
	/**
	 * 全本的总价
	 */
	private Long price;

	/**
	 * 最新更新章节名称
	 */
	private String lastUpdateChapter;

	/**
	 * 最新更新章节排序号(对应章节的index)
	 */
	private Integer lastIndexOrder;

	/**
	 * 单本书对应的销售主体Id(如果外层销售主体是单本则该字段与外层销售主体一致，否则不一致)
	 */
	private Long saleId;

	/**
	 * 供应商名称
	 */
	private String cpShortName;

	/**
	 * 包月结束时间，如果没有包月为空
	 */
	private Long monthlyEndTime;

	/**
	 * 是否有全本权限 1：有；0：没有；
	 */
	private Integer isWholeAuthority;

	/**
	 * 是否有章节权限 1：有；0：没有；
	 */
	private Integer isChapterAuthority;

	/**
	 * 资源下架状态（强制下架） 1：上架 0 ：下架
	 */
	private Integer shelfStatus;

	/**
	 * 演讲者
	 */
	private String speaker;

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public Integer getChapterCnt() {
		return chapterCnt;
	}

	public void setChapterCnt(Integer chapterCnt) {
		this.chapterCnt = chapterCnt;
	}

	public Integer getHasNew() {
		return hasNew;
	}

	public void setHasNew(Integer hasNew) {
		this.hasNew = hasNew;
	}

	public Integer getIsFull() {
		return isFull;
	}

	public void setIsFull(Integer isFull) {
		this.isFull = isFull;
	}

	public Integer getIsSupportFullBuy() {
		return isSupportFullBuy;
	}

	public void setIsSupportFullBuy(Integer isSupportFullBuy) {
		this.isSupportFullBuy = isSupportFullBuy;
	}

	public Long getLastPullChapterDate() {
		return lastPullChapterDate;
	}

	public void setLastPullChapterDate(Long lastPullChapterDate) {
		this.lastPullChapterDate = lastPullChapterDate;
	}

	public Integer getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(Integer priceUnit) {
		this.priceUnit = priceUnit;
	}

	public String getLastUpdateChapter() {
		return lastUpdateChapter;
	}

	public void setLastUpdateChapter(String lastUpdateChapter) {
		this.lastUpdateChapter = lastUpdateChapter;
	}

	public Integer getLastIndexOrder() {
		return lastIndexOrder;
	}

	public void setLastIndexOrder(Integer lastIndexOrder) {
		this.lastIndexOrder = lastIndexOrder;
	}

	public String getCpShortName() {
		return cpShortName;
	}

	public void setCpShortName(String cpShortName) {
		this.cpShortName = cpShortName;
	}

	public Long getMonthlyEndTime() {
		return monthlyEndTime;
	}

	public void setMonthlyEndTime(Long monthlyEndTime) {
		this.monthlyEndTime = monthlyEndTime;
	}

	public Integer getIsWholeAuthority() {
		return isWholeAuthority;
	}

	public void setIsWholeAuthority(Integer isWholeAuthority) {
		this.isWholeAuthority = isWholeAuthority;
	}

	public Integer getIsChapterAuthority() {
		return isChapterAuthority;
	}

	public void setIsChapterAuthority(Integer isChapterAuthority) {
		this.isChapterAuthority = isChapterAuthority;
	}

	public Integer getShelfStatus() {
		return shelfStatus;
	}

	public void setShelfStatus(Integer shelfStatus) {
		this.shelfStatus = shelfStatus;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

}
