package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * 
 * Description: 返回chapter 目录  vo All Rights Reserved.
 * 
 * @version 1.0 2014年12月17日 下午2:36:09 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ChapterContentsVo implements Serializable {

	private static final long serialVersionUID = 372024751563777321L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 章节名
	 */
	private String title;

	/**
	 * 子章节名
	 */
	private String subTitle;

	/**
	 * 顺序
	 */
	private Integer index;

	/**
	 * 是否免费（0：否 1：是）
	 */
	private String isFree;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

}
