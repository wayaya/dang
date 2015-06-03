package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

import com.dangdang.digital.utils.DateUtil;

/**
 * Description:块主体 
 * All Rights Reserved.
 * @version 1.0  2014年11月22日 上午11:01:32  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class Block implements Serializable{
	private static final long serialVersionUID = 7960063850959890767L;

	private Long mediaBlockId;

    /**
     * 块名称
     */
    private String name;

    /**
     * 标识
     */
    private String code;
    
    /**
     * 块主图
     */
    private String picPath;
    
    /**
     * 内容
     */
    private String content;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 组id
     */
    private Long groupId;


    /**
     * 块类型
     */
    private Integer type;
    
    /**
     * 目标URL
     */
    private String targetUrl;
    

    /**
     * 开始时间
     */
    private Date startDate;
    
    /**
     * 活动开始时间string
     */
    private String startDateString;

    /**
     * 结束时间
     */
    private Date endDate;
    
    /**
     * 活动结束时间
     */
    private String endDateString;

    /**
     * 关联栏目ID
     */
    private Integer relationColumnId;

    /**
     * 关联栏目标示code
     */
    private String relationColumnCode;

    /**
     * 访问次数
     */
    private Integer accessCount;

    /**
     * 最后修改的人
     */
    private String modifier;
    
    /**
     * 最后修改时间
     */
    private Date lastModifiedDate;

	public Long getMediaBlockId() {
		return mediaBlockId;
	}

	public void setMediaBlockId(Long mediaBlockId) {
		this.mediaBlockId = mediaBlockId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		if(startDate != null){
			try {
				this.startDateString = DateUtil.getDate(startDate, DateUtil.DATE_PATTERN);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.startDate = startDate;
	}

	public String getStartDateString() {
		return startDateString;
	}

	public void setStartDateString(String startDateString) {
		try {
			this.startDate = DateUtil.getdateFromString(startDateString, DateUtil.DATE_PATTERN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.startDateString = startDateString;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		if(endDate != null){
			try {
				this.endDateString = DateUtil.getDate(endDate, DateUtil.DATE_PATTERN);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.endDate = endDate;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public void setEndDateString(String endDateString) {
		try {
			this.endDate = DateUtil.getdateFromString(endDateString, DateUtil.DATE_PATTERN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.endDateString = endDateString;
	}

	public Integer getRelationColumnId() {
		return relationColumnId;
	}

	public void setRelationColumnId(Integer relationColumnId) {
		this.relationColumnId = relationColumnId;
	}

	public String getRelationColumnCode() {
		return relationColumnCode;
	}

	public void setRelationColumnCode(String relationColumnCode) {
		this.relationColumnCode = relationColumnCode;
	}

	public Integer getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Integer accessCount) {
		this.accessCount = accessCount;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}