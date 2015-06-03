/**
 * Description: UserAuthorityCacheVo.java
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午12:03:53  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午12:03:53  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class UserAuthorityCacheVo implements Serializable {

	/**
	   * @Fields serialVersionUID : TODO 2014年12月15日 下午12:04:03  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = 4654973744438222783L;
	
    /**
     * 用户id
     */
    private Long custId;

    /**
     * 媒体类型 
     */
    private Short mediaType;

    /**
     * 权限类型 Contans.USER_AUTHORITY_
     */
    private Short authorityType;

    /**
     * 电子书id
     */
    private Long mediaId;
    
    private Map<Long,Long> chapterIdsMap;
	
    /**
     * 最后修改时间
     */
    private Date lastModifiedDate;
    
    private Integer payMainPrice;

    private Integer paySubPrice;

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

	public Map<Long, Long> getChapterIdsMap() {
		return chapterIdsMap;
	}

	public void setChapterIdsMap(Map<Long, Long> chapterIdsMap) {
		this.chapterIdsMap = chapterIdsMap;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
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
