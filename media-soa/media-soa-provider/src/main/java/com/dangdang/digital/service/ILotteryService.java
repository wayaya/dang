package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.Prize;

/**
 * Description: 抽奖的service接口
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 上午10:32:12  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface ILotteryService extends IBaseService<Prize, Long>{
	
	/**
	 * Description: 抽奖
	 * @Version1.0 2014年12月8日 上午10:33:47 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param username
	 * @return
	 */
	List<Prize> lotPrize(Long custId,String username)throws Exception ;

}
