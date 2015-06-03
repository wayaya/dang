package com.dangdang.digital.mock.model;

import java.util.List;

public class ChannelArticlePackage {
	
	private String date;
	
	private  Long  timeMills;
	
	private List<ChannelArticle> articlePackage;
	

	public List<ChannelArticle> getArticlePackage() {
		return articlePackage;
	}

	public void setArticleContentPackage(
			List<ChannelArticle> articlePackage) {
		this.articlePackage = articlePackage;
	}

	
	public Long getTimeMills() {
		return timeMills;
	}

	public void setTimeMills(Long timeMills) {
		this.timeMills = timeMills;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	
}

