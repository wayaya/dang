package com.dangdang.digital.dao;

import com.dangdang.digital.model.OrderMain;

/**
 * 
 * Description: 主订单dao
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午6:22:56  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IOrderMainDao extends IBaseDao<OrderMain>{
	/**
	 * Description: 单章购买人数
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 上午10:01:35  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getUserCountBuyChapter(String startDate, String endDate);
	/**
	 * Description: 单章购买次数
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 上午10:02:57  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getTimesBuyChapter(String startDate, String endDate);
	/**
	 * Description: 单张购买金铃铛总额
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 上午10:04:00  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getTotalGoldBuyChapter(String startDate, String endDate);
	/**
	 * Description: 单张购买银铃铛总额
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 上午10:04:00  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getSilverBuyChapter(String startDate, String endDate);
}