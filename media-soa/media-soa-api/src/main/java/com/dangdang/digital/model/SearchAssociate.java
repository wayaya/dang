package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: All Rights Reserved.
 * 
 * @version 1.0 2014年12月27日 下午3:42:42 by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public class SearchAssociate implements Serializable {
	private static final long serialVersionUID = 1896081532656156501L;

	/**
	 * 主键ID
	 */
	private Long asId;

	/**
	 * 来源平台(当读小说：DDXS-P; 当当读书：DDDS-P)
	 */
	private String searchSource;

	/**
	 * 关键字名称
	 */
	private String keyword;
	
	/**
	 * 关键字拼音
	 */
	private String pinyin;

	/**
	 * 大约结果数
	 */
	private Integer count;

	/**
	 * 创建时间
	 */
	private Date creationDate;

	/**
	 * 最后更新时间
	 */
	private Date lastModifyDate;

	public Long getAsId() {
		return asId;
	}

	public void setAsId(Long asId) {
		this.asId = asId;
	}

	public String getsearchSource() {
		return searchSource;
	}

	public void setsearchSource(String searchSource) {
		this.searchSource = searchSource == null ? null : searchSource.trim();
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword == null ? null : keyword.trim();
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getSearchSource() {
		return searchSource;
	}

	public void setSearchSource(String searchSource) {
		this.searchSource = searchSource;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
}