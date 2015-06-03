package com.dangdang.digital.dao;

import com.dangdang.digital.model.Prize;

/**
 * Description:奖品配置dao 
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午2:18:44  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IPrizeDao extends IBaseDao<Prize> {

	/**
	 * Description: 查询该归属类型下的奖品概率综合
	 * @Version1.0 2015年1月15日 下午12:05:09 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param vestType
	 * @return
	 */
	double selectTotalPro(int vestType);
	
	/**
	 * Description:查询有效的奖品个数 
	 * @Version1.0 2015年1月26日 下午6:10:55 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param vestType
	 * @return
	 */
	Integer selectPrizeAmounts(int vestType);
	
}
