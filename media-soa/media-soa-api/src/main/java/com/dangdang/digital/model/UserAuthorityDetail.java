package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description: 用户阅读权限详情
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 下午3:07:33  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class UserAuthorityDetail implements Serializable{
	/**
	   * @Fields serialVersionUID : TODO 2014年12月12日 下午3:11:50  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = -2084711977430490481L;

	/**
	 * 用户权限详情id
	 */
    private Long userAuthorityDetailId;

    /**
     * 用户权限id
     */
    private Long userAuthorityId;

    /**
     * 电子书章节id
     */
    private Long mediaChapterId;

    /**
     * 创建时间
     */
    private Date creationDate;
    
    private Long mediaId;

    private Long custId;

    public Long getUserAuthorityDetailId() {
        return userAuthorityDetailId;
    }

    public void setUserAuthorityDetailId(Long userAuthorityDetailId) {
        this.userAuthorityDetailId = userAuthorityDetailId;
    }

    public Long getUserAuthorityId() {
        return userAuthorityId;
    }

    public void setUserAuthorityId(Long userAuthorityId) {
        this.userAuthorityId = userAuthorityId;
    }

    public Long getMediaChapterId() {
        return mediaChapterId;
    }

    public void setMediaChapterId(Long mediaChapterId) {
        this.mediaChapterId = mediaChapterId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}
}