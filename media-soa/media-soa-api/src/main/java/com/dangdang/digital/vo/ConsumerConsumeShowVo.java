/**
 * Description: ConsumerConsumeShowVo.java
 * All Rights Reserved.
 * @version 1.0  2014年12月31日 上午10:45:07  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.List;

import com.dangdang.digital.model.ActivityInfo;

/**
 * Description: 购买包月显示vo
 * All Rights Reserved.
 * @version 1.0  2014年12月31日 上午10:45:07  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class ConsumerConsumeShowVo implements Serializable {

	/**
	   * @Fields serialVersionUID : TODO 2014年12月31日 上午10:46:56  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = -3123392548665477025L;

	/**
	 * 用户id
	 */
	private Long custId;

	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 主账户余额
	 */
	private Integer mainBalance;
	
	/**
	 * 副账户余额
	 */
	private Integer subBalance;
	
	/**
	 * 主账户余额IOS
	 */
	private Integer mainBalanceIOS;
	/**
	 * 副账户余额IOS
	 */
	private Integer subBalanceIOS;
	
	private List<ActivityInfo> activityInfos;

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getMainBalance() {
		return mainBalance;
	}

	public void setMainBalance(Integer mainBalance) {
		this.mainBalance = mainBalance;
	}

	public Integer getSubBalance() {
		return subBalance;
	}

	public void setSubBalance(Integer subBalance) {
		this.subBalance = subBalance;
	}

	public List<ActivityInfo> getActivityInfos() {
		return activityInfos;
	}

	public void setActivityInfos(List<ActivityInfo> activityInfos) {
		this.activityInfos = activityInfos;
	}

	public Integer getMainBalanceIOS() {
		return mainBalanceIOS;
	}

	public void setMainBalanceIOS(Integer mainBalanceIOS) {
		this.mainBalanceIOS = mainBalanceIOS;
	}

	public Integer getSubBalanceIOS() {
		return subBalanceIOS;
	}

	public void setSubBalanceIOS(Integer subBalanceIOS) {
		this.subBalanceIOS = subBalanceIOS;
	}

}
