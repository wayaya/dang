package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Description: media 分类vo
 * All Rights Reserved.
 * @version 1.0  2014年12月25日 下午3:55:43  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public class CatetoryVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//该节点是否已解析
	private Boolean isParsed = false;
	
	public Boolean  isParsed(){
		return this.isParsed;
	}
	
	public void setParsed(Boolean isParsed){
		this.isParsed = isParsed;
	}
	
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
	
	/**
	 * 分类图片
	 */
	private String image;
	//
	private List<CatetoryMediaSaleVo>  catetoryList;
	
	//分类下面的销售主体
	private List<ReturnSaleVo>  saleList;
	
	public List<CatetoryMediaSaleVo> getCatetoryList() {
		return catetoryList;
	}
	public void setCatetoryList(List<CatetoryMediaSaleVo> catetoryList) {
		this.catetoryList = catetoryList;
	}
	
	public List<ReturnSaleVo> getSaleList() {
		return saleList;
	}
	public void setSaleList(List<ReturnSaleVo> saleList) {
		this.saleList = saleList;
	}

	
	
	public boolean isLeaf() {
		return catetoryList==null||catetoryList.size()==0;
	}
	
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
