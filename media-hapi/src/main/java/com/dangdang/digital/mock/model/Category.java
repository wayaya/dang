package com.dangdang.digital.mock.model;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author lvxiang
 * @date   2015年5月16日 上午10:41:48
 * 书藉分类(非叶子)节点
 */
public class Category implements Serializable{

	private String categoryName;
	
	private String cagegoryCode;
	
	/** 快速检索字母 **/
	private String indexKey;
	
	
	/** 字节点集合,若没有,则为空   **/
	private List<Category> categoryList;
	
	private Integer order;
	
	public Integer getOrder() {
		return order;
	}


	public void setOrder(Integer order) {
		this.order = order;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getCagegoryCode() {
		return cagegoryCode;
	}


	public void setCagegoryCode(String cagegoryCode) {
		this.cagegoryCode = cagegoryCode;
	}


	public String getIndexKey() {
		return indexKey;
	}


	public void setIndexKey(String indexKey) {
		this.indexKey = indexKey;
	}


	public List<Category> getCategoryList() {
		return categoryList;
	}


	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}


}
