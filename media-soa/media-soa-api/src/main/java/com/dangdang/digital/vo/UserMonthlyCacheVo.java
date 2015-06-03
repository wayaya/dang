/**
 * Description: UserMonthlyCacheVo.java
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午6:03:10  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.dangdang.digital.model.UserMonthly;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午6:03:10  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class UserMonthlyCacheVo implements Serializable {

	/**
	   * @Fields serialVersionUID : TODO 2014年12月15日 下午6:03:20  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = 6571274207590284997L;

	/**
	 * 用户包月列表
	 */
	Map<String,UserMonthly> userMonthlys = new HashMap<String,UserMonthly>();

	public Map<String, UserMonthly> getUserMonthlys() {
		return userMonthlys;
	}

	public void setUserMonthlys(Map<String, UserMonthly> userMonthlys) {
		this.userMonthlys = userMonthlys;
	}

	
}
