package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class BookCityFragmentType implements Serializable {

	private static final long serialVersionUID = -6246640982818575475L;

	private Long bookcityFragmentTypeId;

    private Long parentId;

    private String creator;

    private String typeName;
    
    private String description;

    private Date creationDate;

    private String modifier;

    private Date lastChangedDate;

    public Long getBookcityFragmentTypeId() {
        return bookcityFragmentTypeId;
    }

    public void setBookcityFragmentTypeId(Long bookcityFragmentTypeId) {
        this.bookcityFragmentTypeId = bookcityFragmentTypeId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}