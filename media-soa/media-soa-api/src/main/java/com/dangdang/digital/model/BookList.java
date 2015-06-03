package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class BookList implements Serializable{

	private static final long serialVersionUID = 5291383205773541007L;

	private Long booklistId;

    private Long channelId;

    private Long owner;
    
    private Byte isShow;

    private String name;

    private String imageUrl;

    private String description;
    
    private Byte status;

    private Date creationDate;

    private Long creator;

    private Date lastUpdateTime;

    private Long modifier;

    public Long getBooklistId() {
        return booklistId;
    }

    public void setBooklistId(Long booklistId) {
        this.booklistId = booklistId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

	public Byte getIsShow() {
		return isShow;
	}

	public void setIsShow(Byte isShow) {
		this.isShow = isShow;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
}