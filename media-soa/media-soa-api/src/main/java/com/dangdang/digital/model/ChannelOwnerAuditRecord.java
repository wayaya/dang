package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;


/**
 * ChannelOwnerAuditRecord Entity.
 */
public class ChannelOwnerAuditRecord implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8265772340953647651L;

	//列信息
	private Long recordId;
	
	private Long ownerId;
	
	private String account;
	
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
		
		
	public void setOwnerId(Long value) {
		this.ownerId = value;
	}
	
	public Long getOwnerId() {
		return this.ownerId;
	}
		
		
	public void setAccount(String value) {
		this.account = value;
	}
	
	public String getAccount() {
		return this.account;
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

