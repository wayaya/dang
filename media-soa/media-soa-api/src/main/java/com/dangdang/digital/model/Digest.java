package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.dangdang.digital.constant.DigestMoodEnum;
import com.dangdang.digital.utils.DateUtil;

/**
 * Description: 精品阅读章节实体
 * All Rights Reserved.
 * @version 1.0  2015年1月20日 上午11:48:27  by 代鹏（daipeng@dangdang.com）创建
 */
public class Digest implements Serializable{
	
	private static final long serialVersionUID = -2313576079739813188L;
	//首页下拉刷新动作
	public static final String NEW_ACTION = "new";
	//首页上拉动作
	public static final String OLD_ACTION = "old";
	
	private Long id;

    private String author;

    private Long mediaId;

    private Long mediaChapterId;

    private String mediaName;

    private Integer firstCatetoryId;

    private String firstCatetoryName;

    /**
     * 1 翻篇儿  2 抢先读
     */
    private Integer type;

    private Long columnId;

    private String columnName;

    private Integer stars;

    private Integer reviewCnt;

    private Integer collectCnt;
    
    /**
     * 点击数
     */
    private Integer clickCnt;

    private String operator;

    private Integer shareCnt;
    
    private Integer topCnt;

    private String cardTitle;

    private String cardRemark;

    private String pic1Path;
    
    private String smallPic1Path;
    
    private String smallPic2Path;
    
    private String smallPic3Path;
    
    //卡片类型 0:全文字 1:文字加图片 2:全图 3:三张小图
    private Integer cardType;

    private Date showStartDate;

    private Date createDate;

    private String title;
    //0:不显示;1:显示
    private Integer isShow;
    //章节对应所有标签ids，逗号分隔
    private String signIds;
    //白天:0 黑夜:1
    private Integer dayOrNight;
    //心情类别
    private Integer mood;
    //权重置顶排序
    private Integer weight;
    
    private String content;
    //最后一次编辑更新时间
    private Date lastUpdateDate;
    
    private String showStartStartDate;
    
    private String showStartEndDate;
    //首页控制排序分页字段:yyyy-MM-dd + HH:mm:ss + weight+ random(4)
    private Long sortPage;

	//非入库字段
    //精品对应标签List集合
    private List<GoodArticleSign> signList;
    //createDate日期long型
    private Long createTime;
    //心情名称
    private String moodName;
    //展示时间对应yyyy-MM-dd的date类型
    private Date showStartDateYmd;
    //展示时间对应yyyy-MM-dd的long类型
    private long showStartDateYmdLong;
    //展示时间对应yyyy-MM-dd的String类型
    private String showStartDateYmdString;
    //精品对应作者List集合
    private List<BookAuthor> authorList;
    
    private Long barId;
    
    private Integer isDel;
    
    public static final Integer DIGEST_ISSHOW_STATUS_FALSE = 0;
    public static final Integer DIGEST_ISSHOW_STATUS_TRUE = 1;
    
    /**
     * 翻篇
     */
    public static final Integer DIGEST_TYPE_FP = 1;
    /**
     * 抢先读
     */
    public static final Integer DIGEST_TYPE_QXD = 2;
    
    /**
     * 频道
     */
    public static final Integer DIGEST_TYPE_PD = 3;
    
    /**
     * 书评
     */
    public static final Integer DIGEST_TYPE_SP = 4;
    
    /**
     * 书吧
     */
    public static final Integer DIGEST_TYPE_SB = 5;
    
    public Long getBarId() {
		return barId;
	}

	public void setBarId(Long barId) {
		this.barId = barId;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public List<BookAuthor> getAuthorList() {
    	try {
    		if(authorList == null && author != null && author.length() > 0 ){
    			authorList = new ArrayList<BookAuthor>();
    			String[] signArr = author.split(";");
				for(String sign:signArr){
					String[] keyValue = sign.split(":");
					if(keyValue.length == 2){
						List<String> list = Arrays.asList(keyValue);
						String id = list.get(0);
						String name = list.get(1);
						BookAuthor a = new BookAuthor();
						a.setAuthorId(Long.parseLong(id));
						a.setName(name);
						authorList.add(a);
					}
				}
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authorList;
	}

	public String getSmallPic1Path() {
		return smallPic1Path;
	}

	public void setSmallPic1Path(String smallPic1Path) {
		this.smallPic1Path = smallPic1Path;
	}

	public String getSmallPic2Path() {
		return smallPic2Path;
	}

	public void setSmallPic2Path(String smallPic2Path) {
		this.smallPic2Path = smallPic2Path;
	}

	public String getSmallPic3Path() {
		return smallPic3Path;
	}

	public void setSmallPic3Path(String smallPic3Path) {
		this.smallPic3Path = smallPic3Path;
	}

	public Integer getClickCnt() {
		return clickCnt;
	}

	public void setClickCnt(Integer clickCnt) {
		this.clickCnt = clickCnt;
	}

	public void setAuthorList(List<BookAuthor> authorList) {
		this.authorList = authorList;
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

    public Long getMediaChapterId() {
        return mediaChapterId;
    }

    public void setMediaChapterId(Long mediaChapterId) {
        this.mediaChapterId = mediaChapterId;
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

    public Integer getTopCnt() {
        return topCnt;
    }

    public void setTopCnt(Integer topCnt) {
        this.topCnt = topCnt;
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

    public Date getShowStartDate() {
        return showStartDate;
    }

    public void setShowStartDate(Date showStartDate) {
        this.showStartDate = showStartDate;
    }
    
    public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSignIds() {
		return signIds;
	}

	public void setSignIds(String signIds) {
		this.signIds = signIds == null ? null : signIds.trim();
	}

    public Integer getMood() {
        return mood;
    }

    public void setMood(Integer mood) {
        this.mood = mood;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getDayOrNight() {
		return dayOrNight;
	}

	public void setDayOrNight(Integer dayOrNight) {
		this.dayOrNight = dayOrNight;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public List<GoodArticleSign> getSignList() {
		try {
			if(signIds != null && signIds.length() > 0 && signList == null){
				signList = new ArrayList<GoodArticleSign>();
				String[] signArr = signIds.split(";");
				for(String sign:signArr){
					String[] keyValue = sign.split(":");
					if(keyValue.length == 2){
						List<String> list = Arrays.asList(keyValue);
						String id = list.get(0);
						String name = list.get(1);
						GoodArticleSign goodSign = new GoodArticleSign();
						goodSign.setId(Integer.parseInt(id));
						goodSign.setName(name);
						signList.add(goodSign);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signList;
	}

	public void setSignList(List<GoodArticleSign> signList) {
		this.signList = signList;
	}

	public Long getCreateTime() {
		if(createDate == null){
			return null;
		}
		createTime = this.createDate.getTime();
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getMoodName() {
		if(this.getMood() != null){
			DigestMoodEnum digestMoodEnum = DigestMoodEnum.getDigestMoodEnumByKey(this.getMood());
			if(digestMoodEnum != null){
				moodName = digestMoodEnum.getName();
			}
		}
		return moodName;
	}

	public void setMoodName(String moodName) {
		this.moodName = moodName;
	}

	public Date getShowStartDateYmd() {
		if(this.showStartDate != null){
			String ymd = DateUtil.format(this.showStartDate, "yyyy-MM-dd");
			return DateUtil.parseStringToDate(ymd + " 00:00:00");
		}
		return showStartDateYmd;
	}

	public void setShowStartDateYmd(Date showStartDateYmd) {
		this.showStartDateYmd = showStartDateYmd;
	}

	public long getShowStartDateYmdLong() {
		if(this.showStartDate != null){
			String ymd = DateUtil.format(this.showStartDate, "yyyy-MM-dd");
			return DateUtil.parseStringToDate(ymd + " 00:00:00").getTime();
		}
		return showStartDateYmdLong;
	}

	public void setShowStartDateYmdLong(long showStartDateYmdLong) {
		this.showStartDateYmdLong = showStartDateYmdLong;
	}

	public String getShowStartDateYmdString() {
		if(this.showStartDate != null){
			return DateUtil.format(this.showStartDate, "yyyy-MM-dd");
		}
		return showStartDateYmdString;
	}

	public void setShowStartDateYmdString(String showStartDateYmdString) {
		this.showStartDateYmdString = showStartDateYmdString;
	}

	public Long getSortPage() {
		return sortPage;
	}

	public void setSortPage(Long sortPage) {
		this.sortPage = sortPage;
	}
	
}