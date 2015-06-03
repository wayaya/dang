package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 文集与精品关联实体
 * All Rights Reserved.
 * @version 1.0  2015年2月4日 下午5:50:21  by 代鹏（daipeng@dangdang.com）创建
 */
public class DigestAnthology implements Serializable{
	
	private static final long serialVersionUID = -3474671851633808311L;

	private Long anthologyDigestId;

    private Long digestId;

    private Long anthologyId;

    private Date createDate;
    
    //非入库字段
    /**
	 * 精品文章id
	 */
	private Long id;
	/**
	 * 精品文章备注
	 */
	private String cardRemark;
	/**
	 * 精品文章标题
	 */
	private String cardTitle;
	//卡片类型 0:全文字 1:文字加图片 2:全图
    private Integer cardType;
    /**
     * 精品卡片图片url地址
     */
    private String pic1Path;
    /**
     * 加入文集时间Long类型
     */
    private Long createTime;

    public Long getAnthologyDigestId() {
        return anthologyDigestId;
    }

    public void setAnthologyDigestId(Long anthologyDigestId) {
        this.anthologyDigestId = anthologyDigestId;
    }

    public Long getDigestId() {
        return digestId;
    }

    public void setDigestId(Long digestId) {
        this.digestId = digestId;
    }

    public Long getAnthologyId() {
        return anthologyId;
    }

    public void setAnthologyId(Long anthologyId) {
        this.anthologyId = anthologyId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardRemark() {
		return cardRemark;
	}

	public void setCardRemark(String cardRemark) {
		this.cardRemark = cardRemark;
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public void setCardTitle(String cardTitle) {
		this.cardTitle = cardTitle;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getPic1Path() {
		return pic1Path;
	}

	public void setPic1Path(String pic1Path) {
		this.pic1Path = pic1Path;
	}

	public Long getCreateTime() {
		if(createDate == null){
			return null;
		}
		createTime = createDate.getTime();
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
    
}