package com.dangdang.digital.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MediaCategoryData implements Serializable{

	private static final long serialVersionUID = 7592699728539526931L;
	
	public MediaCategoryData(){}
	public MediaCategoryData(Long mediaId, List<String> categoryList){
		this.mediaId = mediaId;
		this.categoryList = categoryList;
	}
	
	Long mediaId;
	List<String> categoryList = new ArrayList<String>();
	
	public Long getMediaId() {
		return mediaId;
	}
	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}
	public List<String> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}
	
}
