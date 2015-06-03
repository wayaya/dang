package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.List;

import com.dangdang.digital.model.CartResult;

public class MobileCartResultVo implements Serializable {

	private static final long serialVersionUID = -3189666355500594495L;

	private CartResult cartResult;
	
	private Object data;
	
	private int errorCode;
	
	private boolean succ;
	
	private List<String> pidsFromData;

	public List<String> getPidsFromData() {
		return pidsFromData;
	}

	public void setPidsFromData(List<String> pidsFromData) {
		this.pidsFromData = pidsFromData;
	}

	public CartResult getCartResult() {
		return cartResult;
	}

	public void setCartResult(CartResult cartResult) {
		this.cartResult = cartResult;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isSucc() {
		return succ;
	}

	public void setSucc(boolean succ) {
		this.succ = succ;
	}
	
	
}
