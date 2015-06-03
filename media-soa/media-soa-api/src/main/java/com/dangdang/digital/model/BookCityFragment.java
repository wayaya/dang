package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

public class BookCityFragment  implements Serializable{

	private static final long serialVersionUID = 8944635743856210953L;

	private Long bookcityFragmentId;

    private String fragmentName;

    private Long fragmentType;

    private Long fragmentSubtype;

    private Boolean hasTitle;

    private String title;

    private Boolean hasMoreText;

    private String moreText;

    private Integer status;

    private String creator;

    private Date creationDate;

    private String modifier;

    private Date lastChangedDate;

    private String fragmentHtml;

    public Long getBookcityFragmentId() {
        return bookcityFragmentId;
    }

    public void setBookcityFragmentId(Long bookcityFragmentId) {
        this.bookcityFragmentId = bookcityFragmentId;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName == null ? null : fragmentName.trim();
    }

    public Long getFragmentType() {
        return fragmentType;
    }

    public void setFragmentType(Long fragmentType) {
        this.fragmentType = fragmentType;
    }

    public Long getFragmentSubtype() {
        return fragmentSubtype;
    }

    public void setFragmentSubtype(Long fragmentSubtype) {
        this.fragmentSubtype = fragmentSubtype;
    }

    public Boolean getHasTitle() {
        return hasTitle;
    }

    public void setHasTitle(Boolean hasTitle) {
        this.hasTitle = hasTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Boolean getHasMoreText() {
        return hasMoreText;
    }

    public void setHasMoreText(Boolean hasMoreText) {
        this.hasMoreText = hasMoreText;
    }

    public String getMoreText() {
        return moreText;
    }

    public void setMoreText(String moreText) {
        this.moreText = moreText == null ? null : moreText.trim();
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

    public String getFragmentHtml() {
        return fragmentHtml;
    }

    public void setFragmentHtml(String fragmentHtml) {
        this.fragmentHtml = fragmentHtml == null ? null : fragmentHtml.trim();
    }
}