package com.dangdang.digital.dao;


import com.dangdang.digital.model.ActivityUser;

/**
 * Description: 福袋模块的用户表dao
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午2:17:48  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IActivityUserDao extends IBaseDao<ActivityUser>{
		
	/**
	 * Description: 增加一次膜拜次数
	 * @Version1.0 2014年12月2日 上午11:38:12 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 */
	void addOneWorshipTime(Long custId,String nickname);
	
	/**
	 * Description: 增加一次被膜拜次数
	 * @Version1.0 2014年12月2日 上午11:38:21 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 */
	void addOneWorshipedTime(Long custId,String nickname);
	
	/**
	 * Description:  修改指定用户的抽奖次数,增加指定的已用次数，如果是只增加，lottedTime 传入0
	 * @Version1.0 2014年12月8日 下午5:31:16 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param times
	 * @param lottedTime
	 */
	void addLotTime(Long custId,Integer times,Integer lottedTime,String nickname);
	
	/**
	 * Description: 增加一次分享次数
	 * @Version1.0 2014年12月2日 下午12:02:22 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 */
	void addOneShareTime(Long custId,String nickname);
	
	
	/**
	 * Description: 根据cust_id获取用户信息
	 * @Version1.0 2014年12月8日 下午5:25:14 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	ActivityUser selectUserByCustID(Long custId) throws Exception;
}
