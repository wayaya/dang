package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description: 道具实体
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午4:03:14  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class Prop implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5701136968031405008L;
	/**
	 * 道具id
	 */
    private Integer propId;
	/**
	 * 道具名称
	 */
    private String propName;
	/**
	 * 主账户阅读币购买价格
	 */
    private Integer propMainGoldPrice;
	/**
	 * 副账户阅读币购买价格
	 */
    private Integer propSubGoldPrice;
	/**
	 * 道具功能id
	 */
    private Integer propPurposeId;
	/**
	 * 道具功能描述
	 */
    private String propPurposeDesc;
	/**
	 * 创建时间
	 */
    private Date creationDate;
	/**
	 * 创建人
	 */
    private String creator;
	/**
	 * 最后修改时间
	 */
    private Date lastModifiedDate;
	/**
	 * 最后修改人
	 */
    private String modifier;
    
    /**
     * 封面图
     */
    private String coverPic;

    public Integer getPropId() {
        return propId;
    }

    public void setPropId(Integer propId) {
        this.propId = propId;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public Integer getPropMainGoldPrice() {
        return propMainGoldPrice;
    }

    public void setPropMainGoldPrice(Integer propMainGoldPrice) {
        this.propMainGoldPrice = propMainGoldPrice;
    }

    public Integer getPropSubGoldPrice() {
        return propSubGoldPrice;
    }

    public void setPropSubGoldPrice(Integer propSubGoldPrice) {
        this.propSubGoldPrice = propSubGoldPrice;
    }

    public Integer getPropPurposeId() {
        return propPurposeId;
    }

    public void setPropPurposeId(Integer propPurposeId) {
        this.propPurposeId = propPurposeId;
    }

    public String getPropPurposeDesc() {
        return propPurposeDesc;
    }

    public void setPropPurposeDesc(String propPurposeDesc) {
        this.propPurposeDesc = propPurposeDesc == null ? null : propPurposeDesc.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}
}