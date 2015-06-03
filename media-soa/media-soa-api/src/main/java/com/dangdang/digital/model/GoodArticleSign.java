package com.dangdang.digital.model;

import java.io.Serializable;

/**
 * 当心好文标签
 * @author Administrator
 *
 */
public class GoodArticleSign implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 父节点ID
	 */
	private Integer parentId;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 父节点名称
	 */
	private Integer parentName;
	
	
	private String path;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getParentName() {
		return parentName;
	}
	public void setParentName(Integer parentName) {
		this.parentName = parentName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
