package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 
 * Description: 用户阅读权限
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 下午3:07:12  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class UserAuthority implements Serializable{
	/**
	   * @Fields serialVersionUID : TODO 2014年12月12日 下午3:11:41  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = -3735959676481337706L;
	/**
	 * 用户权限id
	 */
    private Long userAuthorityId;
    /**
     * 用户id
     */
    private Long custId;

    /**
     * 媒体类型
     */
    private Short mediaType;

    /**
     * 权限类型
     */
    private Short authorityType;

    /**
     * 电子书id
     */
    private Long mediaId;

    /**
     * 权限开始时间
     */
    private Date authorityStart;

    /**
     * 权限结束时间
     */
    private Date authorityEnd;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 最后修改时间
     */
    private Date lastModifiedDate;
    
    private Integer payMainPrice;

    private Integer paySubPrice;
    
    private List<UserAuthorityDetail> userAuthorityDetails = new ArrayList<UserAuthorityDetail>();

    public Long getUserAuthorityId() {
        return userAuthorityId;
    }

    public void setUserAuthorityId(Long userAuthorityId) {
        this.userAuthorityId = userAuthorityId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Short getMediaType() {
        return mediaType;
    }

    public void setMediaType(Short mediaType) {
        this.mediaType = mediaType;
    }

    public Short getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(Short authorityType) {
        this.authorityType = authorityType;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Date getAuthorityStart() {
        return authorityStart;
    }

    public void setAuthorityStart(Date authorityStart) {
        this.authorityStart = authorityStart;
    }

    public Date getAuthorityEnd() {
        return authorityEnd;
    }

    public void setAuthorityEnd(Date authorityEnd) {
        this.authorityEnd = authorityEnd;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

	public List<UserAuthorityDetail> getUserAuthorityDetails() {
		return userAuthorityDetails;
	}

	public void setUserAuthorityDetails(
			List<UserAuthorityDetail> userAuthorityDetails) {
		this.userAuthorityDetails = userAuthorityDetails;
	}

	public Integer getPayMainPrice() {
		return payMainPrice;
	}

	public void setPayMainPrice(Integer payMainPrice) {
		this.payMainPrice = payMainPrice;
	}

	public Integer getPaySubPrice() {
		return paySubPrice;
	}

	public void setPaySubPrice(Integer paySubPrice) {
		this.paySubPrice = paySubPrice;
	}
}