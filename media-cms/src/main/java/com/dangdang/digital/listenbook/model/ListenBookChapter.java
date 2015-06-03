package com.dangdang.digital.listenbook.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class ListenBookChapter implements Serializable {
	private static final long serialVersionUID = 9194201809681455740L;
	
	private BigInteger chapterId;
	private int order;
	private String name;
	private List<String> streams;
	private int size;
	
	public BigInteger getChapterId() {
		return chapterId;
	}
	public void setChapterId(BigInteger chapterId) {
		this.chapterId = chapterId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getStreams() {
		return streams;
	}
	public void setStreams(List<String> streams) {
		this.streams = streams;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
}
