package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * Description: 销售主体缓存vo All Rights Reserved.
 * 
 * @version 1.0 2014年12月8日 下午2:39:03 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class MediaSaleCacheVo implements Serializable {

	private static final long serialVersionUID = 6703197263085949577L;

	private Long saleId;

	private String name;

	private String desc;

	private Short type;

	private Long price;

	private String creator;

	private Date creationDate;

	private String modifier;

	private Date lastModifiedDate;

	private String coverPic;
	/**
	 * 上下架状态 0:下架   1:上架
	 */
	private Integer shelfStatus;
	
	/**
	 * 是否支持全本购买 0:否 1:是
	 */
	private Integer isSupportFullBuy;
	
	/**
	 * 是否完结：0 否 1是
	 */
	private Integer isFull;
	
	private List<MediaCacheWholeVo> mediaList;

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
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
		this.creator = creator;
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
		this.modifier = modifier;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public List<MediaCacheWholeVo> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<MediaCacheWholeVo> mediaList) {
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

	public Integer getIsFull() {
		return isFull;
	}

	public void setIsFull(Integer isFull) {
		this.isFull = isFull;
	}
	
}
