package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description:media缓存，包含所有字段 All Rights Reserved.
 * 
 * @version 1.0 2014年12月6日 上午11:01:47 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class MediaCacheWholeVo implements Serializable {

	private static final long serialVersionUID = 6701066213411266006L;

	private Long mediaId;

	private String title;

	private String subTitle;

	private String descs;

	private Integer provideId;

	private String keyWords;

	private String coverPic;

	private String hdfsPath;

	private String filePath;

	private String uid;

	private Integer shelfStatus;

	private Long productId;

	private Integer chapterCnt;

	private Integer hasNew;

	private Integer isFull;

	private Date creationDate;

	private Date lastModifyDate;

	private String creator;

	private String modifier;

	private Long authorId;

	private String cpResourceId;

	private String encrypkey;

	private String signIds;

	private String signNames;

	private String role;

	private String authorName;

	private String authorPenname;

	private String providerName;

	private Integer isShow;

	private String catalog;

	private String recommandWords;

	private Integer catetoryId;

	private String catetoryIds;

	private Integer isSupportFullBuy;

	private Long saleId;

	private Integer priceUnit;

	private Integer lastIndexOrder;

	private String lastUpdateChapter;

	private Date lastPullChapterDate;

	/**
	 * 分类id(逗号分隔)
	 */
	private String categoryIds;

	/**
	 * 分类名称（逗号分隔）
	 */
	private String categorys;

	private Long wordCnt;

	private String cpShortName;

	private String speaker;
	
	private String docType;

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
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

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public Integer getProvideId() {
		return provideId;
	}

	public void setProvideId(Integer provideId) {
		this.provideId = provideId;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public String getHdfsPath() {
		return hdfsPath;
	}

	public void setHdfsPath(String hdfsPath) {
		this.hdfsPath = hdfsPath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getShelfStatus() {
		return shelfStatus;
	}

	public void setShelfStatus(Integer shelfStatus) {
		this.shelfStatus = shelfStatus;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getCpResourceId() {
		return cpResourceId;
	}

	public void setCpResourceId(String cpResourceId) {
		this.cpResourceId = cpResourceId;
	}

	public String getEncrypkey() {
		return encrypkey;
	}

	public void setEncrypkey(String encrypkey) {
		this.encrypkey = encrypkey;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorPenname() {
		return authorPenname;
	}

	public void setAuthorPenname(String authorPenname) {
		this.authorPenname = authorPenname;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getRecommandWords() {
		return recommandWords;
	}

	public void setRecommandWords(String recommandWords) {
		this.recommandWords = recommandWords;
	}

	public Integer getCatetoryId() {
		return catetoryId;
	}

	public void setCatetoryId(Integer catetoryId) {
		this.catetoryId = catetoryId;
	}

	public String getCatetoryIds() {
		return catetoryIds;
	}

	public void setCatetoryIds(String catetoryIds) {
		this.catetoryIds = catetoryIds;
	}

	public Integer getIsSupportFullBuy() {
		return isSupportFullBuy;
	}

	public void setIsSupportFullBuy(Integer isSupportFullBuy) {
		this.isSupportFullBuy = isSupportFullBuy;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public Integer getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(Integer priceUnit) {
		this.priceUnit = priceUnit;
	}

	public Integer getLastIndexOrder() {
		return lastIndexOrder;
	}

	public void setLastIndexOrder(Integer lastIndexOrder) {
		this.lastIndexOrder = lastIndexOrder;
	}

	public String getLastUpdateChapter() {
		return lastUpdateChapter;
	}

	public void setLastUpdateChapter(String lastUpdateChapter) {
		this.lastUpdateChapter = lastUpdateChapter;
	}

	public Date getLastPullChapterDate() {
		return lastPullChapterDate;
	}

	public void setLastPullChapterDate(Date lastPullChapterDate) {
		this.lastPullChapterDate = lastPullChapterDate;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getCategorys() {
		return categorys;
	}

	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}

	public Long getWordCnt() {
		return wordCnt;
	}

	public void setWordCnt(Long wordCnt) {
		this.wordCnt = wordCnt;
	}

	public String getCpShortName() {
		return cpShortName;
	}

	public void setCpShortName(String cpShortName) {
		this.cpShortName = cpShortName;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	
}
