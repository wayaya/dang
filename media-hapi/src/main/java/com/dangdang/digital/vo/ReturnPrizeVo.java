package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * Description: 与前台交互的榜单主体vo 
 * All Rights Reserved.
 * @version 1.0  2014年12月19日 下午5:29:12  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class ReturnPrizeVo implements Serializable {

	private static final long serialVersionUID = -5823459576162812918L;

	
	/**
	 * 奖品类型
	 */
	private Integer prizeType;
	/**
	 * 奖品名称
	 */
	private String prizeName;
	
	/**
	 * 奖品数量
	 */
	private Integer amount;

	/**
	 * 奖品记录id
	 */
	private String recordId;
	/**
	 * saleId
	 */
	private Long saleId;
	
	public Integer getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(Integer prizeType) {
		this.prizeType = prizeType;
	}


	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}


	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}
	
}
