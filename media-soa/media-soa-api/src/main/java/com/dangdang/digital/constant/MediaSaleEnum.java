package com.dangdang.digital.constant;

public enum MediaSaleEnum {

	AUTO("单品",0),
	PACKAGE("打包",1);
	
	private String name;
	private int type;
	
	private MediaSaleEnum(String name,int type){
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}
	
}
