package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 精品文集实体
 * All Rights Reserved.
 * @version 1.0  2015年2月4日 下午4:40:11  by 代鹏（daipeng@dangdang.com）创建
 */
public class Anthology implements Serializable{
	
	private static final long serialVersionUID = 7825953259411033291L;

	private Long anthologyId;

    private String anthologyName;

    private String anthologyPic;

    private Long custId;

    private String custName;

    private Date createDate;

    private String platform;
    
    //创建时间Long型
    private Long createTime;

    public Long getCreateTime() {
    	if(createDate == null){
			return null;
		}
		createTime = this.createDate.getTime();
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getAnthologyId() {
        return anthologyId;
    }

    public void setAnthologyId(Long anthologyId) {
        this.anthologyId = anthologyId;
    }

    public String getAnthologyName() {
        return anthologyName;
    }

    public void setAnthologyName(String anthologyName) {
        this.anthologyName = anthologyName == null ? null : anthologyName.trim();
    }

    public String getAnthologyPic() {
        return anthologyPic;
    }

    public void setAnthologyPic(String anthologyPic) {
        this.anthologyPic = anthologyPic == null ? null : anthologyPic.trim();
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }
}