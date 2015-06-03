package com.dangdang.digital.service;

import com.dangdang.digital.model.ActivityType;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午2:16:52  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public interface IActivityTypeService extends IBaseService<ActivityType, Integer>{
	
	/**添加促销活动类型
	 * @param ActivityType
	 * @return
	 */
	public void insert(ActivityType activityType);

	/**
	 * 删除促销活动类型
	 * @param ActivityType
	 * @return
	 */
	public void delete(Integer id);
}