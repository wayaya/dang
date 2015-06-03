package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.Date;

public class DSDepositPayInfoVo implements Serializable {
	/**
	   * @Fields serialVersionUID : TODO 2014年12月31日 下午12:30:51  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */

    /**
	 * 
	 */
	private static final long serialVersionUID = 5661868079248313850L;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;  
    
    /**
     * 充值金额（单位：分）
     */
    private Integer depositMoney;
    
    /**
     * 充值金铃铛（单位：个）
     */
    private Integer depositReadPrice;
    
    /**
     * 充值银铃铛（送）（单位：个）
     */
    private Integer depositGiftReadPrice;
    
    
    /**
     * 关联商品编号
     */
    private String relationProductId;
    
    /**
     * 银铃铛有效期（临时字段）
     */
    private Integer effectiveDay;
    
    /**
     * 充值编码
     */
    private String depositCode;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public Integer getEffectiveDay() {
		return effectiveDay;
	}

	public void setEffectiveDay(Integer effectiveDay) {
		this.effectiveDay = effectiveDay;
	}

	public String getDepositCode() {
		return depositCode;
	}

	public void setDepositCode(String depositCode) {
		this.depositCode = depositCode;
	}
}