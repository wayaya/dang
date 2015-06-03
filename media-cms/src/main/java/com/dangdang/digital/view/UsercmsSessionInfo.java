package com.dangdang.digital.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.dangdang.digital.model.Usercms;

public class UsercmsSessionInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private boolean admin;
	private Usercms userInfo = new Usercms();
	private Map<String, Boolean> f = new HashMap<String, Boolean>();
	
	public Usercms getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(Usercms userInfo) {
		this.userInfo = userInfo;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public Map<String, Boolean> getF() {
		return f;
	}
	public void setF(Map<String, Boolean> f) {
		this.f = f;
	}
	
}
