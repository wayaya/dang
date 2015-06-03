package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 奖品信息主体
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午3:33:02  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class Prize implements Serializable {
	private static final long serialVersionUID = -3755102915957515040L;

	private Long mediaLotteryPrizeId;

    /**
     * 奖品名称
     */
    private String prizeName;

    /**
     * 奖品介绍
     */
    private String introduce;


    /**
     * 概率小数值0--1
     */
    private Double probability ;

    /**
     * 每天的发放上限
     */
    private Integer dayLimit ;

    /**
     * 总计的发放上限
     */
    private Integer totalLimit ;

    /**
     * 预留字段，奖品类型
     */
    private Integer prizeType;

    /**
     * 该奖品的id，如果是道具就是道具id，如归是图书就是图书id，如果是金币，填写0'
     */
    private Long prizeId;

    /**
     * 奖品数量，如果是图书就是前n章'
     */
    private Integer amount;
    /**
     * 归属类型，比如福袋
     */
    private Integer vestType;

    /**
     * 生效时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;
    
	/**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 更新时间
     */
    private Date lastModifiedDate;

    /**
     * 创始人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    public Long getMediaLotteryPrizeId() {
        return mediaLotteryPrizeId;
    }

    public void setMediaLotteryPrizeId(Long mediaLotteryPrizeId) {
        this.mediaLotteryPrizeId = mediaLotteryPrizeId;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName == null ? null : prizeName.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }



    public Long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}


    public Integer getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(Integer dayLimit) {
        this.dayLimit = dayLimit;
    }

    public Integer getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(Integer totalLimit) {
        this.totalLimit = totalLimit;
    }

    public Integer getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(Integer prizeType) {
        this.prizeType = prizeType;
    }

    public Integer getVestType() {
        return vestType;
    }

    public void setVestType(Integer vestType) {
        this.vestType = vestType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    
    public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreator() {
        return creator;
    }

    public Double getProbability() {
		return probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
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

	@Override
	public String toString() {
		return "Prize [mediaLotteryPrizeId=" + mediaLotteryPrizeId
				+ ", prizeName=" + prizeName + ", introduce=" + introduce
				+ ", probability=" + probability + ", dayLimit=" + dayLimit
				+ ", totalLimit=" + totalLimit + ", prizeType=" + prizeType
				+ ", prizeId=" + prizeId + ", amount=" + amount + ", vestType="
				+ vestType + ", startDate=" + startDate + ", endDate="
				+ endDate + ", creationDate=" + creationDate
				+ ", lastModifiedDate=" + lastModifiedDate + ", creator="
				+ creator + ", modifier=" + modifier + "]";
	}
    
    
    
}