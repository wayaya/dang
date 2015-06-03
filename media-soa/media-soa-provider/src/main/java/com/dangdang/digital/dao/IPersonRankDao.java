package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.RankConsToBook;

/**
 * Description: 人对数书的消费排行榜（壕赏榜）dao接口
 * All Rights Reserved.
 * @version 1.0  2014年12月2日 下午3:11:51  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IPersonRankDao extends IBaseDao<RankConsToBook> {
		
	/**
	 * Description: 获取前amount条数据
	 * @Version1.0 2014年12月2日 下午3:15:54 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @param amount
	 * @return
	 */
	public List<RankConsToBook> findListForTopAmount(String code,Integer amount);
}
