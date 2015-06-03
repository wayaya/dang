package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.RankConsToBook;

/**
 * Description: 壕赏榜单service接口
 * All Rights Reserved.
 * @version 1.0  2014年11月15日 下午3:08:52  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IRankConsToBookService extends IBaseService<RankConsToBook,Long>{
	

	/**
	 * Description: 根据榜单标识获取榜单数据
	 * @Version1.0 2014年12月15日 下午3:33:00 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @param start
	 * @param count
	 * @return
	 */
	List<RankConsToBook> selectUserconsumeBooksByCode(String code,int start,int count);
	
	
	/**
	 * Description: 获取该标识榜单的总数
	 * @Version1.0 2014年12月15日 下午3:27:57 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @return
	 */
	int selectCountRankConsToBookByCode(String code);
}
