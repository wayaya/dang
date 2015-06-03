package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * Description: 已购信息实体 All Rights Reserved.
 * 
 * @version 1.0 2014年12月25日 下午4:49:32 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class Bought implements Serializable {

	/**
	 * 新增
	 */
	public static Integer SAVE_TYPE_INSERT = 0;
	/**
	 * 更新
	 */
	public static Integer SAVE_TYPE_UPDATE = 1;

	/**
	 * 按章购买
	 */
	public static Integer WHOLE_FLAG_CHAPTER = 0;

	/**
	 * 全本购买
	 */
	public static Integer WHOLE_FLAG_MEDIA = 1;

	/**
	 * 按章购买并且有全本权限
	 */
	public static Integer WHOLE_FLAG_BOTH = 2;

	private static final long serialVersionUID = -3479073335295440091L;
	/**
	 * 主键
	 */
	private Long boughtId;
	/**
	 * mediaId
	 */
	private Long mediaId;
	/**
	 * 销售主体Id
	 */
	private Long saleId;
	/**
	 * 书标题
	 */
	private String mediaTitle;
	/**
	 * 作者笔名
	 */
	private String authorPenName;
	/**
	 * 主账户支付金额
	 */
	private Integer payMainPrice;
	/**
	 * 子账户支付金额
	 */
	private Integer paySubPrice;
	/**
	 * 用户id
	 */
	private Long custId;

	/**
	 * 全本购买标志（2：按章购买并且具有全本权限；1：全本购买，0：按章购买）
	 */
	private Integer wholeFlag;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 来源平台 PayFromPaltform
	 */
	private String fromPaltform;

	/**
	 * 已购详情列表
	 */
	private List<BoughtDetail> boughtDetailList = new ArrayList<BoughtDetail>();

	/**
	 * vo字段 新增还是保存
	 */
	private Integer saveType;

	public Long getBoughtId() {
		return boughtId;
	}

	public void setBoughtId(Long boughtId) {
		this.boughtId = boughtId;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public String getMediaTitle() {
		return mediaTitle;
	}

	public void setMediaTitle(String mediaTitle) {
		this.mediaTitle = mediaTitle;
	}

	public String getAuthorPenName() {
		return authorPenName;
	}

	public void setAuthorPenName(String authorPenName) {
		this.authorPenName = authorPenName;
	}

	public Integer getPayMainPrice() {
		return payMainPrice;
	}

	public void setPayMainPrice(Integer payMainPrice) {
		this.payMainPrice = payMainPrice;
	}

	public Integer getPaySubPrice() {
		return paySubPrice;
	}

	public void setPaySubPrice(Integer paySubPrice) {
		this.paySubPrice = paySubPrice;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Integer getWholeFlag() {
		return wholeFlag;
	}

	public void setWholeFlag(Integer wholeFlag) {
		this.wholeFlag = wholeFlag;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<BoughtDetail> getBoughtDetailList() {
		return boughtDetailList;
	}

	public void setBoughtDetailList(List<BoughtDetail> boughtDetailList) {
		this.boughtDetailList = boughtDetailList;
	}

	public Integer getSaveType() {
		return saveType;
	}

	public void setSaveType(Integer saveType) {
		this.saveType = saveType;
	}

	public String getFromPaltform() {
		return fromPaltform;
	}

	public void setFromPaltform(String fromPaltform) {
		this.fromPaltform = fromPaltform;
	}

}
