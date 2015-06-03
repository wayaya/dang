package com.dangdang.digital.service;

import com.dangdang.digital.model.Prize;

/**
 * Description:奖品配置service接口
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午2:18:44  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IPrizeService extends IBaseService<Prize,Long> {
	
	/**
	 * Description:获取该归属类型下的奖品概率总和 
	 * @Version1.0 2015年1月15日 下午12:03:36 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param vestType
	 * @return
	 */
	double getTotalPro(int vestType);
	
	/**
	 * Description: 获取有效的奖品个数
	 * @Version1.0 2015年1月26日 下午6:09:59 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param vestType
	 * @return
	 */
	Integer getPrizeAmounts(int vestType);

}
