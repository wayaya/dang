package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description: 管理员操作日志表
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午2:27:57  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class ManagerOperateLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7290229132368653342L;
	/**
	 * 管理员操作日志id
	 */
    private Long managerOperateLogId;
    /**
     * 操作类型
     */
    private Short operateType;
    /**
     * 操作结果
     */
    private Short operateResult;
    /**
     * 操作目标类型
     */
    private String operateTargetType;
    /**
     * 操作目标id
     */
    private Long operateTargetId;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 操作时间
     */
    private Date creationDate;
    /**
     * 操作描述
     */
    private String operateDescription;
    /**
     * 查询vo字段：创建开始时间
     */
    private String start;
    /**
     * 查询vo字段：创建结束时间
     */
    private String end;
    
    

    /**
	 * @param operateType
	 * @param operateResult
	 * @param operateTargetType
	 * @param operateTargetId
	 * @param operator
	 * @param creationDate
	 * @param operateDescription
	 */
	public ManagerOperateLog(Short operateType,
			Short operateResult, String operateTargetType,
			Long operateTargetId, String operator, Date creationDate,
			String operateDescription) {
		super();
		this.operateType = operateType;
		this.operateResult = operateResult;
		this.operateTargetType = operateTargetType;
		this.operateTargetId = operateTargetId;
		this.operator = operator;
		this.creationDate = creationDate;
		this.operateDescription = operateDescription;
	}

	public ManagerOperateLog() {
		super();
	}

	public Long getManagerOperateLogId() {
        return managerOperateLogId;
    }

    public void setManagerOperateLogId(Long managerOperateLogId) {
        this.managerOperateLogId = managerOperateLogId;
    }

    public Short getOperateType() {
        return operateType;
    }

    public void setOperateType(Short operateType) {
        this.operateType = operateType;
    }

    public Short getOperateResult() {
        return operateResult;
    }

    public void setOperateResult(Short operateResult) {
        this.operateResult = operateResult;
    }

    public String getOperateTargetType() {
        return operateTargetType;
    }

    public void setOperateTargetType(String operateTargetType) {
        this.operateTargetType = operateTargetType == null ? null : operateTargetType.trim();
    }

    public Long getOperateTargetId() {
        return operateTargetId;
    }

    public void setOperateTargetId(Long operateTargetId) {
        this.operateTargetId = operateTargetId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getOperateDescription() {
        return operateDescription;
    }

    public void setOperateDescription(String operateDescription) {
        this.operateDescription = operateDescription == null ? null : operateDescription.trim();
    }

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
    
}