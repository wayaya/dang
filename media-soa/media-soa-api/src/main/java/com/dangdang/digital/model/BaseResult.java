package com.dangdang.digital.model;

import java.io.Serializable;

/**
 * 返回结果基类.
 */
public class BaseResult implements Serializable{

	private static final long serialVersionUID = -4401138294249374391L;

	private int errorCode;
	
	private int statusCode;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public boolean isSucc() {
		return getErrorCode() == 0;
	}
	
}
