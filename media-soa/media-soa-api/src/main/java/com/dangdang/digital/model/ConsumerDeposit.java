package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description: 用户充值记录表
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午2:27:27  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class ConsumerDeposit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7604101385427226864L;
	/**
	 * 用户充值记录id
	 */
    private Long consumerDepositId;
    /**
     * 用户custId
     */
    private Long custId;
    /**
     * 充值号
     */
    private String depositNo;
    /**
     * 充值订单号
     */
    private String depositOrderNo;
    /**
     * 是否已完成支付		
     */
    private Short isPaid;
    /**
     * 充值方式
     */
    private String payment;
    /**
     * 支付虚拟商品id
     */
    private String relationProductId;
    /**
     * 充值金额
     */
    private Integer money;
    /**
     * 主账户阅读币数量
     */
    private Integer mainGold;
    /**
     * 赠送主账户阅读币数量
     */
    private Integer giveMainGold;
    /**
     * 赠送副账户阅读币数量
     */
    private Integer subGold;
    /**
     * 参加活动id
     */
    private String activeId;
    /**
     * 参加活动名称
     */
    private String activeName;
    /**
     * 人民币兑换主账户阅读币比例，100代表（1比100）
     */
    private Integer exchangeRatio;
    /**
     * 充值流水号
     */
    private String depositSerialNo;
    /**
     * 充值时间
     */
    private Date payTime;
    /**
     * 充值记录创建时间
     */
    private Date creationDate;
    /**
     * 设备类型
     */
    private String deviceType;
    
    private String userName;
    
    private Date lastModifiedDate;
    
    /**
	 * 来源平台 PayFromPaltform
	 */
    private String fromPaltform;
    
    /**
     * 虚拟商品数量
     */
    private Integer productCount;
    
    /**
     * 是否沙盒支付
     */
    private Short isSandbox;
    
    //临时属性
    
    /**
     * 创建开始时间
     */
    private String creationDateStart;
    /**
     * 创建结束时间
     */
    private String creationDateEnd;
    /**
     * 支付开始时间
     */
    private String payTimeStart;
    /**
     * 支付结束时间
     */
    private String payTimeEnd;

    public Long getConsumerDepositId() {
        return consumerDepositId;
    }

    public void setConsumerDepositId(Long consumerDepositId) {
        this.consumerDepositId = consumerDepositId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getDepositNo() {
        return depositNo;
    }

    public void setDepositNo(String depositNo) {
        this.depositNo = depositNo == null ? null : depositNo.trim();
    }

    public String getDepositOrderNo() {
        return depositOrderNo;
    }

    public void setDepositOrderNo(String depositOrderNo) {
        this.depositOrderNo = depositOrderNo == null ? null : depositOrderNo.trim();
    }

    public Short getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Short isPaid) {
        this.isPaid = isPaid;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment == null ? null : payment.trim();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getMainGold() {
        return mainGold;
    }

    public void setMainGold(Integer mainGold) {
        this.mainGold = mainGold;
    }

    public Integer getSubGold() {
        return subGold;
    }

    public void setSubGold(Integer subGold) {
        this.subGold = subGold;
    }

    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId;
    }

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName == null ? null : activeName.trim();
    }

    public Integer getExchangeRatio() {
        return exchangeRatio;
    }

    public void setExchangeRatio(Integer exchangeRatio) {
        this.exchangeRatio = exchangeRatio;
    }

    public String getDepositSerialNo() {
        return depositSerialNo;
    }

    public void setDepositSerialNo(String depositSerialNo) {
        this.depositSerialNo = depositSerialNo == null ? null : depositSerialNo.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
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

	public String getPayTimeStart() {
		return payTimeStart;
	}

	public void setPayTimeStart(String payTimeStart) {
		this.payTimeStart = payTimeStart;
	}

	public String getPayTimeEnd() {
		return payTimeEnd;
	}

	public void setPayTimeEnd(String payTimeEnd) {
		this.payTimeEnd = payTimeEnd;
	}

	public String getRelationProductId() {
		return relationProductId;
	}

	public void setRelationProductId(String relationProductId) {
		this.relationProductId = relationProductId;
	}

	public String getFromPaltform() {
		return fromPaltform;
	}

	public void setFromPaltform(String fromPaltform) {
		this.fromPaltform = fromPaltform;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Integer getGiveMainGold() {
		return giveMainGold;
	}

	public void setGiveMainGold(Integer giveMainGold) {
		this.giveMainGold = giveMainGold;
	}

	public Short getIsSandbox() {
		return isSandbox;
	}

	public void setIsSandbox(Short isSandbox) {
		this.isSandbox = isSandbox;
	}

    
}