package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MediaSale implements Serializable {
	
	private static final long serialVersionUID = -1548488429629518258L;

	private Long saleId;

	private String name;

	private String desc;

	private Short type;

	private Long price;

	private String creator;

	private Date creationDate;

	private String modifier;

	private Date lastModifiedDate;

	private Short isSupportSubscribe;
	
	private String coverPic;
	/**
	 * 上下架状态 0:下架 1:上架
	 */
	private Integer shelfStatus;
	/**
	 * 是否支持全本购买 0:否 1:是
	 */
	private Integer isSupportFullBuy;
	
	private Integer isFull;
	
	/**
	 * mediaId
	 */
	private Long pId;
	

	/**
	 * 销售实体关联信息
	 */
	private List<MediaRelation> mediaRelations = new ArrayList<MediaRelation>(0);

	/**
	 * media集合，用于设置销售主体缓存
	 */
	private List<Media> mediaList;

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc == null ? null : desc.trim();
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator == null ? null : creator.trim();
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Short getIsSupportSubscribe() {
		return isSupportSubscribe;
	}

	public void setIsSupportSubscribe(Short isSupportSubscribe) {
		this.isSupportSubscribe = isSupportSubscribe;
	}

	public List<MediaRelation> getMediaRelations() {
		return mediaRelations;
	}

	public void setMediaRelations(List<MediaRelation> mediaRelations) {
		this.mediaRelations = mediaRelations;
	}

	public List<Media> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<Media> mediaList) {
		this.mediaList = mediaList;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public Integer getShelfStatus() {
		return shelfStatus;
	}

	public void setShelfStatus(Integer shelfStatus) {
		this.shelfStatus = shelfStatus;
	}

	public Integer getIsSupportFullBuy() {
		return isSupportFullBuy;
	}

	public void setIsSupportFullBuy(Integer isSupportFullBuy) {
		this.isSupportFullBuy = isSupportFullBuy;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public Integer getIsFull() {
		return isFull;
	}

	public void setIsFull(Integer isFull) {
		this.isFull = isFull;
	}
	
}