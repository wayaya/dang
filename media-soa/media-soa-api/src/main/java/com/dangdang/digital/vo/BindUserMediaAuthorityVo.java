/**
 * Description: BindUserMediaAuthorityVo.java
 * All Rights Reserved.
 * @version 1.0  2014年12月18日 下午2:39:27  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description: 用户权限绑定vo
 * All Rights Reserved.
 * @version 1.0  2014年12月18日 下午2:39:27  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class BindUserMediaAuthorityVo implements Serializable {

	/**
	   * @Fields serialVersionUID : TODO 2014年12月18日 下午2:39:48  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = -6063458979556934209L;
	/**
	 * 用户id
	 */
	private Long custId;
	/**
	 * 电子书id
	 */
	private Long mediaId;
	/**
	 * 章节id
	 */
	private List<Long> chapterIds;
	/**
	 * 章节数量
	 */
	private Integer chapterNum;
	/**
	 * 包月id
	 */
	private Integer monthlyId;
	/**
	 * 用户权限类型：1 电子书全本权限，2 电子书章节权限，3 电子书借阅权限，4 包月权限
	 */
	private Short userAuthorityType;
    private Integer payMainPrice;

    private Integer paySubPrice;
    /**
     * 是否首次登陆送包月 1：是 0：否
     */
    private Short firstLoginGiving; 
    /**
     * 是否首次分享送包月 1：是 0：否
     */
    private Short firstShareGiving; 
    /**
     * 参加限时分享送包月活动的时间
     */
    private Date shareGivingTime;
    
    private Date shareGivingTimeStart;
    
    private Date shareGivingTimeEnd;
    
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public Long getMediaId() {
		return mediaId;
	}
	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}
	public List<Long> getChapterIds() {
		return chapterIds;
	}
	public void setChapterIds(List<Long> chapterIds) {
		this.chapterIds = chapterIds;
	}

	public Integer getChapterNum() {
		return chapterNum;
	}
	public void setChapterNum(Integer chapterNum) {
		this.chapterNum = chapterNum;
	}
	public Integer getMonthlyId() {
		return monthlyId;
	}
	public void setMonthlyId(Integer monthlyId) {
		this.monthlyId = monthlyId;
	}
	public Short getUserAuthorityType() {
		return userAuthorityType;
	}
	public void setUserAuthorityType(Short userAuthorityType) {
		this.userAuthorityType = userAuthorityType;
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
	public Date getShareGivingTime() {
		return shareGivingTime;
	}
	public void setShareGivingTime(Date shareGivingTime) {
		this.shareGivingTime = shareGivingTime;
	}
	public Date getShareGivingTimeStart() {
		return shareGivingTimeStart;
	}
	public void setShareGivingTimeStart(Date shareGivingTimeStart) {
		this.shareGivingTimeStart = shareGivingTimeStart;
	}
	public Date getShareGivingTimeEnd() {
		return shareGivingTimeEnd;
	}
	public void setShareGivingTimeEnd(Date shareGivingTimeEnd) {
		this.shareGivingTimeEnd = shareGivingTimeEnd;
	}
	
}
