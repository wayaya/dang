package com.dangdang.digital.vo;

import java.util.List;

import com.dangdang.digital.model.AnnouncementsContent;

/**
 * 
 * Description: 公告反馈对象Vo
 * All Rights Reserved.
 * @version 1.0  2015年1月6日 下午6:46:33  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public class ReturnAnnouncementVo {
	
	private String categoryName;

	private String categoryCode;
	
	private String position;
	
	private String icon;
	
	
	/**
	 * 公告内容
	 */
	private List<AnnouncementsContent>  announcementList;
	
	
	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getCategoryCode() {
		return categoryCode;
	}


	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public List<AnnouncementsContent> getAnnouncementList() {
		return announcementList;
	}


	public void setAnnouncementList(List<AnnouncementsContent> announcementList) {
		this.announcementList = announcementList;
	}


	
	
	
	
	
}	
