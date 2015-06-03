package com.dangdang.digital.utils;

import javax.servlet.http.HttpServletRequest;


 
public class RequestParamCheckerUtils {
	public static class TwoTuple<A,M>{
		//是否正确
		public final A success;
		//错误信息
		public final M errorMsg;
		TwoTuple(A success,M errorMsg){
			this.success = success;
			this.errorMsg = errorMsg;
		}
		
	}
	public static class ThreeTuple<A,V,M> extends TwoTuple<A,M>{
		
		public final V value;
		//错误信息
		ThreeTuple(A success,V value,M errorMsg){
			super(success,errorMsg);
			this.value = value;
		}
		
	}
	public static class ListResultTuple<A,S,E,C,M> extends TwoTuple<A,M> {
			public final S start;
			public final E end;
			public final C count;
			ListResultTuple(A success,S start,E end,C count,M errorMsg){
				super(success,errorMsg);
				this.start = start;
				this.end = end;
				this.count = count;
			}

		}
	public static boolean isNullOrEmpty(String value){
		return value==null || value.trim().isEmpty();
	}
	
	
	public static String getFullCodeByChannelTypeAndShortCode(String channelType,String shortCode){
		return (channelType+"_"+shortCode).toLowerCase();
	}
	public static  ThreeTuple<Boolean,String,String> checkParam(String paramName,HttpServletRequest request) {
		String parmaValue  =  request.getParameter(paramName);
		boolean isSuccess = true;
		String errorMsg ="";
		if(isNullOrEmpty(parmaValue)){
			errorMsg ="参数"+paramName+"不存在";
			isSuccess = false;
		}
		return new ThreeTuple<Boolean,String,String>(isSuccess,parmaValue,errorMsg);
	}
	public static  ListResultTuple<Boolean,Integer,Integer,Integer,String> checkStartEndParam(HttpServletRequest request) {
		//栏目类型标识
		boolean isSuccess = true;
		String errorMsg ="";
		//String type  =  request.getParameter("type");
		//请求数量
		String start =  request.getParameter("start");
		String end 	 = request.getParameter("end");
		if(isNullOrEmpty(start)){
			errorMsg = "请求数量参数start不存在或非法,必须是数值类型";
			isSuccess =  false;
		}
		int startInt = 0;
		int endInt = 0;
		int count = 0 ;
		
		if(isNullOrEmpty(end)){
			errorMsg = "请求数量参数end不存在或非法,必须是数值类型";
			isSuccess = false;
		}
		if(isSuccess){
			try{
				startInt = Integer.valueOf(start);
				endInt = Integer.valueOf(end);
			}catch(NumberFormatException e){
				errorMsg = "请求数量参数start,end数值不对,必须是数字";
				isSuccess = false;
			}
			if(startInt < 0 || endInt < 0||(endInt < startInt)){
				errorMsg = "请求数量参数start,end数值不对";
				isSuccess = false;
			}else{
				count = endInt - startInt+1;
			}
		}
		return new ListResultTuple<Boolean,Integer,Integer,Integer,String>(isSuccess,startInt,endInt,count,errorMsg);
	}
	
	
	
	
}	
