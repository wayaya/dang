package com.dangdang.digital.dao;

import com.dangdang.digital.model.ActivityInfo;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午2:16:40  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public interface IActivityInfoDao extends IBaseDao<ActivityInfo>{
	
	/**添加分类树节点
	 * @param ActivityInfoQueryVo
	 * @return
	 */
	public void insert(ActivityInfo activityInfo);
	
	/**修改树节点
	 * @param ActivityInfoQueryVo
	 * @return
	 */
	public void update(ActivityInfo activityInfo);
	/**
	 * 删除树节点
	 * @param ActivityInfoQueryVo
	 * @return
	 */
	public void delete(Integer id);
}