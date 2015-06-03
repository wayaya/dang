package com.dangdang.digital.vo;

/**
 * Description: 搜索返回实体vo All Rights Reserved.
 * 
 * @version 1.0 2014年12月13日 下午3:08:16 by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public class SearchReturnVo {
	/**
	 * 销售实体ID
	 */
	private Long saleId;

	/**
	 * 资源ID
	 */
	private Long mediaId;

	/**
	 * 资源图片
	 */
	private String mediaPic;

	/**
	 * 是否全本
	 */
	private Integer isFull;

	/**
	 * 章节数
	 */
	private Long chapterCnt;

	/**
	 * 书名
	 */
	private String title;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 分类
	 */
	private String category;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 演讲者
	 */
	private String speaker;

	/**
	 * 是否收藏 0：未收藏，1：收藏
	 */
	private int isStore;

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

	public String getMediaPic() {
		return mediaPic;
	}

	public void setMediaPic(String mediaPic) {
		this.mediaPic = mediaPic;
	}

	public Integer getIsFull() {
		return isFull;
	}

	public void setIsFull(Integer isFull) {
		this.isFull = isFull;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getChapterCnt() {
		return chapterCnt;
	}

	public void setChapterCnt(Long chapterCnt) {
		this.chapterCnt = chapterCnt;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public int getIsStore() {
		return isStore;
	}

	public void setIsStore(int isStore) {
		this.isStore = isStore;
	}

	@Override
	public String toString() {
		return "SearchReturnVo [saleId=" + saleId + ", mediaId=" + mediaId + ", mediaPic=" + mediaPic + ", isFull="
				+ isFull + ", chapterCnt=" + chapterCnt + ", title=" + title + ", author=" + author + ", category="
				+ category + ", description=" + description + ", speaker=" + speaker + "]";
	}

}
