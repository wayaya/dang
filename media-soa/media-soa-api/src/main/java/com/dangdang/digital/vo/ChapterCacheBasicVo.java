package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description: 章节缓存basicvo All Rights Reserved.
 * 
 * @version 1.0 2014年12月6日 下午6:43:30 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ChapterCacheBasicVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8743048557716891722L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 章节名
	 */
	private String title;

	/**
	 * 子章节名
	 */
	private String subTitle;

	/**
	 * 顺序
	 */
	private Integer index;

	/**
	 * 字节数
	 */
	private Long wordCnt;

	/**
	 * 价格
	 */
	private Integer price;

	/**
	 * ios价格
	 */
	private Integer iosPrice;

	/**
	 * 创建日期
	 */
	private Date creationDate;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 最后修改时间
	 */
	private Date lastModifyedDate;

	/**
	 * 修改人
	 */
	private String modifier;

	/**
	 * 是否免费（0：否 1：是）
	 */
	private String isFree;

	/**
	 * 标签id
	 */
	private String signIds;

	/**
	 * 标签名称
	 */
	private String signNames;

	/**
	 * 卷id
	 */
	private Long volumeId;

	private Long mediaId;

	private Integer isShow;

	/**
	 * 存储路径
	 */
	private String filePath;

	/**
	 * 解密key
	 */
	private String encrypkey;

	/**
	 * 卷名
	 */
	private String volumeName;

	/**
	 * 时长
	 */
	private Integer duration;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Long getWordCnt() {
		return wordCnt;
	}

	public void setWordCnt(Long wordCnt) {
		this.wordCnt = wordCnt;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getIosPrice() {
		return iosPrice;
	}

	public void setIosPrice(Integer iosPrice) {
		this.iosPrice = iosPrice;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModifyedDate() {
		return lastModifyedDate;
	}

	public void setLastModifyedDate(Date lastModifyedDate) {
		this.lastModifyedDate = lastModifyedDate;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public String getSignIds() {
		return signIds;
	}

	public void setSignIds(String signIds) {
		this.signIds = signIds;
	}

	public String getSignNames() {
		return signNames;
	}

	public void setSignNames(String signNames) {
		this.signNames = signNames;
	}

	public Long getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(Long volumeId) {
		this.volumeId = volumeId;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getEncrypkey() {
		return encrypkey;
	}

	public void setEncrypkey(String encrypkey) {
		this.encrypkey = encrypkey;
	}

	public String getVolumeName() {
		return volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

}
