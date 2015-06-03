package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.RewardTop;


/**
 * Description: 电子书金币消费壕记录表的dao接口
 * All Rights Reserved.
 * @version 1.0  2015年4月1日 下午7:33:48  by yangzhenping（yangzhenping@dangdang.com）创建
 */
public interface IEbookConsRecordDao {
	/**
	 * Description: 打赏金额top100
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 下午7:10:20  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @param count
	 * @return
	 */
	public List<RewardTop> getRewardTop(String startDate, String endDate, Integer count);
	/**
	 * Description: 金铃铛Top100
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 下午7:10:20  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @param count
	 * @return
	 */
	public List<RewardTop> getBuyChapterGlobTop(String startDate, String endDate, Integer count);
	/**
	 * Description: 银铃铛Top100
	 * All Rights Reserved.
	 * @version 1.0  2015年4月1日 下午7:10:20  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @param count
	 * @return
	 */
	public List<RewardTop> getBuyChapterSilverTop(String startDate, String endDate, Integer count);
}
