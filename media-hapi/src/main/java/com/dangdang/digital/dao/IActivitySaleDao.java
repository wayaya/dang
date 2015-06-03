package com.dangdang.digital.dao;

import com.dangdang.digital.model.ActivitySale;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午2:16:47  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public interface IActivitySaleDao extends IBaseDao<ActivitySale>{
	
	/**添加分类树节点
	 * @param ActivitySale
	 * @return
	 */
	public void insert(ActivitySale activitySale);
	
	/**修改树节点
	 * @param ActivitySale
	 * @return
	 */
	public void update(ActivitySale activitySale);
	/**
	 * 删除树节点
	 * @param ActivitySale
	 * @return
	 */
	public void delete(Integer id);
	
}