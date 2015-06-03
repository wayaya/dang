package com.dangdang.digital.model;

/**
 * Description: 敏感作品
 * All Rights Reserved.
 * @version 1.0  2014年11月22日 下午2:54:14  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public class IllegalMedia {
    private Integer illegalMediaId;
    
    private Long mediaId;
    
    //敏感原因说明
    private String details;
    
	private String mediaName;
	
	private String creator;

    private Integer authorId;

    private Long operatorId;

    private String lastChangeDate;

	private Media media;
	
	public Media getMedia() {
			return media;
		}

		public void setMedia(Media media) {
			this.media = media;
	}
    public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
    public Integer getIllegalMediaId() {
        return illegalMediaId;
    }

    public Long getMediaId() {
  		return mediaId;
  	}

  	public void setMediaId(Long mediaId) {
  		this.mediaId = mediaId;
  	}
  	
  	
    public void setIllegalMediaId(Integer illegalMediaId) {
        this.illegalMediaId = illegalMediaId;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName == null ? null : mediaName.trim();
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(String lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }
}