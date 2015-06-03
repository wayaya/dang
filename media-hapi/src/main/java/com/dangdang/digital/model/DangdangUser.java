package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

import javacommon.util.StringUtils;

/**
 * 当当用户.
 *
 */
public class DangdangUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long displayId;
	private String email;
	private String nickName;
	private String point;
	private String phone;
	private Date registerDate;
	private String vipType;
	private String headportrait;
	
	/**
	 * 客户端来源  
	 * Constants.SOURCE_CLIENT_DD_READER
	 * Constants.SOURCE_CLIENT_DD_NOVEL
	 */
	private String sourceClient;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDisplayId() {
		return displayId;
	}
	public void setDisplayId(Long displayId) {
		this.displayId = displayId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getVipType() {
		return vipType;
	}
	public void setVipType(String vipType) {
		this.vipType = vipType;
	}
	public String getHeadportrait() {
		return headportrait;
	}
	public void setHeadportrait(String headportrait) {
		this.headportrait = headportrait;
	}
	
	public String getUserName() {
		String userName = null;
		if (StringUtils.isNotBlank(this.phone)) {
			userName = this.phone;
		} else {
			userName = this.email;
		}
		return userName;
	}
	
	public String getSourceClient() {
		return sourceClient;
	}
	
	public void setSourceClient(String sourceClient) {
		this.sourceClient = sourceClient;
	}
}
