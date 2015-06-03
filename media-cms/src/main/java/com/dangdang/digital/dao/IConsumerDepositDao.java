package com.dangdang.digital.dao;

import com.dangdang.digital.model.ConsumerDeposit;

/**
 * 
 * Description: 用户充值记录dao
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午6:20:51  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IConsumerDepositDao  extends IBaseDao<ConsumerDeposit>{
	/**
	 * Description: 充值人数
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 上午10:15:23  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getUserCountDeposit(String startDate, String endDate);
	/**
	 * Description: 充值次数
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 上午10:15:36  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getTimesDeposit(String startDate, String endDate);
	/**
	 * Description: 充值总金额
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 上午10:15:49  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getTotalDeposit(String startDate, String endDate);
}