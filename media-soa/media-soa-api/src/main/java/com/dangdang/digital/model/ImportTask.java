package com.dangdang.digital.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ImportTask {
	
	public static final Integer STATUS_NEW = 0;
	public static final Integer STATUS_WAITING = 1;
	public static final Integer STATUS_RUNNING = 2;
	public static final Integer STATUS_FINISHED = 3;
	public static final Integer STATUS_CONFLICT = 4;
	public static final Integer STATUS_FAILED = -1;
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<Integer, String> statuStrMap = new HashMap();
	{
		statuStrMap.put(STATUS_NEW, "未执行");
		statuStrMap.put(STATUS_WAITING, "等待执行");
		statuStrMap.put(STATUS_RUNNING, "执行中");
		statuStrMap.put(STATUS_FINISHED, "已完成");
		statuStrMap.put(STATUS_CONFLICT, "重复");
		statuStrMap.put(STATUS_FAILED, "执行失败");
	}
	
    private Long taskId;

    private String path;

    private Date creationDate;

    private Date finishDate;

    private String status;

    private String error;

    private Long batchId;

    private String title;

    private String author;

    private Long mediaId;

    private Long chapterId;

    private String chapterName;

    private String mediaName;

    private String volumeName;

    private Long volumeId;

    private String uid;
    
    private Long mediaPullId;
    
    private Long chapterPullId;
    
    private Integer indexOrder;
    
    private String multiStatus;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error == null ? null : error.trim();
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
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

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName == null ? null : chapterName.trim();
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName == null ? null : mediaName.trim();
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName == null ? null : volumeName.trim();
    }

    public Long getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(Long volumeId) {
        this.volumeId = volumeId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

	public Long getMediaPullId() {
		return mediaPullId;
	}

	public void setMediaPullId(Long mediaPullId) {
		this.mediaPullId = mediaPullId;
	}

	public Long getChapterPullId() {
		return chapterPullId;
	}

	public void setChapterPullId(Long chapterPullId) {
		this.chapterPullId = chapterPullId;
	}

	public Integer getIndexOrder() {
		return indexOrder;
	}

	public void setIndexOrder(Integer indexOrder) {
		this.indexOrder = indexOrder;
	}

	public String getMultiStatus() {
		return multiStatus;
	}

	public void setMultiStatus(String multiStatus) {
		this.multiStatus = multiStatus;
	}
    
}