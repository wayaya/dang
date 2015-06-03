package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.RankConsToBook;

/**
 * Description:人对数书的消费排行榜（壕赏榜）Service接口
 * All Rights Reserved.
 * @version 1.0  2014年12月2日 下午2:54:37  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IPersonRankService extends IBaseService<RankConsToBook, Long> {
	
	
	/**
	 * Description:  获取前 amount 条排行榜数据
	 * @Version1.0 2014年12月2日 下午3:04:02 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @param amount
	 * @return
	 */
	public List<RankConsToBook> findListForTopAmount(String code,Integer amount);
	
}
