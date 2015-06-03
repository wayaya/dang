package com.dangdang.digital.listenbook.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class ListenBookMedia implements Serializable{
	private static final long serialVersionUID = 9194296839681455740L;
	
	private BigInteger albumId;
	private Long ddMediaId ;
	private Long saleId;
	private Long authorId;
	private String name;
	private List<String> authors;
	private String authorsStr;
	private String brief;
	private List<String> speakers;
	private String speakersStr;
	private List<String> categorys;
	private List<String> selfCategorys;
//	private List<String> selfCategoryNames;
	private Date updateTime;
	private String status;
//	private List<String> sources; 废弃不用
	private String imageUrl;
	private String imageContent;//上传CDN，获取path
	private Integer episodesSize;
	private List<ListenBookChapter> chapters;
	private String cpCode;
	
	public BigInteger getAlbumId() {
		return albumId;
	}
	public void setAlbumId(BigInteger albumId) {
		this.albumId = albumId;
	}
	public Long getDdMediaId() {
		return ddMediaId;
	}
	public void setDdMediaId(Long ddMediaId) {
		this.ddMediaId = ddMediaId;
	}
	public Long getSaleId() {
		return saleId;
	}
	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}
	public Long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getAuthors() {
		return authors;
	}
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	public String getAuthorsStr() {
		return authorsStr;
	}
	public void setAuthorsStr(String authorsStr) {
		this.authorsStr = authorsStr;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public List<String> getSpeakers() {
		return speakers;
	}
	public void setSpeakers(List<String> speakers) {
		this.speakers = speakers;
	}
	public String getSpeakersStr() {
		return speakersStr;
	}
	public void setSpeakersStr(String speakersStr) {
		this.speakersStr = speakersStr;
	}
	public List<String> getCategorys() {
		return categorys;
	}
	public void setCategorys(List<String> categorys) {
		this.categorys = categorys;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public List<String> getSelfCategorys() {
		return selfCategorys;
	}
	public void setSelfCategorys(List<String> selfCategorys) {
		this.selfCategorys = selfCategorys;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageContent() {
		return imageContent;
	}
	public void setImageContent(String imageContent) {
		this.imageContent = imageContent;
	}
	public Integer getEpisodesSize() {
		return episodesSize;
	}
	public void setEpisodesSize(Integer episodesSize) {
		this.episodesSize = episodesSize;
	}
	public List<ListenBookChapter> getChapters() {
		return chapters;
	}
	public void setChapters(List<ListenBookChapter> chapters) {
		this.chapters = chapters;
	}
	public String getCpCode() {
		return cpCode;
	}
	public void setCpCode(String cpCode) {
		this.cpCode = cpCode;
	}
	
}
