package com.dangdang.digital.constant;

/**
 * 
 * Description: 栏目相关的常量
 * All Rights Reserved.
 * @version 1.0  2014年11月21日 下午3:10:54  by 吕翔  (lvxiang@dangdang.com) 创建
 */
  
public enum ConstantEnum {
		 
		 /**
		  * 销售主体状态
		  */
		 SALE_STATUS_FORCE_INVALID("强制无效",0),
		 SALE_STATUS_FORCE_VALID("强制有效",1),
		 SALE_STATUS_NORMAL("正常显示",2),
		 SALE_ON_SHELF_STATUS("上架状态",1),
		 SALE_OFF_SHELF_STATUS("下架状态",2),
		
		 //栏目下销售主体的状态
		 SALE_HISTORY_STATUS("归档状态",9),
		//栏目状态
		COLUMN_ACTIVE_FOREVER("栏目长期有效",1),
		COLUMN_ACTIVE_TEMP("栏目有指定有效期",0),
		
		//频道信息
		CHANNEL_ALL("全频","all"),
		CHANNEL_NP("男频","np"),
		CHANNEL_VP("女频","vp"),
		;
		
		private final String name;
		private final int value;
		
		private final String code;
		
		
		private ConstantEnum(final int value,final String name,final String code){
			this.name = name;
			this.value = value;
			this.code = code;
		}
		private ConstantEnum(final String name,final int value){
			this(value,name,null);
		}
	
		private ConstantEnum(final String name,final String code){
			this(0,name,code);
		}
		
		public String getCode(){
			return this.code;
		}
		
		public String getName(){
			return this.name;
		}
		public int getValue(){
			return this.value;
		}
		
}
