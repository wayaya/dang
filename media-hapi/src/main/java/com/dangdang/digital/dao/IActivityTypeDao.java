package com.dangdang.digital.dao;

import com.dangdang.digital.model.ActivityType;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午2:16:52  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public interface IActivityTypeDao extends IBaseDao<ActivityType>{
	
	/**添加促销活动类型
	 * @param ActivityType
	 * @return
	 */
	public void insert(ActivityType activityType);
	
	/**修改促销活动类型
	 * @param ActivityType
	 * @return
	 */
	public void update(ActivityType activityType);
	/**
	 * 删除树节点
	 * @param ActivityType
	 * @return
	 */
	public void delete(Integer id);
}