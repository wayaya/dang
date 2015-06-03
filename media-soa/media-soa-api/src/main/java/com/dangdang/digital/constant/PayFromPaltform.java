package com.dangdang.digital.constant;

/**
 * 
 * Description: 支付平台来源
 * All Rights Reserved.
 * @version 1.0  2015年3月12日 下午12:12:31  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public enum PayFromPaltform {
	DS("ds","当当读书平台"),
	YC("yc","当读小说平台"),
	DS_ANDROID("ds_android","当当读书安卓平台"),
	DS_IOS("ds_ios","当当读书ios平台"),
	YC_ANDROID("yc_android","当读小说安卓平台"),
	YC_IOS("yc_ios","当读小说ios平台");
	
	private String source;
	private String name;
	
	private PayFromPaltform(String source, String name) {
		this.source = source;
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public String getName() {
		return name;
	}
	
	public static PayFromPaltform getBySource(String source){
		for(PayFromPaltform searchSource : PayFromPaltform.values()){
			if(searchSource.getSource().equals(source)){
				return searchSource;
			}
		}
		return null;
	}
	
	public static PayFromPaltform getParentSource(String source){
		if(source == null || source.trim().equals("")){
			return null;
		}
		if(getBySource(source) == null){
			return null;
		}
		if(source.startsWith("yc")){
			return YC;
		}else if(source.startsWith("ds")){
			return DS;
		}else{
			return null;
		}
	}
}
