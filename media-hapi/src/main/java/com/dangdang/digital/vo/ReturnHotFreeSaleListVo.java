package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.List;

import com.dangdang.digital.mock.model.ChannelArticle;

public class ReturnHotFreeSaleListVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5455117566681501511L;
	
	private Long activityEndTime;
	
	public Long getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(Long activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public List<ReturnDDHotFreeVo> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<ReturnDDHotFreeVo> mediaList) {
		this.mediaList = mediaList;
	}

	private List<ReturnDDHotFreeVo> mediaList;

}
