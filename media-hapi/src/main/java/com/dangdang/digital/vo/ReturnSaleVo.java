package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Description: 与前台交互的销售主体vo 单品页 All Rights Reserved.
 * 
 * @version 1.0 2014年12月16日 下午5:48:23 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ReturnSaleVo implements Serializable {

	private static final long serialVersionUID = 6338658319515106494L;

	/**
	 * 销售主体主键id
	 */
	private Long saleId;

	/**
	 * 销售主体名称
	 */
	private String name;

	/**
	 * 销售主体描述
	 */
	private String desc;

	/**
	 * 销售主体类型
	 */
	private Short type;

	/**
	 * 价格
	 */
	private Long price;

	/**
	 * 销售主体封面图片地址
	 */
	private String coverPic;

	/**
	 * media列表
	 */
	List<ReturnMediaVo> mediaList;

	/**
	 * 活动价
	 */
	private Long activityPrice;

	/**
	 * 活动类型id
	 */
	private Integer activityType;

	/**
	 * 活动截止时间
	 */
	private Long activityEndTime;

	/**
	 * 是否收藏 0：未收藏，1：收藏
	 */
	private int isStore;

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

	public List<ReturnMediaVo> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<ReturnMediaVo> mediaList) {
		this.mediaList = mediaList;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public int getIsStore() {
		return isStore;
	}

	public void setIsStore(int isStore) {
		this.isStore = isStore;
	}

	public Long getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(Long activityPrice) {
		this.activityPrice = activityPrice;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public Long getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(Long activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

}
