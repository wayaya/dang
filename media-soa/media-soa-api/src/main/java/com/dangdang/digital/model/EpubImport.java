package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EpubImport implements Serializable {
	private static final long serialVersionUID = -7965123939997693538L;

	private Long id;

    private String title;

    private Long ebookId;

    private Long productId;

    private String uid;

    private Integer status;

    private String error;

    private String batchId;

    private Date importTime;

    private Date createTime;
    
	public static final Integer STATUS_NEW = 0;
	public static final Integer STATUS_RUNNING = 1;
	public static final Integer STATUS_FINISHED = 2;
	public static final Integer STATUS_FAILED = -1;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<Integer, String> statuStrMap = new HashMap();
	{
		statuStrMap.put(STATUS_NEW, "未执行");
		statuStrMap.put(STATUS_RUNNING, "执行中");
		statuStrMap.put(STATUS_FINISHED, "已完成");
		statuStrMap.put(STATUS_FAILED, "执行失败");
	}
	

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getEbookId() {
        return ebookId;
    }

    public void setEbookId(Long ebookId) {
        this.ebookId = ebookId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Integer getStatus() {
        return status;
    }
    
    public String getStatusString(){
    	return statuStrMap.get(status);
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error == null ? null : error.trim();
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}