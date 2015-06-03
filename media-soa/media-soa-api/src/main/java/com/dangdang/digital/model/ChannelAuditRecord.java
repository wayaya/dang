package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;


/**
 * ChannelAuditRecord Entity.
 */
public class ChannelAuditRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4507254067441721910L;

	//列信息
	private Long recordId;
	
	private Long channelId;
	
	private String channelTitle;
	
	private Date createDate;
	
	private Date auditDate;
	
	private Integer status;
	
	private String failMsg;
	
	private String auditor;
	

		
	public void setRecordId(Long value) {
		this.recordId = value;
	}
	
	public Long getRecordId() {
		return this.recordId;
	}
		
		
	public void setChannelId(Long value) {
		this.channelId = value;
	}
	
	public Long getChannelId() {
		return this.channelId;
	}
		
		
	public void setChannelTitle(String value) {
		this.channelTitle = value;
	}
	
	public String getChannelTitle() {
		return this.channelTitle;
	}
		
		
	public void setCreateDate(Date value) {
		this.createDate = value;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
		
		
	public void setAuditDate(Date value) {
		this.auditDate = value;
	}
	
	public Date getAuditDate() {
		return this.auditDate;
	}
		
		
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
		
		
	public void setFailMsg(String value) {
		this.failMsg = value;
	}
	
	public String getFailMsg() {
		return this.failMsg;
	}
		
		
	public void setAuditor(String value) {
		this.auditor = value;
	}
	
	public String getAuditor() {
		return this.auditor;
	}
		
}

