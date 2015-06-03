package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月22日 下午3:42:22  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class UserMonthly implements Serializable {
    /**
	   * @Fields serialVersionUID : TODO 2014年12月22日 下午5:32:28  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = -1803510158414836233L;

	private Long userMonthlyId;

    private Long custId;

    private String monthlyPaymentRelation;

    private Short monthlyType;

    private Date monthlyStartTime;
    /**
     * 包月结束时间
     */
    private Date monthlyEndTime;

    private Integer operateVersion;
	/**
	 * 是否首次登陆送包月 1：是 0：否
	 */
    private Short firstLoginGiving;
    /**
	 * 是否首次分享送包月 1：是 0：否
	 */
    private Short firstShareGiving;   
    /**
	 * 首次登陆送包月天数
	 */
    private Integer firstLoginGivingDays;
    /**
     * 首次分享送包月天数
     */
    private Integer firstShareGivingDays;
    
    /**
     * 限时分享送包月天数
     */
    private Integer timeShareGivingDays;
    
    private Date shareGivingTime;
    
    //临时字段
    private int monthlyDays;

    public Long getUserMonthlyId() {
        return userMonthlyId;
    }

    public void setUserMonthlyId(Long userMonthlyId) {
        this.userMonthlyId = userMonthlyId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getMonthlyPaymentRelation() {
        return monthlyPaymentRelation;
    }

    public void setMonthlyPaymentRelation(String monthlyPaymentRelation) {
        this.monthlyPaymentRelation = monthlyPaymentRelation == null ? null : monthlyPaymentRelation.trim();
    }

    public Short getMonthlyType() {
        return monthlyType;
    }

    public void setMonthlyType(Short monthlyType) {
        this.monthlyType = monthlyType;
    }

    public Date getMonthlyStartTime() {
        return monthlyStartTime;
    }

    public void setMonthlyStartTime(Date monthlyStartTime) {
        this.monthlyStartTime = monthlyStartTime;
    }

    public Date getMonthlyEndTime() {
        return monthlyEndTime;
    }

    public void setMonthlyEndTime(Date monthlyEndTime) {
        this.monthlyEndTime = monthlyEndTime;
    }

    public Integer getOperateVersion() {
        return operateVersion;
    }

    public void setOperateVersion(Integer operateVersion) {
        this.operateVersion = operateVersion;
    }

	public Short getFirstLoginGiving() {
		return firstLoginGiving;
	}

	public void setFirstLoginGiving(Short firstLoginGiving) {
		this.firstLoginGiving = firstLoginGiving;
	}

	public Short getFirstShareGiving() {
		return firstShareGiving;
	}

	public void setFirstShareGiving(Short firstShareGiving) {
		this.firstShareGiving = firstShareGiving;
	}

	public Integer getFirstLoginGivingDays() {
		return firstLoginGivingDays;
	}

	public void setFirstLoginGivingDays(Integer firstLoginGivingDays) {
		this.firstLoginGivingDays = firstLoginGivingDays;
	}

	public Integer getFirstShareGivingDays() {
		return firstShareGivingDays;
	}

	public void setFirstShareGivingDays(Integer firstShareGivingDays) {
		this.firstShareGivingDays = firstShareGivingDays;
	}

	public int getMonthlyDays() {
		return monthlyDays;
	}

	public void setMonthlyDays(int monthlyDays) {
		this.monthlyDays = monthlyDays;
	}

	public Date getShareGivingTime() {
		return shareGivingTime;
	}

	public void setShareGivingTime(Date shareGivingTime) {
		this.shareGivingTime = shareGivingTime;
	}

	public Integer getTimeShareGivingDays() {
		return timeShareGivingDays;
	}

	public void setTimeShareGivingDays(Integer timeShareGivingDays) {
		this.timeShareGivingDays = timeShareGivingDays;
	}
    
}