package com.dangdang.digital.mock.model;

import java.io.Serializable;

/**
 * 
 * @author lvxiang
 * @date   2015年5月15日 下午3:59:21
 * 栏目信息
 */
public class Column implements Serializable {
	
	/** 栏目类型  **/
	private Integer type;
	
	private String columnCode;
	
	private String title;
	
	/** 描述信息  **/
	private String description;
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/** 该栏目下书的总数  **/
	private Integer total;
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
}
