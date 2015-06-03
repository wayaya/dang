/**
 * Description: UserAuthorityResultVo.java
 * All Rights Reserved.
 * @version 1.0  2014年12月16日 下午3:36:43  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 用户阅读权限验证结果vo
 * All Rights Reserved.
 * @version 1.0  2014年12月16日 下午3:36:43  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class UserAuthorityResultVo implements Serializable {

	/**
	   * @Fields serialVersionUID : TODO 2014年12月16日 下午3:37:09  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = -6542218046797764867L;
	/**
	 * 包月结束时间
	 */
	private Date monthlyEndTime = null;
	/**
	 * 是否拥有阅读权限
	 */
	private boolean hasMediaAuthority = false;
	public Date getMonthlyEndTime() {
		return monthlyEndTime;
	}
	public void setMonthlyEndTime(Date monthlyEndTime) {
		this.monthlyEndTime = monthlyEndTime;
	}
	public boolean isHasMediaAuthority() {
		return hasMediaAuthority;
	}
	public void setHasMediaAuthority(boolean hasMediaAuthority) {
		this.hasMediaAuthority = hasMediaAuthority;
	}

}
