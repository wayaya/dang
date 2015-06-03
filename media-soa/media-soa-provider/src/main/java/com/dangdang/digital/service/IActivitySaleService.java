package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.ActivitySale;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午2:16:47  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public interface IActivitySaleService extends IBaseService<ActivitySale, Integer>{
	
	/**添加分类树节点
	 * @param ActivitySale
	 * @return
	 */
	public void insert(ActivitySale activitySale);
	
	/**
	 * 删除树节点
	 * @param ActivitySale
	 * @return
	 */
	public void delete(Integer id);
	
	/**
	 * 
	 * Description: 根据活动编号查询火辣限免media的saleId集合(now() between start_time  and end_time)
	 * @Version1.0 2014年12月23日 下午6:18:22 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @return
	 */
	public List<Long> getHotFreeSaleIdList(int activityId);
	
	/**
	 * 
	 * Description: 查询当前火辣限免栏目的最后截止时间
	 * @Version1.0 2014年12月23日 下午6:20:16 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param activityId
	 * @return
	 */
	public String getHotFreeExpireTime(int activityId);
}