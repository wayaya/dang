package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class BookCityModel implements Serializable {
	
	private static final long serialVersionUID = -3499080445263455634L;

	private Long bookcityModelId;

    private String modelCode;

    private String modelName;

    private String modelFragContentIds;

    private Integer status;

    private String creator;

    private Date creationDate;

    private String modifier;

    private Date lastChangedDate;
    
    private String modelHtml;

    private String modelHtmlEditing;

    public Long getBookcityModelId() {
        return bookcityModelId;
    }

    public void setBookcityModelId(Long bookcityModelId) {
        this.bookcityModelId = bookcityModelId;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode == null ? null : modelCode.trim();
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
    }

    public String getModelFragContentIds() {
        return modelFragContentIds;
    }

    public void setModelFragContentIds(String modelFragContentIds) {
        this.modelFragContentIds = modelFragContentIds == null ? null : modelFragContentIds.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getLastChangedDate() {
        return lastChangedDate;
    }

    public void setLastChangedDate(Date lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }
    
    public String getModelHtml() {
        return modelHtml;
    }

    public void setModelHtml(String modelHtml) {
        this.modelHtml = modelHtml == null ? null : modelHtml.trim();
    }

    public String getModelHtmlEditing() {
        return modelHtmlEditing;
    }

    public void setModelHtmlEditing(String modelHtmlEditing) {
        this.modelHtmlEditing = modelHtmlEditing == null ? null : modelHtmlEditing.trim();
    }
}