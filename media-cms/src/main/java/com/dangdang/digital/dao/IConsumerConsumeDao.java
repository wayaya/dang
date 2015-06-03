package com.dangdang.digital.dao;

import com.dangdang.digital.model.ConsumerConsume;

/**
 * 
 * Description: 用户消费信息dao
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午6:20:35  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IConsumerConsumeDao extends IBaseDao<ConsumerConsume>{
	/**
	 * Description: 购买包月人数
	 * All Rights Reserved.
	 * @version 1.0  2015年3月31日 下午7:11:06  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @return
	 */
	public Long getUserCountBuyMonthly(String startDate, String endDate);
	/**
	 * Description: 购买包月次数
	 * All Rights Reserved.
	 * @version 1.0  2015年3月31日 下午7:20:12  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getTimesBuyMonthly(String startDate, String endDate);
	/**
	 * Description: 购买包月金额
	 * All Rights Reserved.
	 * @version 1.0  2015年3月31日 下午7:21:27  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getTotalBuyMonthly(String startDate, String endDate);
	/**
	 * Description: 打赏人数
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 上午10:01:35  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getUserCountReward(String startDate, String endDate);
	/**
	 * Description: 打赏次数
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 上午10:02:57  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getTimesReward(String startDate, String endDate);
	/**
	 * Description: 打赏金额（金铃铛）
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 上午10:04:00  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getTotalReward(String startDate, String endDate);
	/**
	 * Description: 打赏人数
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 上午10:01:35  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getUserCountBuyLuckyBag(String startDate, String endDate);
	/**
	 * Description: 打赏次数
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 上午10:02:57  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getTimesBuyLuckyBag(String startDate, String endDate);
	/**
	 * Description: 打赏金额（金铃铛）
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 上午10:04:00  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getTotalCoinBuyLuckyBag(String startDate, String endDate);
}