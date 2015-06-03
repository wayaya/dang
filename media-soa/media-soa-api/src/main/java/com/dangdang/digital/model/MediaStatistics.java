package com.dangdang.digital.model;

import java.util.Date;

public class MediaStatistics {

	private Long sId;

	private Long saleId;

	private Long mediaId;

	private String mediaTitle;

	private String mediaAuthorPenname;

	private String mediaCover;

	private String mediaIntroduce;

	private String mediaCategoryIds;

	private Date statisticsDay;

	private Long saleCount;

	private Long prizeCount;

	private Long rewardsCount;

	private Float starCount;

	private Long commentCount;

	/**
	 * 下载数量
	 */
	private Long downloadCount;

	/**
	 * 搜索数量
	 */
	private Long searchCount;
	
	/**
	 * 播放数量
	 */
	private Long playCount;

	private String mediaChapterChangeDate;

	private String mediaCreationDate;

	public Long getsId() {
		return sId;
	}

	public void setsId(Long sId) {
		this.sId = sId;
	}

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

	public String getMediaTitle() {
		return mediaTitle;
	}

	public void setMediaTitle(String mediaTitle) {
		this.mediaTitle = mediaTitle == null ? null : mediaTitle.trim();
	}

	public String getMediaAuthorPenname() {
		return mediaAuthorPenname;
	}

	public void setMediaAuthorPenname(String mediaAuthorPenname) {
		this.mediaAuthorPenname = mediaAuthorPenname == null ? null : mediaAuthorPenname.trim();
	}

	public String getMediaCover() {
		return mediaCover;
	}

	public void setMediaCover(String mediaCover) {
		this.mediaCover = mediaCover == null ? null : mediaCover.trim();
	}

	public String getMediaIntroduce() {
		return mediaIntroduce;
	}

	public void setMediaIntroduce(String mediaIntroduce) {
		this.mediaIntroduce = mediaIntroduce == null ? null : mediaIntroduce.trim();
	}

	public String getMediaCategoryIds() {
		return mediaCategoryIds;
	}

	public void setMediaCategoryIds(String mediaCategoryIds) {
		this.mediaCategoryIds = mediaCategoryIds == null ? null : mediaCategoryIds.trim();
	}

	public Date getStatisticsDay() {
		return statisticsDay;
	}

	public void setStatisticsDay(Date statisticsDay) {
		this.statisticsDay = statisticsDay;
	}

	public Long getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(Long saleCount) {
		this.saleCount = saleCount;
	}

	public Long getPrizeCount() {
		return prizeCount;
	}

	public void setPrizeCount(Long prizeCount) {
		this.prizeCount = prizeCount;
	}

	public Long getRewardsCount() {
		return rewardsCount;
	}

	public void setRewardsCount(Long rewardsCount) {
		this.rewardsCount = rewardsCount;
	}

	public Float getStarCount() {
		return starCount;
	}

	public void setStarCount(Float starCount) {
		this.starCount = starCount;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public String getMediaChapterChangeDate() {
		return mediaChapterChangeDate;
	}

	public void setMediaChapterChangeDate(String mediaChapterChangeDate) {
		this.mediaChapterChangeDate = mediaChapterChangeDate == null ? null : mediaChapterChangeDate.trim();
	}

	public String getMediaCreationDate() {
		return mediaCreationDate;
	}

	public void setMediaCreationDate(String mediaCreationDate) {
		this.mediaCreationDate = mediaCreationDate == null ? null : mediaCreationDate.trim();
	}

	public Long getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Long downloadCount) {
		this.downloadCount = downloadCount;
	}

	public Long getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(Long searchCount) {
		this.searchCount = searchCount;
	}

	public Long getPlayCount() {
		return playCount;
	}

	public void setPlayCount(Long playCount) {
		this.playCount = playCount;
	}
	
}