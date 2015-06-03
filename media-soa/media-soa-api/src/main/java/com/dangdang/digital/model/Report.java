package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description: 举报 All Rights Reserved.
 * 
 * @version 1.0 2015年1月28日 下午8:30:54 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class Report implements Serializable {

	private static final long serialVersionUID = 4089251098756569649L;
	private Long id;

	private Long custId;
	/**
	 * 资源id
	 */
	private Long mediaId;
	/**
	 * 章节id
	 */
	private Long chapterId;

	private Date createTime;
	/**
	 * 举报类型 ReportTypeEnum
	 */
	private String reportType;
	/**
	 * 具体说明
	 */
	private String reportContent;
	/**
	 * 联系方式
	 */
	private String contactInfo;
	/**
	 * 平台来源
	 */
	private String platform;

	/** vo 字段 **/

	private String createTimeStart;

	private String createTimeEnd;

	private String platformName;

	private String reportTypeName;

	private String userName;

	private String custIdStr;

	private String mediaTitle;

	private String chapterTitle;

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

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getReportTypeName() {
		return reportTypeName;
	}

	public void setReportTypeName(String reportTypeName) {
		this.reportTypeName = reportTypeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCustIdStr() {
		return custIdStr;
	}

	public void setCustIdStr(String custIdStr) {
		this.custIdStr = custIdStr;
	}

	public String getMediaTitle() {
		return mediaTitle;
	}

	public void setMediaTitle(String mediaTitle) {
		this.mediaTitle = mediaTitle;
	}

	public String getChapterTitle() {
		return chapterTitle;
	}

	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}

}
