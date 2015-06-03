package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EpubImage implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7317663873545809434L;

	private Long id;

    private Long mediaId;

    private String url;

    private String localPath;

    private Date importTime;

    private Integer status;
    
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

    public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath == null ? null : localPath.trim();
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getStatusString(){
    	return statuStrMap.get(this.status);
    }
}