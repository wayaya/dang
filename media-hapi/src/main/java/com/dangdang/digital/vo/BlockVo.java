package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月8日 上午10:33:04  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public class BlockVo implements Serializable {
	private static final long serialVersionUID = 7960063850959890767L;
	/**
	 * 块名称
	 */
	private String blockName;

	/**
	 * 标识
	 */
	private String code;
	
	/**
     * 创建时间
     */
    private Date creationDate;

	/**
	 * 块主图
	 */
	private String picPath;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 目标URL
	 */
	private String targetUrl;

	/**
	 * 开始时间
	 */
	private Date startDate;

	/**
	 * 结束时间
	 */
	private Date endDate;

	/**
	 * 关联栏目名称
	 */
	private String relationColumnName;
	
	/**
	 * 关联栏目标示code
	 */
	private String relationColumnCode;
	
	 /**
     * 关联saleList
     */
    private List<ReturnSaleVo> saleList;

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRelationColumnName() {
		return relationColumnName;
	}

	public void setRelationColumnName(String relationColumnName) {
		this.relationColumnName = relationColumnName;
	}

	public String getRelationColumnCode() {
		return relationColumnCode;
	}

	public void setRelationColumnCode(String relationColumnCode) {
		this.relationColumnCode = relationColumnCode;
	}

	public List<ReturnSaleVo> getSaleList() {
		return saleList;
	}

	public void setSaleList(List<ReturnSaleVo> saleList) {
		this.saleList = saleList;
	}
}