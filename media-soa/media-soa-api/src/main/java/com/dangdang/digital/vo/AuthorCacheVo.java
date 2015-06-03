package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description: 作者信息缓存 All Rights Reserved.
 * 
 * @version 1.0 2014年12月13日 上午10:31:02 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class AuthorCacheVo implements Serializable {

	private static final long serialVersionUID = -3321523404250713729L;

	private Long authorId;

	private String pseudonym;

	private Short sex;

	private Date birth;

	private String sign;

	private String creator;

	private Date creationDate;

	private String modifier;

	private Date lastModifiedDate;

	private String name;

	private String cpType;

	private String cpAuthorId;

	private String introduction;

	private String headPic;

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getPseudonym() {
		return pseudonym;
	}

	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpType() {
		return cpType;
	}

	public void setCpType(String cpType) {
		this.cpType = cpType;
	}

	public String getCpAuthorId() {
		return cpAuthorId;
	}

	public void setCpAuthorId(String cpAuthorId) {
		this.cpAuthorId = cpAuthorId;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

}
