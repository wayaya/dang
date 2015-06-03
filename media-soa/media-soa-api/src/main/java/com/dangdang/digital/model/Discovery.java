package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class Discovery implements Serializable{
	
	private static final long serialVersionUID = 5187605466376471942L;
	//文字
	public static final int WORD = 0;
	//大图
	public static final int BIG_PIC = 1;
	//小图
	public static final int SMALL_PIC = 2;
    private Long id;

    private String author;

    private Long mediaId;

    private String mediaName;

    private Integer firstCatetoryId;

    private String firstCatetoryName;

    private Integer type;

    private Long columnId;

    private String columnName;

    private Integer stars;

    private Integer reviewCnt;

    private Integer collectCnt;
    
    private Integer topOrder;

    private String operator;

    private Integer shareCnt;

    private String signName;

    private Integer secondCatetoryId;

    private String secondCatetoryName;

    private Integer topCnt;

    private Integer discardCnt;

    private String cardTitle;

    private String cardRemark;

    private String pic1Path;

    private String pic2Path;

    private String pic3Path;

    private Integer cardType;

    private Date showStartDate;

    private Date showEndDate;
    
    private String showStartStartDate;
    
    private String showStartEndDate;
    
    private String showEndStartDate;
    
    private String showEndEndDate;

    private String title;

    private String content;
    //状态 0:未发送  1:已发送
    private Integer state;
    
    //是否显示 0:不显示 1:显示 
    private Integer isShow;
    
    private String signId;
    
    private String htmlContent = null;
    
    private Long custId;
    
    private String platForm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName == null ? null : mediaName.trim();
    }

    public Integer getFirstCatetoryId() {
        return firstCatetoryId;
    }

    public void setFirstCatetoryId(Integer firstCatetoryId) {
        this.firstCatetoryId = firstCatetoryId;
    }

    public String getFirstCatetoryName() {
        return firstCatetoryName;
    }

    public void setFirstCatetoryName(String firstCatetoryName) {
        this.firstCatetoryName = firstCatetoryName == null ? null : firstCatetoryName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getReviewCnt() {
        return reviewCnt;
    }

    public void setReviewCnt(Integer reviewCnt) {
        this.reviewCnt = reviewCnt;
    }

    public Integer getCollectCnt() {
        return collectCnt;
    }

    public void setCollectCnt(Integer collectCnt) {
        this.collectCnt = collectCnt;
    }

    public Integer getTopOrder() {
		return topOrder;
	}

	public void setTopOrder(Integer topOrder) {
		this.topOrder = topOrder;
	}

	public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Integer getShareCnt() {
        return shareCnt;
    }

    public void setShareCnt(Integer shareCnt) {
        this.shareCnt = shareCnt;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName == null ? null : signName.trim();
    }

    public Integer getSecondCatetoryId() {
        return secondCatetoryId;
    }

    public void setSecondCatetoryId(Integer secondCatetoryId) {
        this.secondCatetoryId = secondCatetoryId;
    }

    public String getSecondCatetoryName() {
        return secondCatetoryName;
    }

    public void setSecondCatetoryName(String secondCatetoryName) {
        this.secondCatetoryName = secondCatetoryName == null ? null : secondCatetoryName.trim();
    }

    public Integer getTopCnt() {
        return topCnt;
    }

    public void setTopCnt(Integer topCnt) {
        this.topCnt = topCnt;
    }

    public Integer getDiscardCnt() {
        return discardCnt;
    }

    public void setDiscardCnt(Integer discardCnt) {
        this.discardCnt = discardCnt;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle == null ? null : cardTitle.trim();
    }

    public String getCardRemark() {
        return cardRemark;
    }

    public void setCardRemark(String cardRemark) {
        this.cardRemark = cardRemark == null ? null : cardRemark.trim();
    }

    public String getPic1Path() {
        return pic1Path;
    }

    public void setPic1Path(String pic1Path) {
        this.pic1Path = pic1Path == null ? null : pic1Path.trim();
    }

    public String getPic2Path() {
        return pic2Path;
    }

    public void setPic2Path(String pic2Path) {
        this.pic2Path = pic2Path == null ? null : pic2Path.trim();
    }

    public String getPic3Path() {
        return pic3Path;
    }

    public void setPic3Path(String pic3Path) {
        this.pic3Path = pic3Path == null ? null : pic3Path.trim();
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Date getShowStartDate() {
        return showStartDate;
    }

    public void setShowStartDate(Date showStartDate) {
        this.showStartDate = showStartDate;
    }

    public Date getShowEndDate() {
        return showEndDate;
    }

    public void setShowEndDate(Date showEndDate) {
        this.showEndDate = showEndDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getShowStartStartDate() {
		return showStartStartDate;
	}

	public void setShowStartStartDate(String showStartStartDate) {
		this.showStartStartDate = showStartStartDate;
	}

	public String getShowStartEndDate() {
		return showStartEndDate;
	}

	public void setShowStartEndDate(String showStartEndDate) {
		this.showStartEndDate = showStartEndDate;
	}

	public String getShowEndStartDate() {
		return showEndStartDate;
	}

	public void setShowEndStartDate(String showEndStartDate) {
		this.showEndStartDate = showEndStartDate;
	}

	public String getShowEndEndDate() {
		return showEndEndDate;
	}

	public void setShowEndEndDate(String showEndEndDate) {
		this.showEndEndDate = showEndEndDate;
	}

	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getPlatForm() {
		return platForm;
	}

	public void setPlatForm(String platForm) {
		this.platForm = platForm;
	}
	
	
}