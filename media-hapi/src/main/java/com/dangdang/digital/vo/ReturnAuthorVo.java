package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * 
 * Description: 作者交互vo All Rights Reserved.
 * 
 * @version 1.0 2014年12月22日 下午1:46:06 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ReturnAuthorVo implements Serializable {

	private static final long serialVersionUID = 7139241141956336048L;

	/**
	 * 作者Id
	 */
	private Long authorId;

	/**
	 * 笔名
	 */
	private String authorPenname;

	/**
	 * 性别
	 */
	private Short sex;

	/**
	 * 生日
	 */
	private Long birth;

	/**
	 * 介绍
	 */
	private String introduction;

	/***
	 * 头像
	 */
	private String headPic;

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getAuthorPenname() {
		return authorPenname;
	}

	public void setAuthorPenname(String authorPenname) {
		this.authorPenname = authorPenname;
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public Long getBirth() {
		return birth;
	}

	public void setBirth(Long birth) {
		this.birth = birth;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

}
