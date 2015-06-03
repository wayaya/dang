package com.dangdang.digital.exception;

public class MediaException extends Exception{
	/**
	   * @Fields serialVersionUID : TODO 2014年12月3日 下午12:19:17  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	private static final long serialVersionUID = -4294734450421367192L;
	String errorMsg;
	String errorCode;
	public MediaException(){}
	
	public MediaException(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public MediaException(String errorCode,String errorMsg) {
		this.errorMsg = errorMsg;
		this.errorCode = errorCode;
	}
	
	public String getErrorMsg(){
		return this.errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
}
