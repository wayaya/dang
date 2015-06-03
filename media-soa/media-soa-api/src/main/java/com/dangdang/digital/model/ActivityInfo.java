package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

import com.dangdang.digital.utils.DateUtil;

public class ActivityInfo implements Serializable {
	/**
	   * @Fields serialVersionUID : TODO 2014年12月31日 下午12:30:51  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = 2614051771587455050L;

	/**
	 * 主键ID 促销ID
	 */
    private Integer activityId;

    /**
     * 促销名称
     */
    private String activityName;

    /**
     * 活动开始时间
     */
    private Date startTime;
    
    /**
     * 活动开始时间string
     */
    private String startTimeString;

    /**
     * 活动结束时间
     */
    private Date endTime;
    
    /**
     * 活动结束时间
     */
    private String endTimeString;

    /**
     * 方案ID
     */
    private Integer activityTypeId;

    /**
     * 方案编号
     */
    private String activityTypeCode;

    /**
     * 是否为第一次充值
     */
    private Integer isFirstDeposit;

    /**
     * 前N名充值用户
     */
    private Integer isPreviousNumber;

    /**
     * 赠送金币比例
     */
    private Integer giveScale;

    /**
     * 消费满N金币
     */
    private Integer consumeSatisfy;

    /**
     * 原会员级别
     */
    private String originalLevel;

    /**
     * 新会员级别
     */
    private String newLevel;

    /**
     * 赠送金币
     */
    private Integer giveGoldPiece;

    /**
     * 每天最多膜拜次数
     */
    private Integer dayWorshipLimit;

    /**
     * 每次膜拜最少奖励金币
     */
    private Integer lowestGoldPiece;

    /**
     * 每次膜拜最多奖励金币
     */
    private Integer highestGoldPiece;

    /**
     * 是否为全本
     */
    private Integer isWholeMedia;

    /**
     * 每次消费满的章节数量
     */
    private Integer consumeChapter;

    /**
     * 打折比例
     */
    private Integer discount;

    /**
     * 是否包月（0：非包月 1：是包月）
     */
    private Integer isMonthlyPayment;

    /**
     * 包月类型：1001：全场、1002：按栏目包月、1003：按分类包月
     */
    private String monthlyPaymentType;
    
    /**
     * 获取类型：0：购买  1：赠送
     */
    private Integer monthlyBuyOrGive;
    
    /**
     * 包月原价
     */
    private Integer monthlyPaymentOriginalPrice;

    /**
     * 包月的销售金额
     */
    private Integer monthlyPaymentPrice;
    
    /**
     * 购买折扣
     */
    private Integer monthlyPaymentDiscount;

    /**
     * 如果是按栏目包月的话此处为栏目ID，如果是按分类包月的话此处为分类的编号；
     */
    private String monthlyPaymentRelation;

	 /**
     * 包月购买天数
     */
    private Integer monthlyBuyDays;

    /**
     * 包月赠送天数
     */
    private Integer monthlyGiveDays;
    
    /**
     * 是否为一口价（0：不是一口价, 1:是一口价）
     */
    private Integer isFixedPrice;

    /**
     * 一口价金额
     */
    private Integer fixedPrice;
    
    /**
     * 购买福袋个数
     */
    private Integer prizeQuantity;
    
    /**
     * 购买福袋金额
     */
    private Integer prizePrice;
    
    /**
     * 充值金额（单位：分）
     */
    private Integer depositMoney;
    
    /**
     * 充值阅读币（单位：个）
     */
    private Integer depositReadPrice;
    
    /**
     * 充值阅读币（送）（单位：个）
     */
    private Integer depositGiftReadPrice;
    
    private Integer depositGiftGoldPrice;
    
    /**
     * 关联商品编号
     */
    private String relationProductId;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 最后修改时间
     */
    private Date lastChangedDate;
    
    /**
     * 类型状态(0：无效  1:有效，)
     */
    private Integer status;
    
    /**
   	 * 来源平台 PayFromPaltform
   	 */
    private String fromPaltform;
    
    /**
     * 银铃铛有效期（临时字段）
     */
    private Integer effectiveDay;
    
    /**
     * 充值编号
     */
    private String depositCode;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeString() {
		return startTimeString;
	}

	public void setStartTimeString(String startTimeString) {
		try {
			this.startTime = DateUtil.getdateFromString(startTimeString, DateUtil.DATE_PATTERN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.startTimeString = startTimeString;
	}

	public String getEndTimeString() {
		return endTimeString;
	}

	public void setEndTimeString(String endTimeString) {
		try {
			this.endTime = DateUtil.getdateFromString(endTimeString, DateUtil.DATE_PATTERN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.endTimeString = endTimeString;
	}

	public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getActivityTypeCode() {
        return activityTypeCode;
    }

    public void setActivityTypeCode(String activityTypeCode) {
        this.activityTypeCode = activityTypeCode == null ? null : activityTypeCode.trim();
    }

    public Integer getIsFirstDeposit() {
        return isFirstDeposit;
    }

    public void setIsFirstDeposit(Integer isFirstDeposit) {
        this.isFirstDeposit = isFirstDeposit;
    }

    public Integer getIsPreviousNumber() {
        return isPreviousNumber;
    }

    public void setIsPreviousNumber(Integer isPreviousNumber) {
        this.isPreviousNumber = isPreviousNumber;
    }

    public Integer getGiveScale() {
        return giveScale;
    }

    public void setGiveScale(Integer giveScale) {
        this.giveScale = giveScale;
    }

    public Integer getConsumeSatisfy() {
        return consumeSatisfy;
    }

    public void setConsumeSatisfy(Integer consumeSatisfy) {
        this.consumeSatisfy = consumeSatisfy;
    }

    public String getOriginalLevel() {
        return originalLevel;
    }

    public void setOriginalLevel(String originalLevel) {
        this.originalLevel = originalLevel == null ? null : originalLevel.trim();
    }

    public String getNewLevel() {
        return newLevel;
    }

    public void setNewLevel(String newLevel) {
        this.newLevel = newLevel == null ? null : newLevel.trim();
    }

    public Integer getGiveGoldPiece() {
        return giveGoldPiece;
    }

    public void setGiveGoldPiece(Integer giveGoldPiece) {
        this.giveGoldPiece = giveGoldPiece;
    }

    public Integer getDayWorshipLimit() {
        return dayWorshipLimit;
    }

    public void setDayWorshipLimit(Integer dayWorshipLimit) {
        this.dayWorshipLimit = dayWorshipLimit;
    }

    public Integer getLowestGoldPiece() {
        return lowestGoldPiece;
    }

    public void setLowestGoldPiece(Integer lowestGoldPiece) {
        this.lowestGoldPiece = lowestGoldPiece;
    }

    public Integer getHighestGoldPiece() {
        return highestGoldPiece;
    }

    public void setHighestGoldPiece(Integer highestGoldPiece) {
        this.highestGoldPiece = highestGoldPiece;
    }

    public Integer getIsWholeMedia() {
        return isWholeMedia;
    }

    public void setIsWholeMedia(Integer isWholeMedia) {
        this.isWholeMedia = isWholeMedia;
    }

    public Integer getConsumeChapter() {
        return consumeChapter;
    }

    public void setConsumeChapter(Integer consumeChapter) {
        this.consumeChapter = consumeChapter;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

	public Integer getIsMonthlyPayment() {
		return isMonthlyPayment;
	}

	public void setIsMonthlyPayment(Integer isMonthlyPayment) {
		this.isMonthlyPayment = isMonthlyPayment;
	}

	public String getMonthlyPaymentType() {
		return monthlyPaymentType;
	}

	public void setMonthlyPaymentType(String monthlyPaymentType) {
		this.monthlyPaymentType = monthlyPaymentType;
	}

	public Integer getMonthlyBuyOrGive() {
		return monthlyBuyOrGive;
	}

	public void setMonthlyBuyOrGive(Integer monthlyBuyOrGive) {
		this.monthlyBuyOrGive = monthlyBuyOrGive;
	}

	public Integer getMonthlyPaymentOriginalPrice() {
		return monthlyPaymentOriginalPrice;
	}

	public void setMonthlyPaymentOriginalPrice(Integer monthlyPaymentOriginalPrice) {
		this.monthlyPaymentOriginalPrice = monthlyPaymentOriginalPrice;
	}

	public Integer getMonthlyPaymentPrice() {
		return monthlyPaymentPrice;
	}

	public void setMonthlyPaymentPrice(Integer monthlyPaymentPrice) {
		this.monthlyPaymentPrice = monthlyPaymentPrice;
	}

	public Integer getMonthlyPaymentDiscount() {
		return monthlyPaymentDiscount;
	}

	public void setMonthlyPaymentDiscount(Integer monthlyPaymentDiscount) {
		this.monthlyPaymentDiscount = monthlyPaymentDiscount;
	}

	public String getMonthlyPaymentRelation() {
		return monthlyPaymentRelation;
	}

	public void setMonthlyPaymentRelation(String monthlyPaymentRelation) {
		this.monthlyPaymentRelation = monthlyPaymentRelation;
	}

	public Integer getMonthlyBuyDays() {
		return monthlyBuyDays;
	}

	public void setMonthlyBuyDays(Integer monthlyBuyDays) {
		this.monthlyBuyDays = monthlyBuyDays;
	}

	public Integer getMonthlyGiveDays() {
		return monthlyGiveDays;
	}

	public void setMonthlyGiveDays(Integer monthlyGiveDays) {
		this.monthlyGiveDays = monthlyGiveDays;
	}

	public Integer getIsFixedPrice() {
        return isFixedPrice;
    }

    public void setIsFixedPrice(Integer isFixedPrice) {
        this.isFixedPrice = isFixedPrice;
    }

    public Integer getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(Integer fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public Integer getPrizeQuantity() {
		return prizeQuantity;
	}

	public void setPrizeQuantity(Integer prizeQuantity) {
		this.prizeQuantity = prizeQuantity;
	}

	public Integer getPrizePrice() {
		return prizePrice;
	}

	public void setPrizePrice(Integer prizePrice) {
		this.prizePrice = prizePrice;
	}

	public Integer getDepositMoney() {
		return depositMoney;
	}

	public void setDepositMoney(Integer depositMoney) {
		this.depositMoney = depositMoney;
	}

	public Integer getDepositReadPrice() {
		return depositReadPrice;
	}

	public void setDepositReadPrice(Integer depositReadPrice) {
		this.depositReadPrice = depositReadPrice;
	}

	public Integer getDepositGiftReadPrice() {
		return depositGiftReadPrice;
	}

	public void setDepositGiftReadPrice(Integer depositGiftReadPrice) {
		this.depositGiftReadPrice = depositGiftReadPrice;
	}

	public String getRelationProductId() {
		return relationProductId;
	}

	public void setRelationProductId(String relationProductId) {
		this.relationProductId = relationProductId;
	}

	public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getLastChangedDate() {
        return lastChangedDate;
    }

    public void setLastChangedDate(Date lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFromPaltform() {
		return fromPaltform;
	}

	public void setFromPaltform(String fromPaltform) {
		this.fromPaltform = fromPaltform;
	}

	public Integer getDepositGiftGoldPrice() {
		return depositGiftGoldPrice;
	}

	public void setDepositGiftGoldPrice(Integer depositGiftGoldPrice) {
		this.depositGiftGoldPrice = depositGiftGoldPrice;
	}

	public Integer getEffectiveDay() {
		return effectiveDay;
	}

	public String getDepositCode() {
		return depositCode;
	}

	public void setDepositCode(String depositCode) {
		this.depositCode = depositCode;
	}

	public void setEffectiveDay(Integer effectiveDay) {
		this.effectiveDay = effectiveDay;
	}

	@Override
	public String toString() {
		return "ActivityInfo [activityId=" + activityId + ", activityName="
				+ activityName + ", startTime=" + startTime
				+ ", startTimeString=" + startTimeString + ", endTime="
				+ endTime + ", endTimeString=" + endTimeString
				+ ", activityTypeId=" + activityTypeId + ", activityTypeCode="
				+ activityTypeCode + ", isFirstDeposit=" + isFirstDeposit
				+ ", isPreviousNumber=" + isPreviousNumber + ", giveScale="
				+ giveScale + ", consumeSatisfy=" + consumeSatisfy
				+ ", originalLevel=" + originalLevel + ", newLevel=" + newLevel
				+ ", giveGoldPiece=" + giveGoldPiece + ", dayWorshipLimit="
				+ dayWorshipLimit + ", lowestGoldPiece=" + lowestGoldPiece
				+ ", highestGoldPiece=" + highestGoldPiece + ", isWholeMedia="
				+ isWholeMedia + ", consumeChapter=" + consumeChapter
				+ ", discount=" + discount + ", isMonthlyPayment="
				+ isMonthlyPayment + ", monthlyPaymentType="
				+ monthlyPaymentType + ", monthlyBuyOrGive=" + monthlyBuyOrGive
				+ ", monthlyPaymentOriginalPrice="
				+ monthlyPaymentOriginalPrice + ", monthlyPaymentPrice="
				+ monthlyPaymentPrice + ", monthlyPaymentDiscount="
				+ monthlyPaymentDiscount + ", monthlyPaymentRelation="
				+ monthlyPaymentRelation + ", monthlyBuyDays=" + monthlyBuyDays
				+ ", monthlyGiveDays=" + monthlyGiveDays + ", isFixedPrice="
				+ isFixedPrice + ", fixedPrice=" + fixedPrice
				+ ", prizeQuantity=" + prizeQuantity + ", prizePrice="
				+ prizePrice + ", depositMoney=" + depositMoney
				+ ", depositReadPrice=" + depositReadPrice
				+ ", depositGiftReadPrice=" + depositGiftReadPrice
				+ ", relationProductId=" + relationProductId + ", creator="
				+ creator + ", creationDate=" + creationDate + ", modifier="
				+ modifier + ", lastChangedDate=" + lastChangedDate
				+ ", status=" + status + ", fromPaltform=" + fromPaltform + "]";
	}
}