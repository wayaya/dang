package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.Prize;

/**
 * Description: 抽奖dao cache 接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月2日 下午5:49:01 by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface ILotteryCacheDao extends IBaseDao<Prize> {

	/**
	 * Description: 从缓存获取奖品的信息
	 * 
	 * @Version1.0 2014年12月8日 下午5:51:21 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param vestType
	 * @return
	 */
	List<Prize> getPrizeListCache(int vestType);

	/**
	 * Description: 奖品信息保存到缓存
	 * 
	 * @Version1.0 2014年12月23日 下午6:19:12 by 周伟洋（zhouweiyang@dangdang.com）创建
	 */
	void setPrizeListCache(List<Prize> list, int vestType);

	/**
	 * Description: 删除key
	 * 
	 * @Version1.0 2014年12月31日 上午11:01:19 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param vestType
	 */
	void deletePrizeListCache(int vestType);
}
