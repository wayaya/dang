package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.List;

import com.dangdang.digital.model.Author;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.model.Discovery;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.model.UserAuthority;

/**
 * 
 * Description: 用于多线程增加缓存的Vo All Rights Reserved.
 * 
 * @version 1.0 2014年12月9日 上午11:29:27 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class AddCacheVo implements Serializable {
	private static final long serialVersionUID = -5704839475935578596L;
	/**
	 * 增加销售主体缓存
	 */
	public static final int ADD_TYPE_MEDIA_SALE = 101;
	/**
	 * 批量增加销售主体缓存
	 */
	public static final int ADD_TYPE_MEDIA_SALE_BATCH = 102;
	/**
	 * 增加media缓存
	 */
	public static final int ADD_TYPE_MEDIA = 103;
	/**
	 * 批量增加销售主体缓存
	 */
	public static final int ADD_TYPE_MEDIA_BATCH = 104;
	/**
	 * 增加章节缓存
	 */
	public static final int ADD_TYPE_CHAPTER = 105;
	/**
	 * 批量增加章节缓存
	 */
	public static final int ADD_TYPE_CHAPTER_BATCH = 106;
	/**
	 * 增加作者缓存
	 */
	public static final int ADD_TYPE_AUTHOR = 107;
	/**
	 * 批量增加作者缓存
	 */
	public static final int ADD_TYPE_AUTHOR_BATCH = 108;
	/**
	 * 增加用户阅读权限缓存
	 */
	public static final int ADD_TYPE_AUTHORITY = 109;
	/**
	 * 增加用户包月信息缓存
	 */
	public static final int ADD_TYPE_MONTHLY = 111;
	/**
	 * 增加目录缓存
	 */
	public static final int ADD_TYPE_CONTENTS = 110;
	/**
	 * 增加发现缓存
	 */
	public static final int ADD_TYPE_DISCOVERY = 112;
	/**
	 * 增加活动缓存
	 */
	public static final int ADD_TYPE_ACTIVITY = 113;
	/**
	 * 增加活动缓存
	 */
	public static final int ADD_TYPE_ACTIVITY_BATCH = 114;
	/**
	 * 刷新销售主体缓存
	 */
	public static final int REFRESH_TYPE_MEDIA_SALE = 201;
	/**
	 * 批量刷新销售主体缓存
	 */
	public static final int REFRESH_TYPE_MEDIA_SALE_BATCH = 202;
	/**
	 * 刷新media缓存过期时间
	 */
	public static final int REFRESH_TYPE_MEDIA = 203;
	/**
	 * 批量刷新media缓存过期时间
	 */
	public static final int REFRESH_TYPE_MEDIA_BATCH = 204;
	/**
	 * 刷新章节缓存过期时间
	 */
	public static final int REFRESH_TYPE_CHAPTER = 205;
	/**
	 * 批量刷新章节缓存过期时间
	 */
	public static final int REFRESH_TYPE_CHAPTER_BATCH = 206;

	/**
	 * 加载栏目缓存过期时间
	 */
	public static final int REFRESH_TYPE_COLUMN = 207;
	/**
	 * 刷新作者缓存过期时间
	 */
	public static final int REFRESH_TYPE_AUTHOR = 207;
	/**
	 * 批量刷新作者缓存过期时间
	 */
	public static final int REFRESH_TYPE_AUTHOR_BATCH = 208;
	/**
	 * 刷新用户阅读权限缓存过期时间
	 */
	public static final int REFRESH_TYPE_AUTHORITY = 209;
	/**
	 * 刷新用户包月信息缓存过期时间
	 */
	public static final int REFRESH_TYPE_MONTHLY = 210;

	/**
	 * 刷新活动缓存过期时间
	 */
	public static final int REFRESH_TYPE_ACTIVITY = 213;
	/**
	 * 批量刷新活动缓存过期时间
	 */
	public static final int REFRESH_TYPE_ACTIVITY_BATCH = 214;

	private int addType;

	private Media media;

	private List<Media> mediaList;

	private MediaSale mediaSale;

	private List<MediaSale> mediaSaleList;

	private Chapter chapter;

	private List<Chapter> chapterList;

	private Author author;

	private UserAuthority userAuthority;

	private UserMonthlyCacheVo userMonthlyCacheVo;

	private List<Author> authorList;

	private Long mediaSaleId;

	private Long mediaId;

	private Long chapterId;

	private Long authorId;

	private List<Long> mediaSaleIdList;

	private List<Long> mediaIdList;

	private List<Long> chapterIdList;

	private List<Long> authorIdList;

	private String batchKey;

	private Long custId;

	private Discovery discovery;

	private Integer activityId;

	private List<Integer> activityIdList;

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public List<Media> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<Media> mediaList) {
		this.mediaList = mediaList;
	}

	public MediaSale getMediaSale() {
		return mediaSale;
	}

	public void setMediaSale(MediaSale mediaSale) {
		this.mediaSale = mediaSale;
	}

	public List<MediaSale> getMediaSaleList() {
		return mediaSaleList;
	}

	public void setMediaSaleList(List<MediaSale> mediaSaleList) {
		this.mediaSaleList = mediaSaleList;
	}

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	public List<Chapter> getChapterList() {
		return chapterList;
	}

	public void setChapterList(List<Chapter> chapterList) {
		this.chapterList = chapterList;
	}

	public int getAddType() {
		return addType;
	}

	public void setAddType(int addType) {
		this.addType = addType;
	}

	public Long getMediaSaleId() {
		return mediaSaleId;
	}

	public void setMediaSaleId(Long mediaSaleId) {
		this.mediaSaleId = mediaSaleId;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public List<Long> getMediaSaleIdList() {
		return mediaSaleIdList;
	}

	public void setMediaSaleIdList(List<Long> mediaSaleIdList) {
		this.mediaSaleIdList = mediaSaleIdList;
	}

	public List<Long> getMediaIdList() {
		return mediaIdList;
	}

	public void setMediaIdList(List<Long> mediaIdList) {
		this.mediaIdList = mediaIdList;
	}

	public List<Long> getChapterIdList() {
		return chapterIdList;
	}

	public void setChapterIdList(List<Long> chapterIdList) {
		this.chapterIdList = chapterIdList;
	}

	public String getBatchKey() {
		return batchKey;
	}

	public void setBatchKey(String batchKey) {
		this.batchKey = batchKey;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public List<Author> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<Author> authorList) {
		this.authorList = authorList;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public List<Long> getAuthorIdList() {
		return authorIdList;
	}

	public void setAuthorIdList(List<Long> authorIdList) {
		this.authorIdList = authorIdList;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public UserAuthority getUserAuthority() {
		return userAuthority;
	}

	public void setUserAuthority(UserAuthority userAuthority) {
		this.userAuthority = userAuthority;
	}

	public UserMonthlyCacheVo getUserMonthlyCacheVo() {
		return userMonthlyCacheVo;
	}

	public void setUserMonthlyCacheVo(UserMonthlyCacheVo userMonthlyCacheVo) {
		this.userMonthlyCacheVo = userMonthlyCacheVo;
	}

	public Discovery getDiscovery() {
		return discovery;
	}

	public void setDiscovery(Discovery discovery) {
		this.discovery = discovery;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public List<Integer> getActivityIdList() {
		return activityIdList;
	}

	public void setActivityIdList(List<Integer> activityIdList) {
		this.activityIdList = activityIdList;
	}

}
