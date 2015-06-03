package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.RewardTop;
import com.dangdang.digital.vo.IncomeVo;

/**
 * Description: 数据统计
 * All Rights Reserved.
 * @version 1.0  2015年3月31日 下午2:46:39  by yangzhenping（yangzhenping@dangdang.com）创建
 */
public interface IStatisticsService {
	
	/**
	 * Description: 统计平台收入信息
	 * All Rights Reserved.
	 * @version 1.0  2015年3月31日 下午2:50:09  by yangzhenping（yangzhenping@dangdang.com）创建
	 * @return
	 */
	public IncomeVo getIncomeVo(String startDate, String endDate);
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
