/**
 * Description: UserTradeBaseVo.java
 * All Rights Reserved.
 * @version 1.0  2014年12月3日 下午3:22:23  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * Description: 用户交易基础vo All Rights Reserved.
 * 
 * @version 1.0 2014年12月3日 下午3:22:23 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class UserTradeBaseVo implements Serializable {

	private static final long serialVersionUID = 9116896088784664780L;

	/**
	 * 用户id
	 */
	private Long custId;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 电子邮箱
	 */
	private String email;

	/**
	 * 介绍
	 */
	private String introduct;

	/**
	 * 用户类型
	 */
	private String custType;

	/**
	 * 用户头像
	 */
	private String custImg;

	/**
	 * 频道
	 */
	private String channelType;

	/**
	 * 是否交易成功
	 */
	private boolean tradeResult = false;
	/**
	 * 是否只允许消费主账户阅读币
	 */
	private boolean isOnlyPayMainAmount = true;
	/**
	 * 交易类型：1 充值，2 消费，3 查询
	 */
	private Integer tradeType;
	/**
	 * 需要消费金额
	 */
	private Integer requirePayAmount;
	/**
	 * 主账户实际消费金额
	 */
	private Integer payMainAmount;
	/**
	 * 副账户实际消费金额
	 */
	private Integer paySubAmount;
	/**
	 * 主账户需要充值金额
	 */
	private Integer requireRechargeMainAmount;
	/**
	 * 副账户需要充值金额
	 */
	private Integer requireRechargeSubAmount;
	/**
	 * 主账户实际充值金额
	 */
	private Integer rechargeMainAmount;
	/**
	 * 副账户实际充值金额
	 */
	private Integer rechargeSubAmount;
	/**
	 * 主账户余额
	 */
	private Integer mainBalance;
	/**
	 * 副账户余额
	 */
	private Integer subBalance;
	/**
	 * IOS主账户余额
	 */
	private Integer mainBalanceIOS;
	/**
	 * IOS副账户余额
	 */
	private Integer subBalanceIOS;		
	/**
	 * 外部订单号
	 */
	private String sourceOrderNo ;
	
	/**
	 * 消费来源--设备类型
	 */
	private String consumeSource ;
	/**
	 * 上次获取的最小消费明细ID（用于分页） （可为0）
	 */
	private Long lastConsumeItemId;
	
	/**
	 * 来源平台
	 */
	private String fromPaltform;
	
	private Integer accountGrade;// 用户级别
	private Integer accountIntegral;// 账户级别
	private String activityCode; //辅账户充值对应的活动码
	

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public boolean isTradeResult() {
		return tradeResult;
	}

	public void setTradeResult(boolean tradeResult) {
		this.tradeResult = tradeResult;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getRequirePayAmount() {
		return requirePayAmount;
	}

	public void setRequirePayAmount(Integer requirePayAmount) {
		this.requirePayAmount = requirePayAmount;
	}

	public Integer getPayMainAmount() {
		return payMainAmount;
	}

	public void setPayMainAmount(Integer payMainAmount) {
		this.payMainAmount = payMainAmount;
	}

	public Integer getPaySubAmount() {
		return paySubAmount;
	}

	public void setPaySubAmount(Integer paySubAmount) {
		this.paySubAmount = paySubAmount;
	}

	public Integer getRequireRechargeMainAmount() {
		return requireRechargeMainAmount;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRequireRechargeMainAmount(Integer requireRechargeMainAmount) {
		this.requireRechargeMainAmount = requireRechargeMainAmount;
	}

	public Integer getRequireRechargeSubAmount() {
		return requireRechargeSubAmount;
	}

	public void setRequireRechargeSubAmount(Integer requireRechargeSubAmount) {
		this.requireRechargeSubAmount = requireRechargeSubAmount;
	}

	public Integer getRechargeMainAmount() {
		return rechargeMainAmount;
	}

	public void setRechargeMainAmount(Integer rechargeMainAmount) {
		this.rechargeMainAmount = rechargeMainAmount;
	}

	public Integer getRechargeSubAmount() {
		return rechargeSubAmount;
	}

	public void setRechargeSubAmount(Integer rechargeSubAmount) {
		this.rechargeSubAmount = rechargeSubAmount;
	}

	public Integer getMainBalance() {
		return mainBalance;
	}

	public void setMainBalance(Integer mainBalance) {
		this.mainBalance = mainBalance;
	}

	public Integer getSubBalance() {
		return subBalance;
	}

	public void setSubBalance(Integer subBalance) {
		this.subBalance = subBalance;
	}

	public boolean isOnlyPayMainAmount() {
		return isOnlyPayMainAmount;
	}

	public void setOnlyPayMainAmount(boolean isOnlyPayMainAmount) {
		this.isOnlyPayMainAmount = isOnlyPayMainAmount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIntroduct() {
		return introduct;
	}

	public void setIntroduct(String introduct) {
		this.introduct = introduct;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getCustImg() {
		return custImg;
	}

	public void setCustImg(String custImg) {
		this.custImg = custImg;
	}

	public String getSourceOrderNo() {
		return sourceOrderNo;
	}

	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}

	public String getConsumeSource() {
		return consumeSource;
	}

	public void setConsumeSource(String consumeSource) {
		this.consumeSource = consumeSource;
	}

	public Long getLastConsumeItemId() {
		return lastConsumeItemId;
	}

	public void setLastConsumeItemId(Long lastConsumeItemId) {
		this.lastConsumeItemId = lastConsumeItemId;
	}

	public String getFromPaltform() {
		return fromPaltform;
	}

	public void setFromPaltform(String fromPaltform) {
		this.fromPaltform = fromPaltform;
	}

	public Integer getMainBalanceIOS() {
		return mainBalanceIOS;
	}

	public void setMainBalanceIOS(Integer mainBalanceIOS) {
		this.mainBalanceIOS = mainBalanceIOS;
	}

	public Integer getSubBalanceIOS() {
		return subBalanceIOS;
	}

	public void setSubBalanceIOS(Integer subBalanceIOS) {
		this.subBalanceIOS = subBalanceIOS;
	}

	public Integer getAccountGrade() {
		return accountGrade;
	}

	public void setAccountGrade(Integer accountGrade) {
		this.accountGrade = accountGrade;
	}

	public Integer getAccountIntegral() {
		return accountIntegral;
	}

	public void setAccountIntegral(Integer accountIntegral) {
		this.accountIntegral = accountIntegral;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

}
