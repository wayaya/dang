package com.dangdang.digital.constant;

/**
 * Description: 平台来源枚举
 * All Rights Reserved.
 * @version 1.0  2014年12月27日 下午4:40:50  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public enum BehaviorTypeEnum {
	DOWNLOAD("DOWNLOAD","下载"),
	PLAY("PLAY","播放"),
	SEARCH("SEARCH","搜索");
	
	private String type;
	private String name;

	
	private BehaviorTypeEnum(String type,String name){
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static BehaviorTypeEnum getByType(String type){
		for(BehaviorTypeEnum searchType : BehaviorTypeEnum.values()){
			if(searchType.getType().equals(type)){
				return searchType;
			}
		}
		return null;
	}
}
