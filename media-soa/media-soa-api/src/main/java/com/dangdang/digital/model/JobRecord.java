package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class JobRecord implements Serializable{
	
	private static final long serialVersionUID = -1817651505620376142L;

	private Long jobRecordId;

    private String jobRecordName;

    private Long lastRecordId;

    private Date lastRecordTime;

    private Date lastChangedDate;

    public Long getJobRecordId() {
        return jobRecordId;
    }

    public void setJobRecordId(Long jobRecordId) {
        this.jobRecordId = jobRecordId;
    }

    public String getJobRecordName() {
        return jobRecordName;
    }

    public void setJobRecordName(String jobRecordName) {
        this.jobRecordName = jobRecordName == null ? null : jobRecordName.trim();
    }

    public Long getLastRecordId() {
        return lastRecordId;
    }

    public void setLastRecordId(Long lastRecordId) {
        this.lastRecordId = lastRecordId;
    }

    public Date getLastRecordTime() {
        return lastRecordTime;
    }

    public void setLastRecordTime(Date lastRecordTime) {
        this.lastRecordTime = lastRecordTime;
    }

    public Date getLastChangedDate() {
        return lastChangedDate;
    }

    public void setLastChangedDate(Date lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }
    
    public String toString(){
    	return this.lastRecordId+","+ this.jobRecordName+","+this.lastRecordTime;
    }
}