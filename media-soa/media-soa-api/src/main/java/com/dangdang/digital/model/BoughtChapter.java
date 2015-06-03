package com.dangdang.digital.model;

import java.io.Serializable;

public class BoughtChapter implements Serializable {

	private static final long serialVersionUID = 4275456270869216475L;

	/**
	 * 主键
	 */
	private Long boughtChapterId;

	/**
	 * 已购信息详情Id
	 */
	private Long boughtDetailId;

	/**
	 * 章节Id
	 */
	private Long chapterId;

	/**
	 * 章节名称
	 */
	private String chapterTitle;

	/**
	 * 章节序号
	 */
	private Integer chapterIndex;

	/**
	 * 主账户支付金额
	 */
	private Integer payMainPrice;
	/**
	 * 子账户支付金额
	 */
	private Integer paySubPrice;
	public Long getBoughtChapterId() {
		return boughtChapterId;
	}
	public void setBoughtChapterId(Long boughtChapterId) {
		this.boughtChapterId = boughtChapterId;
	}
	public Long getBoughtDetailId() {
		return boughtDetailId;
	}
	public void setBoughtDetailId(Long boughtDetailId) {
		this.boughtDetailId = boughtDetailId;
	}
	public Long getChapterId() {
		return chapterId;
	}
	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}
	public String getChapterTitle() {
		return chapterTitle;
	}
	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}
	public Integer getChapterIndex() {
		return chapterIndex;
	}
	public void setChapterIndex(Integer chapterIndex) {
		this.chapterIndex = chapterIndex;
	}
	public Integer getPayMainPrice() {
		return payMainPrice;
	}
	public void setPayMainPrice(Integer payMainPrice) {
		this.payMainPrice = payMainPrice;
	}
	public Integer getPaySubPrice() {
		return paySubPrice;
	}
	public void setPaySubPrice(Integer paySubPrice) {
		this.paySubPrice = paySubPrice;
	}
	
	
}
