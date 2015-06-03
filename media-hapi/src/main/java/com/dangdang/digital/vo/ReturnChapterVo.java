package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * 
 * Description: 返回chapter vo All Rights Reserved.
 * 
 * @version 1.0 2014年12月17日 下午2:36:09 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ReturnChapterVo implements Serializable {

	private static final long serialVersionUID = -1906164440141092458L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 章节名
	 */
	private String title;

	/**
	 * 顺序
	 */
	private Integer index;

	/**
	 * 是否免费（0：否 1：是）
	 */
	private String isFree;

	/**
	 * 章节文件路径
	 */
	private String filePath;

	/**
	 * 文件大小
	 */
	private Long fileSize;

	/**
	 * 时长
	 */
	private Integer duration;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

}
