package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class Chapter implements Serializable {

	private static final long serialVersionUID = -4478742737175730222L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 子标题
	 */
	private String subTitle;

	/**
	 * 排序
	 */
	private Integer index;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 字数
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
	 * 创建时间
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
	 * 是否免费 Constans.IS_FREE_NO ; Constans.IS_FREE_YES
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

	private String recommandWords;

	/**
	 * 卷
	 */
	private Long volumeId;

	/**
	 * 外键 mediaId
	 */
	private Long mediaId;

	private Integer isShow;

	private String content;

	private Integer order;

	/**
	 * 文件存储路径
	 */
	private String filePath;

	/**
	 * 解密key
	 */
	private String encrypkey;

	private String creationDateStart;

	private String creationDateEnd;

	private String lastModifyedDateStart;

	private String lastModifyedDateEnd;

	private String volumeName;

	/**
	 * 网络完整下载路径
	 */
	private String httpPath;

	private String encryptVersion;
	/**
	 * 时长
	 */
	private Integer duration;
	
	/**
	 * 上浮
	 */
	private Integer upRation;

	/**
	 * 下浮
	 */
	private Integer downRation;
	
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
		this.title = title == null ? null : title.trim();
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle == null ? null : subTitle.trim();
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc == null ? null : desc.trim();
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator == null ? null : creator.trim();
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
		this.modifier = modifier == null ? null : modifier.trim();
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree == null ? null : isFree.trim();
	}

	public String getSignIds() {
		return signIds;
	}

	public void setSignIds(String signIds) {
		this.signIds = signIds == null ? null : signIds.trim();
	}

	public String getSignNames() {
		return signNames;
	}

	public void setSignNames(String signNames) {
		this.signNames = signNames == null ? null : signNames.trim();
	}

	public String getRecommandWords() {
		return recommandWords;
	}

	public void setRecommandWords(String recommandWords) {
		this.recommandWords = recommandWords == null ? null : recommandWords.trim();
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
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

	public String getCreationDateStart() {
		return creationDateStart;
	}

	public void setCreationDateStart(String creationDateStart) {
		this.creationDateStart = creationDateStart;
	}

	public String getCreationDateEnd() {
		return creationDateEnd;
	}

	public void setCreationDateEnd(String creationDateEnd) {
		this.creationDateEnd = creationDateEnd;
	}

	public String getLastModifyedDateStart() {
		return lastModifyedDateStart;
	}

	public void setLastModifyedDateStart(String lastModifyedDateStart) {
		this.lastModifyedDateStart = lastModifyedDateStart;
	}

	public String getLastModifyedDateEnd() {
		return lastModifyedDateEnd;
	}

	public void setLastModifyedDateEnd(String lastModifyedDateEnd) {
		this.lastModifyedDateEnd = lastModifyedDateEnd;
	}

	public String getHttpPath() {
		return httpPath;
	}

	public void setHttpPath(String httpPath) {
		this.httpPath = httpPath;
	}

	public String getEncryptVersion() {
		return encryptVersion;
	}

	public void setEncryptVersion(String encryptVersion) {
		this.encryptVersion = encryptVersion;
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

	public Integer getUpRation() {
		return upRation;
	}

	public void setUpRation(Integer upRation) {
		this.upRation = upRation;
	}

	public Integer getDownRation() {
		return downRation;
	}

	public void setDownRation(Integer downRation) {
		this.downRation = downRation;
	}
}