package com.dangdang.digital.dao;

import java.util.Date;
import java.util.List;

import com.dangdang.digital.model.ActivityRecord;

/**
 * Description: 该用户活动参与 dao接口
 * All Rights Reserved.
 * @version 1.0  2014年12月2日 下午5:15:13  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IActivityRecordDao extends IBaseDao<ActivityRecord> {

	/**
	 * Description: 查询当然用户的分享次数
	 * @Version1.0 2014年12月4日 下午3:48:34 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	int selectUserShareTimes(Long custId,int activityType, Date startDate,Date endDate);
	
	/**
	 * Description: 获取该用户今天的膜拜数量
	 * @Version1.0 2014年12月3日 下午4:32:07 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	int selectTodayWorshipTimes(Long custId);
	
	/**
	 * Description: 奖品该日发放量
	 * @Version1.0 2014年12月9日 上午11:14:01 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param prizeId
	 * @return
	 */
	int selectPrizeTodayPutCount(Long prizeId);
	
	/**
	 * Description: 奖品总发放量
	 * @Version1.0 2014年12月9日 上午11:14:47 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param prizeId
	 * @return
	 */
	int selectPrizeTotalPutCount(Long prizeId);
	
	/**
	 * Description: 根据类型，时间，查询该用户的活动参与量
	 * @Version1.0 2014年12月5日 下午5:05:37 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	int selectUserParticipateTimesByType(Long custId,int activityType, Date startDate,Date endDate);
	
	/**
	 * Description:查询玩家的活动历史记录，根据type 
	 * @Version1.0 2014年12月6日 下午3:40:52 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @return
	 */
	List<ActivityRecord> selectUserActivityRecord(Long custId,int activityType);
	
	
	/**
	 * Description: 获取最近的n条抽奖记录  / 前台页面的中奖信息 根据类型 不限制类型填写0
	 * @Version1.0 2014年12月3日 下午12:03:55 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param amount
	 * @return
	 */
	List<ActivityRecord> selectLatestActivityRecord(int activityType,int amount);
	
	/**
	 * Description: 判断是否是第一次抽奖
	 * @Version1.0 2014年12月8日 上午11:48:33 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	boolean judgeFirstLot(Long custId);
	
	/**
	 * Description: 插入一条抽奖记录
	 * @Version1.0 2014年12月2日 下午5:52:09 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param prize
	 */
}
