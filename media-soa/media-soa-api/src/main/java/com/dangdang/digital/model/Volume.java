package com.dangdang.digital.model;

import java.util.Date;
import java.util.List;

public class Volume {
    private Long volumeId;

    private String title;

    private Integer volumeOrder;

    private Date creationDate;

    private Long mediaId;

    private String cpCode;

    private String cpVolumeId;

    private String descs;

    private List<Chapter> list;
    
    private Integer maxIndex;
    public Long getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(Long volumeId) {
        this.volumeId = volumeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getVolumeOrder() {
        return volumeOrder;
    }

    public void setVolumeOrder(Integer volumeOrder) {
        this.volumeOrder = volumeOrder;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode == null ? null : cpCode.trim();
    }

    public String getCpVolumeId() {
        return cpVolumeId;
    }

    public void setCpVolumeId(String cpVolumeId) {
        this.cpVolumeId = cpVolumeId == null ? null : cpVolumeId.trim();
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs == null ? null : descs.trim();
    }

	public List<Chapter> getList() {
		return list;
	}

	public void setList(List<Chapter> list) {
		this.list = list;
	}

	public Integer getMaxIndex() {
		return maxIndex;
	}

	public void setMaxIndex(Integer maxIndex) {
		this.maxIndex = maxIndex;
	}
    
}