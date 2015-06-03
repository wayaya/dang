package com.dangdang.digital.vo;

import java.io.Serializable;

import com.dangdang.base.account.client.commons.enums.AccountTypeEnum;
import com.dangdang.base.account.client.commons.enums.ConsumeTypeEnum;
import com.dangdang.base.account.client.commons.enums.CostTypeEnum;
import com.dangdang.base.account.client.commons.enums.PlatformNoEnum;

/**
 * Description:红包的传递参数vo 
 * All Rights Reserved.
 * @version 1.0  2015年1月4日 上午11:22:57  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class ParamRedRocketVo implements Serializable{
	
	private static final long serialVersionUID = -7203729143328516737L;
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 客户ID
	 */
	private Long custId ;
	/**
	 * 用户名称
	 */
	private String userName ;
	/**
	 * 消费金额
	 */
	private Integer consumeMoney ;
	/**
	 * 账户类型
	 * @see AccountTypeEnum
	 */
	private String accountType ;
	/**
	 * 消费类型
	 * @see ConsumeTypeEnum
	 */
	private String consumeType ;
	/**
	 * 扣费类型
	 * @see CostTypeEnum
	 */
	private String costType ;
	/**
	 * 消费来源--设备类型
	 */
	private String consumeSource ;
	/**
	 * 平台编号
	 * @see PlatformNoEnum
	 */
	private String platformNo ;
	/**
	 * 外部订单号
	 */
	private String sourceOrderNo ;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getConsumeMoney() {
		return consumeMoney;
	}
	public void setConsumeMoney(Integer consumeMoney) {
		this.consumeMoney = consumeMoney;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getConsumeType() {
		return consumeType;
	}
	public void setConsumeType(String consumeType) {
		this.consumeType = consumeType;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public String getConsumeSource() {
		return consumeSource;
	}
	public void setConsumeSource(String consumeSource) {
		this.consumeSource = consumeSource;
	}
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public String getSourceOrderNo() {
		return sourceOrderNo;
	}
	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}
	
	
}
