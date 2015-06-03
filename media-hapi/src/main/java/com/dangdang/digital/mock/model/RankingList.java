package com.dangdang.digital.mock.model;

import java.io.Serializable;

/**
 * 
 * @author lvxiang
 * @date   2015年5月15日 下午5:06:48
 * 榜单基本信息
 */
public class RankingList implements Serializable {
	
	/** 榜单代码 **/
	private String listCode;
	
	/** 榜单名称  **/
	private String listName;
	
	
	public String getListCode() {
		return listCode;
	}

	public void setListCode(String listCode) {
		this.listCode = listCode;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	
	
}
