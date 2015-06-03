package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dangdang.digital.model.Bought;
import com.dangdang.digital.model.ConsumerDeposit;

/**
 * 
 * Description: 用户交易信息vo
 * All Rights Reserved.
 * @version 1.0  2015年4月7日 下午3:15:31  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class UserRradeInfoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6132320515731587268L;

	/**
	 * 用户id
	 */
	private Long custId;
	
	/**
	 * 用户充值记录
	 */
	private List<ConsumerDeposit> consumerDepositList = new ArrayList<ConsumerDeposit>();
	
	/**
	 * 用户已购信息
	 */
	private List<Bought> boughtList = new ArrayList<Bought>();
	
	/**
	 * 操作结果
	 */
	private boolean result = false;
	
	/**
	 * 错误信息
	 */
	private String errorMsg;

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public List<ConsumerDeposit> getConsumerDepositList() {
		return consumerDepositList;
	}

	public void setConsumerDepositList(List<ConsumerDeposit> consumerDepositList) {
		this.consumerDepositList = consumerDepositList;
	}

	public List<Bought> getBoughtList() {
		return boughtList;
	}

	public void setBoughtList(List<Bought> boughtList) {
		this.boughtList = boughtList;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String isErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
