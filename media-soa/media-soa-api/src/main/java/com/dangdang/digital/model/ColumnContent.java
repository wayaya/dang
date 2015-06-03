package com.dangdang.digital.model;

public class ColumnContent {
	
    private Integer contentId;

    private Integer columnId;
    
    private String columnCode;
    
    private Long saleId;

    private String saleName;

    private String creationDate;

    private String creator;

    private String aduitor;

    private String aduitDate;

    private String startDate;

    private String endDate;

    private String lastChangeDate;

    private String modifer;

    private Integer status;

    private Integer orderValue;
    
   
	private Media media;
    
    private MediaSale  mediaSale;
    
    
    public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public MediaSale getMediaSale() {
		return mediaSale;
	}

	public void setMediaSale(MediaSale mediaSale) {
		this.mediaSale = mediaSale;
	}
	
    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName == null ? null : saleName.trim();
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate == null ? null:creationDate.trim().isEmpty()? null:creationDate.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getAduitor() {
        return aduitor;
    }

    public void setAduitor(String aduitor) {
        this.aduitor = aduitor == null ? null : aduitor.trim();
    }

    public String getAduitDate() {
        return aduitDate;
    }

    public void setAduitDate(String aduitDate) {
        this.aduitDate = aduitDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null:startDate.trim().isEmpty()? null:startDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null:endDate.trim().isEmpty()? null:endDate.trim();
    }

    public String getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(String lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public String getModifer() {
        return modifer;
    }

    public void setModifer(String modifer) {
        this.modifer = modifer == null ? null : modifer.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }
    public String getColumnCode() {
		return columnCode;
	}
   

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode == null? null : columnCode.trim();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
			sb.append("columnCode=").append(columnCode).append(",")
			   .append("saleName=").append(saleName).append(",")
			   .append("mediaId=").append(this.media!=null?this.media.getMediaId():"").append(",")
			   .append("saleId=").append(this.saleId).append(",");
		return sb.toString();
	}
}