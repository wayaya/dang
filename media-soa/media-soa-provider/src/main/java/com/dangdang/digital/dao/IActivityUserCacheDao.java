package com.dangdang.digital.dao;


import com.dangdang.digital.model.ActivityUser;

/**
 * Description: 福袋模块的用户表cache dao
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午2:17:48  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IActivityUserCacheDao extends IBaseDao<ActivityUser>{
	
	/**
	 * Description: 删除key
	 * @Version1.0 2014年12月24日 下午3:42:05 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 */
	void deleteTodayActivityUserCache(Long custId);
	
	
	
	/**
	 * Description: 删除用户的cache信息
	 * @Version1.0 2014年12月25日 上午11:21:34 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 */
	void deleteActivityUserVoCache(Long custId);
	
}
