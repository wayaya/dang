package com.dangdang.digital.constant;

/**
 * Description: 平台来源枚举
 * All Rights Reserved.
 * @version 1.0  2014年12月27日 下午4:40:50  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public enum PlatformSourceEnum {
	DDXS_P("DDXS-P","当读小说"),
	TS_P("TS-P","听书"),
	FP_P("FP-P","翻篇"),
	DDDS_P("DDDS-P","当当读书"),
	BBTS_P("BBTS-P","宝贝听书");
	
	private String source;
	private String name;

	
	private PlatformSourceEnum(String source,String name){
		this.source = source;
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static PlatformSourceEnum getBySource(String source){
		for(PlatformSourceEnum searchSource : PlatformSourceEnum.values()){
			if(searchSource.getSource().equals(source)){
				return searchSource;
			}
		}
		return null;
	}
}
