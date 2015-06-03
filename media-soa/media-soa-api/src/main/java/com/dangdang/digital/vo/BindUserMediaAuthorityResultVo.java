/**
 * Description: BindUserMediaAuthorityResultVo.java
 * All Rights Reserved.
 * @version 1.0  2015年1月5日 上午11:52:55  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.vo;

import java.io.Serializable;

import com.dangdang.digital.model.UserAuthority;
import com.dangdang.digital.model.UserMonthly;

/**
 * Description: 绑定用户权限返回结果vo
 * All Rights Reserved.
 * @version 1.0  2015年1月5日 上午11:52:55  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class BindUserMediaAuthorityResultVo implements Serializable {

	/**
	   * @Fields serialVersionUID : TODO 2015年1月5日 上午11:53:37  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = -9101131532043597389L;

	/**
	 * 权限类型
	 */
	private Short userAuthorityType;
	
	/**
	 * 包月权限
	 */
	private UserMonthly userMonthly;
	
	/**
	 * 电子书权限
	 */
	private UserAuthority userAuthority;

	public Short getUserAuthorityType() {
		return userAuthorityType;
	}

	public void setUserAuthorityType(Short userAuthorityType) {
		this.userAuthorityType = userAuthorityType;
	}

	public UserMonthly getUserMonthly() {
		return userMonthly;
	}

	public void setUserMonthly(UserMonthly userMonthly) {
		this.userMonthly = userMonthly;
	}

	public UserAuthority getUserAuthority() {
		return userAuthority;
	}

	public void setUserAuthority(UserAuthority userAuthority) {
		this.userAuthority = userAuthority;
	}
	
}
