package com.dangdang.digital.constant;

public enum RefActionEnum {
	browse("browse");
	
	private String name;
	private RefActionEnum(String name){
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
