package com.dangdang.digital.model;

import java.io.Serializable;

/**
 * Description: 精品章节与标签关联关系表
 * All Rights Reserved.
 * @version 1.0  2015年1月20日 下午4:08:02  by 代鹏（daipeng@dangdang.com）创建
 */
public class DigestLable implements Serializable{
	
	private static final long serialVersionUID = -3280658531489860760L;

	private Long id;

    private Long digestId;

    private Long signId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDigestId() {
        return digestId;
    }

    public void setDigestId(Long digestId) {
        this.digestId = digestId;
    }

    public Long getSignId() {
        return signId;
    }

    public void setSignId(Long signId) {
        this.signId = signId;
    }
}