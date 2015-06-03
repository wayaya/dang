package com.dangdang.digital.mock.model;

import java.io.Serializable;
/**
 * 频道每日文章内容
 * @author lvxiang
 * @date   2015年5月15日 下午2:48:31
 */
public class ChannelArticle implements Serializable{
	/** 文章编号　**/
	private Long articleId;
	

	/** 文章标题　**/
	private String title;
	
	/** 文章图标 **/
	private String icon;
	
	/** 文章描述 **/
	private String description;
	
	/** 文章发布时间**/
	private String time;
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	
}