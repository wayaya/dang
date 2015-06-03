package com.dangdang.digital.dao;

import java.util.List;

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
	
	/**
	 * 批量添加分类树节点
	 * @param ActivitySale
	 * @return
	 */
	public void batchInsert(List<ActivitySale> activitySaleList);
	
	/**
	 * 根据ids获取一口价销售对象
	 * @param ActivitySale
	 * @return
	 */
	public List<ActivitySale> findByIds(List<Integer> ids);
	
	
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