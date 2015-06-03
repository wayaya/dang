package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 奖品记录主体
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午3:33:29  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class PrizeRecord implements Serializable {
	private static final long serialVersionUID = 377500084536552244L;

	private Long mediaLotteryRecordId;

    /**
     *  用户id
     */
    private Long custId;
    /**
     * 奖品id
     */
    private Long prizeId;

    /**
     * 奖品类型
     */
    private Integer prizeType;

    /**
     * 归属类型，比如福袋
     */
    private Integer vestType;

    /**
     * 奖品名称
     */
    private String prizeName;

    /**
     * 创建时间
     */
    private Date creationDate;

    public Long getMediaLotteryRecordId() {
        return mediaLotteryRecordId;
    }

    public void setMediaLotteryRecordId(Long mediaLotteryRecordId) {
        this.mediaLotteryRecordId = mediaLotteryRecordId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Long getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Long prizeId) {
        this.prizeId = prizeId;
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

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName == null ? null : prizeName.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

	@Override
	public String toString() {
		return "PrizeRecord [mediaLotteryRecordId=" + mediaLotteryRecordId
				+ ", custId=" + custId + ", prizeId=" + prizeId
				+ ", prizeType=" + prizeType + ", vestType=" + vestType
				+ ", prizeName=" + prizeName + ", creationDate=" + creationDate
				+ "]";
	}
    
    
    
}