package com.dangdang.digital.mock.model;

import java.io.Serializable;

/**
 * 
 * @author lvxiang
 * @date   2015年5月15日 下午6:03:39
 * 专题实体
 */
public class SpecialTopic  implements  Serializable{
	
	private Long stId;
	
	private String img;
	
	
	public Long getStId() {
		return stId;
	}

	public void setStId(Long stId) {
		this.stId = stId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	
	
}
