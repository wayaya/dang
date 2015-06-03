package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.vo.ReturnActivityVo;


/**
 * Description: 该用户活动参与cache dao接口
 * All Rights Reserved.
 * @version 1.0  2014年12月2日 下午5:15:13  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IActivityRecordCacheDao {
	
	/**
	 * Description: 获取用户的活动记录
	 * @Version1.0 2014年12月26日 上午11:51:02 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	List<ReturnActivityVo> getUserActivityRecordCache(Long custId,int type,int prizeType);
	
	/**
	 * Description: 存放活动用户当天的cache
	 * @Version1.0 2014年12月24日 上午11:47:50 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	void setUserActivityRecordCache(Long custId,int type,Integer prizeType,List<ReturnActivityVo> list);
	
	/**
	 * Description: 删除key
	 * @Version1.0 2014年12月24日 下午3:42:05 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 */
	void deleteUserActivityRecordCache(Long custId,int type);
	
	/**
	 * Description: 根据类型获取最近的n条活动记录
	 * @Version1.0 2014年12月26日 下午4:08:22 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param type
	 * @return
	 */
	List<ReturnActivityVo> getLatestActivityRecordCache(int activityType);
	
	/**
	 * Description: 更新cache
	 * @Version1.0 2014年12月26日 下午4:10:45 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param activityType
	 * @param list
	 */
	void setLatestActivityRecordCache(int activityType,List<ReturnActivityVo> list);
	
	/**
	 * Description: 删除key
	 * @Version1.0 2014年12月26日 下午4:10:16 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param activityType
	 */
	void deleteLatestActivityRecordCache(int activityType);
	
	
	/**
	 * Description: 判断该用户是否获取该红包了，如果获取了，直接返回钱！
	 * @Version1.0 2015年2月5日 下午4:01:49 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param redPacketId
	 * @return
	 */
	Integer getGettedRedPacketCoins(Long custId,Long redPacketId);
	
	
	/**
	 * Description: 更新用户是否获取该红包
	 * @Version1.0 2015年2月5日 下午4:03:08 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param redPacketId
	 * @param coins
	 */
	void setGettedRedPacketCoins(Long custId,Long redPacketId,int coins);
	
	/**
	 * Description: 删除key
	 * @Version1.0 2014年12月24日 下午3:42:05 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 */
	void deleteGettedRedPacketCoins(Long custId,Long redPacketId);
}
