package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class BookCityFragContent implements Serializable{
	
	private static final long serialVersionUID = 3562513237321641133L;

	private Long bookcityFragContentId;

    private Long fragmentId;

    private String creator;

    private Date creationDate;

    private String modifier;

    private Date lastChangedDate;
    
    private String contentHtml;

    private String contentHtmlEditing;

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml == null ? null : contentHtml.trim();
    }

    public String getContentHtmlEditing() {
        return contentHtmlEditing;
    }

    public void setContentHtmlEditing(String contentHtmlEditing) {
        this.contentHtmlEditing = contentHtmlEditing == null ? null : contentHtmlEditing.trim();
    }

    public Long getBookcityFragContentId() {
        return bookcityFragContentId;
    }

    public void setBookcityFragContentId(Long bookcityFragContentId) {
        this.bookcityFragContentId = bookcityFragContentId;
    }

    public Long getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(Long fragmentId) {
        this.fragmentId = fragmentId;
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
}