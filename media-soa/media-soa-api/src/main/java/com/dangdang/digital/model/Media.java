package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Media implements Serializable{
	
	private static final long serialVersionUID = 4608283945260985426L;

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
    
    private String creationDateStart;
    
    private String creationDateEnd;
    
    private String lastModifyDateStart;
    
    private String lastModifyDateEnd;
    
    private String recommandWords;
    
    private Integer catetoryId;
    
    private String catetoryIds;
    
    private Integer isSupportFullBuy;
    
    private Integer priceUnit;
    
    private String lastUpdateChapter;
    
    private Integer lastIndexOrder;
    
    private Long wordCnt;
    
    private String cpShortName;
    
    private String cpCode;
    
    private Integer picCdnStatus;
    
    private Integer isBlack;
    
    private Integer isVip;
    
    private String speaker;
    
    private List<Chapter> chapters = new ArrayList<Chapter>(0);             
    
    
    private Date lastPullChapterDate;
    
    //打包状态 0:未打包  1:已经打包
    private Integer zipStatus;
    
    private Long saleId;
    
    private Long weekHeat;
    
    private Long monthHeat;
    
    private Long heat;//热度
    
    private String freeKey;

    private String pdfFullKey;

    private String pdfFreeKey;

    private String introTitle;

    private String docType;

    private String seriesBook;

    private Long seriesBookId;

    private Long paperBookId;

    private Long readBookId;

    private String isBn;

    private Integer edition;

    private Integer printNo;

    private Integer pageNum;

    private String bookSize;

    private String wordsNum;

    private String printNum;

    private String publisher;

    private String publishLocation;

    private Date publishDate;

    private Integer paperBookPrice;

    private Integer paperBookOverseaPrice;

    private String paperBookCategory;

    private String language;

    private String translator;

    private String metadatafile;

    private String pdfFile;

    private String srcFile;

    private String paperBookOverseaPriceUnit;

    private String copyRightCommpany;

    private Integer pdfPageNumber;

    private Integer probationProportion;

    private Integer isFullBook;

    private Integer backgroundUpdate;

    private Integer readShelfStatus;

    private String swfSize;

    private Integer priceStatus;

    private String epubFile;

    private String category;

    private String taskName;

    private Long batchId;

    private String otherPaperBookId;

    private Integer thirdPartyPermission;

    private Long vendorId;

    private Date uploadDate;

    private Integer iosPrice;

    private Integer catalogLevel;

    private Long promotionId;

    private Integer iapFlag;

    private Integer iapShelfStatus;

    private String iapDeviceType;

    private Integer rateMediaStatus;

    private Integer canUsePoints;

    private String relatedProducts;

    private String isMonth;

    private Date firstLoadDate;

    private String promotionLanguage;

    private Integer pimState;

    private Integer borrowDuration;

    private Date authorizeStartDate;

    private Date authorizeEndDate;

    private String otherPaperBookids;
    
    private String authorIntroduce;

    private String editorRecommend;

    private String massMediaRecommend;
    
    /**
     * 电子书价格
     */
    private Integer price;
    
    public Long getWeekHeat() {
		return weekHeat;
	}

	public void setWeekHeat(Long weekHeat) {
		this.weekHeat = weekHeat;
	}

	public Long getMonthHeat() {
		return monthHeat;
	}

	public void setMonthHeat(Long monthHeat) {
		this.monthHeat = monthHeat;
	}

	public Long getHeat() {
		return heat;
	}

	public void setHeat(Long heat) {
		this.heat = heat;
	}

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
        this.title = title == null ? null : title.trim();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs == null ? null : descs.trim();
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
        this.keyWords = keyWords == null ? null : keyWords.trim();
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic == null ? null : coverPic.trim();
    }

    public String getHdfsPath() {
        return hdfsPath;
    }

    public void setHdfsPath(String hdfsPath) {
        this.hdfsPath = hdfsPath == null ? null : hdfsPath.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
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
        this.creator = creator == null ? null : creator.trim();
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
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
        this.cpResourceId = cpResourceId == null ? null : cpResourceId.trim();
    }

    public String getEncrypkey() {
        return encrypkey;
    }

    public void setEncrypkey(String encrypkey) {
        this.encrypkey = encrypkey == null ? null : encrypkey.trim();
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName == null ? null : authorName.trim();
    }

    public String getAuthorPenname() {
        return authorPenname;
    }

    public void setAuthorPenname(String authorPenname) {
        this.authorPenname = authorPenname == null ? null : authorPenname.trim();
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName == null ? null : providerName.trim();
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
        this.catalog = catalog == null ? null : catalog.trim();
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

	public String getLastModifyDateStart() {
		return lastModifyDateStart;
	}

	public void setLastModifyDateStart(String lastModifyDateStart) {
		this.lastModifyDateStart = lastModifyDateStart;
	}

	public String getLastModifyDateEnd() {
		return lastModifyDateEnd;
	}

	public void setLastModifyDateEnd(String lastModifyDateEnd) {
		this.lastModifyDateEnd = lastModifyDateEnd;
	}

	public String getRecommandWords() {
		return recommandWords;
	}

	public void setRecommandWords(String recommandWords) {
		this.recommandWords = recommandWords;
	}
	public static void main(String[] args) {
		Random d = new Random();
		System.out.println(System.currentTimeMillis()+""+(d.nextInt(899)+100));
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

	public Integer getShelfStatus() {
		return shelfStatus;
	}

	public void setShelfStatus(Integer shelfStatus) {
		this.shelfStatus = shelfStatus;
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

	public List<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}

	public Date getLastPullChapterDate() {
		return lastPullChapterDate;
	}

	public void setLastPullChapterDate(Date lastPullChapterDate) {
		this.lastPullChapterDate = lastPullChapterDate;
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

	public String getCpCode() {
		return cpCode;
	}

	public void setCpCode(String cpCode) {
		this.cpCode = cpCode;
	}

	public Integer getZipStatus() {
		return zipStatus;
	}

	public void setZipStatus(Integer zipStatus) {
		this.zipStatus = zipStatus;
	}

	public Integer getPicCdnStatus() {
		return picCdnStatus;
	}

	public void setPicCdnStatus(Integer picCdnStatus) {
		this.picCdnStatus = picCdnStatus;
	}

	public Integer getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Integer isBlack) {
		this.isBlack = isBlack;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}
	
	public String getFreeKey() {
        return freeKey;
    }

    public void setFreeKey(String freeKey) {
        this.freeKey = freeKey == null ? null : freeKey.trim();
    }

    public String getPdfFullKey() {
        return pdfFullKey;
    }

    public void setPdfFullKey(String pdfFullKey) {
        this.pdfFullKey = pdfFullKey == null ? null : pdfFullKey.trim();
    }

    public String getPdfFreeKey() {
        return pdfFreeKey;
    }

    public void setPdfFreeKey(String pdfFreeKey) {
        this.pdfFreeKey = pdfFreeKey == null ? null : pdfFreeKey.trim();
    }

    public String getIntroTitle() {
        return introTitle;
    }

    public void setIntroTitle(String introTitle) {
        this.introTitle = introTitle == null ? null : introTitle.trim();
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType == null ? null : docType.trim();
    }

    public String getSeriesBook() {
        return seriesBook;
    }

    public void setSeriesBook(String seriesBook) {
        this.seriesBook = seriesBook == null ? null : seriesBook.trim();
    }

    public Long getSeriesBookId() {
        return seriesBookId;
    }

    public void setSeriesBookId(Long seriesBookId) {
        this.seriesBookId = seriesBookId;
    }

    public Long getPaperBookId() {
        return paperBookId;
    }

    public void setPaperBookId(Long paperBookId) {
        this.paperBookId = paperBookId;
    }

    public Long getReadBookId() {
        return readBookId;
    }

    public void setReadBookId(Long readBookId) {
        this.readBookId = readBookId;
    }

    public String getIsBn() {
        return isBn;
    }

    public void setIsBn(String isBn) {
        this.isBn = isBn == null ? null : isBn.trim();
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public Integer getPrintNo() {
        return printNo;
    }

    public void setPrintNo(Integer printNo) {
        this.printNo = printNo;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getBookSize() {
        return bookSize;
    }

    public void setBookSize(String bookSize) {
        this.bookSize = bookSize == null ? null : bookSize.trim();
    }

    public String getWordsNum() {
        return wordsNum;
    }

    public void setWordsNum(String wordsNum) {
        this.wordsNum = wordsNum == null ? null : wordsNum.trim();
    }

    public String getPrintNum() {
        return printNum;
    }

    public void setPrintNum(String printNum) {
        this.printNum = printNum == null ? null : printNum.trim();
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }

    public String getPublishLocation() {
        return publishLocation;
    }

    public void setPublishLocation(String publishLocation) {
        this.publishLocation = publishLocation == null ? null : publishLocation.trim();
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getPaperBookPrice() {
        return paperBookPrice;
    }

    public void setPaperBookPrice(Integer paperBookPrice) {
        this.paperBookPrice = paperBookPrice;
    }

    public Integer getPaperBookOverseaPrice() {
        return paperBookOverseaPrice;
    }

    public void setPaperBookOverseaPrice(Integer paperBookOverseaPrice) {
        this.paperBookOverseaPrice = paperBookOverseaPrice;
    }

    public String getPaperBookCategory() {
        return paperBookCategory;
    }

    public void setPaperBookCategory(String paperBookCategory) {
        this.paperBookCategory = paperBookCategory == null ? null : paperBookCategory.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator == null ? null : translator.trim();
    }

    public String getMetadatafile() {
        return metadatafile;
    }

    public void setMetadatafile(String metadatafile) {
        this.metadatafile = metadatafile == null ? null : metadatafile.trim();
    }

    public String getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(String pdfFile) {
        this.pdfFile = pdfFile == null ? null : pdfFile.trim();
    }

    public String getSrcFile() {
        return srcFile;
    }

    public void setSrcFile(String srcFile) {
        this.srcFile = srcFile == null ? null : srcFile.trim();
    }

    public String getPaperBookOverseaPriceUnit() {
        return paperBookOverseaPriceUnit;
    }

    public void setPaperBookOverseaPriceUnit(String paperBookOverseaPriceUnit) {
        this.paperBookOverseaPriceUnit = paperBookOverseaPriceUnit == null ? null : paperBookOverseaPriceUnit.trim();
    }

    public String getCopyRightCommpany() {
        return copyRightCommpany;
    }

    public void setCopyRightCommpany(String copyRightCommpany) {
        this.copyRightCommpany = copyRightCommpany == null ? null : copyRightCommpany.trim();
    }

    public Integer getPdfPageNumber() {
        return pdfPageNumber;
    }

    public void setPdfPageNumber(Integer pdfPageNumber) {
        this.pdfPageNumber = pdfPageNumber;
    }

    public Integer getProbationProportion() {
        return probationProportion;
    }

    public void setProbationProportion(Integer probationProportion) {
        this.probationProportion = probationProportion;
    }

    public Integer getIsFullBook() {
        return isFullBook;
    }

    public void setIsFullBook(Integer isFullBook) {
        this.isFullBook = isFullBook;
    }

    public Integer getBackgroundUpdate() {
        return backgroundUpdate;
    }

    public void setBackgroundUpdate(Integer backgroundUpdate) {
        this.backgroundUpdate = backgroundUpdate;
    }

    public Integer getReadShelfStatus() {
        return readShelfStatus;
    }

    public void setReadShelfStatus(Integer readShelfStatus) {
        this.readShelfStatus = readShelfStatus;
    }

    public String getSwfSize() {
        return swfSize;
    }

    public void setSwfSize(String swfSize) {
        this.swfSize = swfSize == null ? null : swfSize.trim();
    }

    public Integer getPriceStatus() {
        return priceStatus;
    }

    public void setPriceStatus(Integer priceStatus) {
        this.priceStatus = priceStatus;
    }

    public String getEpubFile() {
        return epubFile;
    }

    public void setEpubFile(String epubFile) {
        this.epubFile = epubFile == null ? null : epubFile.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getOtherPaperBookId() {
        return otherPaperBookId;
    }

    public void setOtherPaperBookId(String otherPaperBookId) {
        this.otherPaperBookId = otherPaperBookId == null ? null : otherPaperBookId.trim();
    }

    public Integer getThirdPartyPermission() {
        return thirdPartyPermission;
    }

    public void setThirdPartyPermission(Integer thirdPartyPermission) {
        this.thirdPartyPermission = thirdPartyPermission;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getIosPrice() {
        return iosPrice;
    }

    public void setIosPrice(Integer iosPrice) {
        this.iosPrice = iosPrice;
    }

    public Integer getCatalogLevel() {
        return catalogLevel;
    }

    public void setCatalogLevel(Integer catalogLevel) {
        this.catalogLevel = catalogLevel;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getIapFlag() {
        return iapFlag;
    }

    public void setIapFlag(Integer iapFlag) {
        this.iapFlag = iapFlag;
    }

    public Integer getIapShelfStatus() {
        return iapShelfStatus;
    }

    public void setIapShelfStatus(Integer iapShelfStatus) {
        this.iapShelfStatus = iapShelfStatus;
    }

    public String getIapDeviceType() {
        return iapDeviceType;
    }

    public void setIapDeviceType(String iapDeviceType) {
        this.iapDeviceType = iapDeviceType == null ? null : iapDeviceType.trim();
    }

    public Integer getRateMediaStatus() {
        return rateMediaStatus;
    }

    public void setRateMediaStatus(Integer rateMediaStatus) {
        this.rateMediaStatus = rateMediaStatus;
    }

    public Integer getCanUsePoints() {
        return canUsePoints;
    }

    public void setCanUsePoints(Integer canUsePoints) {
        this.canUsePoints = canUsePoints;
    }

    public String getRelatedProducts() {
        return relatedProducts;
    }

    public void setRelatedProducts(String relatedProducts) {
        this.relatedProducts = relatedProducts == null ? null : relatedProducts.trim();
    }

    public String getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(String isMonth) {
        this.isMonth = isMonth == null ? null : isMonth.trim();
    }

    public Date getFirstLoadDate() {
        return firstLoadDate;
    }

    public void setFirstLoadDate(Date firstLoadDate) {
        this.firstLoadDate = firstLoadDate;
    }

    public String getPromotionLanguage() {
        return promotionLanguage;
    }

    public void setPromotionLanguage(String promotionLanguage) {
        this.promotionLanguage = promotionLanguage == null ? null : promotionLanguage.trim();
    }

    public Integer getPimState() {
        return pimState;
    }

    public void setPimState(Integer pimState) {
        this.pimState = pimState;
    }

    public Integer getBorrowDuration() {
        return borrowDuration;
    }

    public void setBorrowDuration(Integer borrowDuration) {
        this.borrowDuration = borrowDuration;
    }

    public Date getAuthorizeStartDate() {
        return authorizeStartDate;
    }

    public void setAuthorizeStartDate(Date authorizeStartDate) {
        this.authorizeStartDate = authorizeStartDate;
    }

    public Date getAuthorizeEndDate() {
        return authorizeEndDate;
    }

    public void setAuthorizeEndDate(Date authorizeEndDate) {
        this.authorizeEndDate = authorizeEndDate;
    }

    public String getOtherPaperBookids() {
        return otherPaperBookids;
    }

    public void setOtherPaperBookids(String otherPaperBookids) {
        this.otherPaperBookids = otherPaperBookids == null ? null : otherPaperBookids.trim();
    }

	public String getAuthorIntroduce() {
		return authorIntroduce;
	}

	public void setAuthorIntroduce(String authorIntroduce) {
		this.authorIntroduce = authorIntroduce;
	}

	public String getEditorRecommend() {
		return editorRecommend;
	}

	public void setEditorRecommend(String editorRecommend) {
		this.editorRecommend = editorRecommend;
	}

	public String getMassMediaRecommend() {
		return massMediaRecommend;
	}

	public void setMassMediaRecommend(String massMediaRecommend) {
		this.massMediaRecommend = massMediaRecommend;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}