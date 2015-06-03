package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class RedPacketUser implements Serializable {

	private static final long serialVersionUID = 4700719195344004649L;

	private Long mediaRedPacketUserId;

    private Long custId;

    /**
     * 对应的奖品记录表的主键id
     */
    private Long prizeId;
    /**
     * 红包总数
     */
    private Integer amount;

    /**
     * 已被领的钱数
     */
    private Integer putCons;

    /**
     * 剩余的数量
     */
    private Integer leftAmount;

    private Date creationDate;
    public RedPacketUser(){};
    
    public RedPacketUser(Long custId,Integer amount,Integer putCons,Integer leftAmount,Date creationDate){
    	this.custId = custId;
    	this.amount = amount;
    	this.putCons = putCons;
    	this.leftAmount = leftAmount;
    	this.creationDate = creationDate;    	
    };
    

    public Long getMediaRedPacketUserId() {
        return mediaRedPacketUserId;
    }

    public void setMediaRedPacketUserId(Long mediaRedPacketUserId) {
        this.mediaRedPacketUserId = mediaRedPacketUserId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPutCons() {
        return putCons;
    }

    public void setPutCons(Integer putCons) {
        this.putCons = putCons;
    }

    public Integer getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(Integer leftAmount) {
        this.leftAmount = leftAmount;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

	public Long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}
    
}