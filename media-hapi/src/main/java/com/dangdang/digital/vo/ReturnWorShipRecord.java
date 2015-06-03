package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * 
 * Description: 膜拜关系记录
 * All Rights Reserved.
 * @version 1.0  2015年3月16日 下午3:21:28  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public class ReturnWorShipRecord  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5666794149808253004L;


	//记录编号
	private Long recordId;

	//我膜拜过的或膜拜我的用户的custId
	private  String custId;
	
	private String custImg;

	//我膜拜过的或膜拜我的用户的的昵称
	private  String nickname;
	
	
	//最后一次膜拜时间
	private Long lastTime;
	
	private Long times;
	
	public void setTimes(Long value) {
		this.times = value;
	}
	
	public Long getTimes() {
		return this.times;
	}
	public String getCustImg() {
		return custImg;
	}

	public void setCustImg(String custImg) {
		this.custImg = custImg;
	}
	
	
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getNickName() {
		return nickname;
	}

	public void setNickName(String nickName) {
		this.nickname = nickName;
	}

	public Long getLastTime() {
		return lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}


}
