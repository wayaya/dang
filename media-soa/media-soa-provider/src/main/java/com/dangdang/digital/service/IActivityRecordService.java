package com.dangdang.digital.service;


import java.util.List;

import com.dangdang.digital.model.ActivityRecord;

/**
 * Description: 活动参与记录 service接口
 * All Rights Reserved.
 * @version 1.0  2014年12月5日 下午3:01:23  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IActivityRecordService extends IBaseService<ActivityRecord, Long> {

	
	/**
	 * Description: 获取用户的活动记录 根据类型
	 * @Version1.0 2014年12月3日 上午11:47:42 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @return
	 */
	List<ActivityRecord> selectUserActivityRecord(Long custId,int activityType);
	
	/**
	 * Description: 获取最近的n条抽奖记录  / 前台页面的中奖信息 根据类型
	 * @Version1.0 2014年12月3日 下午12:03:55 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param amount
	 * @return
	 */
	List<ActivityRecord> selectLatestActivityRecord(int activityType,int amount);
	
}
