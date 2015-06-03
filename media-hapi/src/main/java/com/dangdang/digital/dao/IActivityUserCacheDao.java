package com.dangdang.digital.dao;


import java.util.Map;

import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.vo.ReturnActivityUserVo;

/**
 * Description: 福袋模块的用户表cache dao
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午2:17:48  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IActivityUserCacheDao extends IBaseDao<ActivityUser>{
	
	/**
	 * Description: 获取活动用户当天的cache
	 * @Version1.0 2014年12月24日 上午11:47:50 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	Map<String, String> getTodayActivityUserCache(Long custId) throws Exception;
	
	/**
	 * Description: 存放活动用户当天的cache
	 * @Version1.0 2014年12月24日 上午11:47:50 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	void setTodayActivityUserCache(Long custId,Map<String,String> map) throws Exception;
	
	/**
	 * Description: 删除key
	 * @Version1.0 2014年12月24日 下午3:42:05 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 */
	void deleteTodayActivityUserCache(Long custId);
	
	
	/**
	 * Description: 获取cust_id获取用户cache信息
	 * @Version1.0 2014年12月8日 下午5:25:14 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	ReturnActivityUserVo getActivityUserVoCache(Long custId);
	
	/**
	 * Description: 存放用户信息到cache
	 * @Version1.0 2014年12月24日 下午5:57:26 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 */
	void setActivityUserVoCache(Long custId,ReturnActivityUserVo vo) throws Exception;
	
	/**
	 * Description: 删除用户的cache信息
	 * @Version1.0 2014年12月25日 上午11:21:34 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 */
	void deleteActivityUserVoCache(Long custId);
	
}
