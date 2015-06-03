package com.dangdang.digital.constant;

/**
 * Description: 原创频道枚举类
 * All Rights Reserved.
 * @version 1.0  2015年1月10日 下午1:40:41  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public enum ChannelTypeEnum {
	NP("NP","男频"),
	VP("VP","女频"),
	ALL("ALL","全频");
	
	private String channelTypeNo;
	private String channelTypeName;

	private ChannelTypeEnum(String channelTypeNo,String channelTypeName){
		this.channelTypeNo = channelTypeNo;
		this.channelTypeName = channelTypeName;
	}

	public String getChannelTypeNo() {
		return channelTypeNo;
	}

	public void setChannelTypeNo(String channelTypeNo) {
		this.channelTypeNo = channelTypeNo;
	}

	public String getChannelTypeName() {
		return channelTypeName;
	}

	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}

	public static ChannelTypeEnum getByChannelType(String channelTypeNo){
		for(ChannelTypeEnum channelType : ChannelTypeEnum.values()){
			if(channelType.getChannelTypeNo().equals(channelTypeNo)){
				return channelType;
			}
		}
		return null;
	}
}