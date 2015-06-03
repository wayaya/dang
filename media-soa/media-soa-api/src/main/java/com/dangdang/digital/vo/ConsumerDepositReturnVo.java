/**
 * Description: ConsumerDepositReturnVo.java
 * All Rights Reserved.
 * @version 1.0  2014年12月30日 下午5:33:16  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月30日 下午5:33:16  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class ConsumerDepositReturnVo implements Serializable {
	
	/**
	   * @Fields serialVersionUID : TODO 2014年12月30日 下午5:34:11  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = 6299844199710788292L;
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
     * 充值方式
     */
    private String payment;
    /**
     * 充值金额
     */
    private Integer money;
    /**
     * 主账户阅读币数量
     */
    private Integer mainGold;
    /**
     * 赠送副账户阅读币数量
     */
    private Integer subGold;
    /**
     * 充值时间
     */
    private Long payTime;
    /**
     * 充值记录创建时间
     */
    private Long creationDate;
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
		this.depositNo = depositNo;
	}
	public String getDepositOrderNo() {
		return depositOrderNo;
	}
	public void setDepositOrderNo(String depositOrderNo) {
		this.depositOrderNo = depositOrderNo;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
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
	public Long getPayTime() {
		return payTime;
	}
	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}
	public Long getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Long creationDate) {
		this.creationDate = creationDate;
	}


}
