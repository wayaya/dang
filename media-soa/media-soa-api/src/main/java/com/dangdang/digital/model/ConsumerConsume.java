package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * Description: 用户消费记录实体
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午3:15:57  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class ConsumerConsume implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9010207052168331393L;
    private Long consumerConsumeId;

    private Long custId;

    private String userName;
    
    private Short consumeTpye;

    private Short isFinalEstimate;

    private Integer totalPrice;

    private Integer prePrice;

    private Integer mainGoldUsed;

    private Integer subGoldUsed;

    private String activeId;

    private String consumeSerialNo;

    private Integer monthlyId;
    
    private Integer luckyBagId;

    private Date monthlyStartTime;

    private Date monthlyEndTime;

    private Date creationDate;

    private Date payTime;
    
    private String commodityName;
    private String fromPaltform;
	private Short supportBalance;
    
    private List<ConsumerConsumeDetail> consumeDetails = new ArrayList<ConsumerConsumeDetail>(0);

    public Long getConsumerConsumeId() {
        return consumerConsumeId;
    }

    public void setConsumerConsumeId(Long consumerConsumeId) {
        this.consumerConsumeId = consumerConsumeId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Short getConsumeTpye() {
        return consumeTpye;
    }

    public void setConsumeTpye(Short consumeTpye) {
        this.consumeTpye = consumeTpye;
    }

    public Short getIsFinalEstimate() {
        return isFinalEstimate;
    }

    public void setIsFinalEstimate(Short isFinalEstimate) {
        this.isFinalEstimate = isFinalEstimate;
    }

    public Integer getMainGoldUsed() {
        return mainGoldUsed;
    }

    public void setMainGoldUsed(Integer mainGoldUsed) {
        this.mainGoldUsed = mainGoldUsed;
    }

    public Integer getSubGoldUsed() {
        return subGoldUsed;
    }

    public void setSubGoldUsed(Integer subGoldUsed) {
        this.subGoldUsed = subGoldUsed;
    }

    public String getConsumeSerialNo() {
        return consumeSerialNo;
    }

    public void setConsumeSerialNo(String consumeSerialNo) {
        this.consumeSerialNo = consumeSerialNo == null ? null : consumeSerialNo.trim();
    }

    public Integer getMonthlyId() {
        return monthlyId;
    }

    public void setMonthlyId(Integer monthlyId) {
        this.monthlyId = monthlyId;
    }

    public Date getMonthlyStartTime() {
        return monthlyStartTime;
    }

    public void setMonthlyStartTime(Date monthlyStartTime) {
        this.monthlyStartTime = monthlyStartTime;
    }

    public Date getMonthlyEndTime() {
        return monthlyEndTime;
    }

    public void setMonthlyEndTime(Date monthlyEndTime) {
        this.monthlyEndTime = monthlyEndTime;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

	public List<ConsumerConsumeDetail> getConsumeDetails() {
		return consumeDetails;
	}

	public void setConsumeDetails(List<ConsumerConsumeDetail> consumeDetails) {
		this.consumeDetails = consumeDetails;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getPrePrice() {
		return prePrice;
	}

	public void setPrePrice(Integer prePrice) {
		this.prePrice = prePrice;
	}

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getLuckyBagId() {
		return luckyBagId;
	}

	public void setLuckyBagId(Integer luckyBagId) {
		this.luckyBagId = luckyBagId;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFromPaltform() {
		return fromPaltform;
	}

	public void setFromPaltform(String fromPaltform) {
		this.fromPaltform = fromPaltform;
	}

	public Short getSupportBalance() {
		return supportBalance;
	}

	public void setSupportBalance(Short supportBalance) {
		this.supportBalance = supportBalance;
	}

}