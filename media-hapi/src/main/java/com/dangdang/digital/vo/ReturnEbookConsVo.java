package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * Description: 与前台交互的单品消费vo 
 * All Rights Reserved.
 * @version 1.0  2014年12月19日 下午5:29:12  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class ReturnEbookConsVo implements Serializable {

	private static final long serialVersionUID = 8277740630548181121L;

	/**
	 * custId 加密后的
	 */
	private String custId;

	/**
	 * 用户头像地址
	 */
	private String userImgUrl;
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 消费的金币
	 */
	private Integer cons;


	public String getUserImgUrl() {
		return userImgUrl;
	}

	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getCons() {
		return cons;
	}

	public void setCons(Integer cons) {
		this.cons = cons;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}


}
