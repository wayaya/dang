package com.dangdang.digital.view;

import java.util.LinkedHashSet;

public class CustomerMediaData {
	
	private Long custId;
	private LinkedHashSet<Long> history = new LinkedHashSet<Long>();
	 
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public LinkedHashSet<Long> getHistory() {
		return history;
	}
	public void setHistory(LinkedHashSet<Long> history) {
		this.history = history;
	}
}
