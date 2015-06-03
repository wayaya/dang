package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.Prize;

/**
 * Description: 抽奖dao 接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月2日 下午5:49:01 by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface ILotteryDao extends IBaseDao<Prize> {

	/**
	 * Description: 获取奖品的信息
	 * 
	 * @Version1.0 2014年12月8日 下午5:51:21 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param vestType
	 * @return
	 */
	List<Prize> getPrizeList(int vestType);

}
