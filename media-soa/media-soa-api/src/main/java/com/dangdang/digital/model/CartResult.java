package com.dangdang.digital.model;


/**
 * 操作购物车结果.
 * @author xuye
 * 
 */
public class CartResult extends BaseResult {

	private String errorMsg;
	
	private String from;	

	private Object data;	
		
	public CartResult() {		
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}	

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
}
