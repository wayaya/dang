package com.dangdang.digital.vo;

import java.util.Date;

/**
 * Description: 专题bean All Rights Reserved.
 * 
 * @version 1.0 2015年1月8日 下午6:34:46 by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public class ReturnSpecialTopicVo {
	
	/**
	 * ID
	 */
	private Integer stId;
	
	/**
	 * 专题
	 */
	private String name;

	/**
	 * 专题主图
	 */
	private String picPath;

	/**
	 * 创建时间
	 */
	private Date creationDate;

	/**
	 * 修改时间
	 */
	private Date lastModifiedDate;

	public Integer getStId() {
		return stId;
	}

	public void setStId(Integer stId) {
		this.stId = stId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}