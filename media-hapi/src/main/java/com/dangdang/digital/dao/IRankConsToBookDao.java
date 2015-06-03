package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.RankConsToBook;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

/**
 * Description: 壕赏榜单dao
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 下午5:47:20  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IRankConsToBookDao extends IBaseDao<RankConsToBook>{
	PageFinder<RankConsToBook> getPaperRankConsToBook(Map<String, String> paramMap, Query query);
	
	/**
	 * Description: 根据榜单标识获取壕赏榜单
	 * @Version1.0 2014年12月15日 下午3:30:59 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @param start
	 * @param end
	 * @return
	 */
	List<RankConsToBook> selectRankConsToBookByCode(String code,int start,int end);
	
	
	/**
	 * Description:根据榜单标识获取壕赏榜单总量 
	 * @Version1.0 2014年12月15日 下午3:34:04 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @return
	 */
	int selectRankConsToBookByCodeAmount(String code);
	
	/**
	 * Description: 获取用户该榜单的最高排名
	 * @Version1.0 2015年1月6日 下午8:45:44 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param type
	 * @return
	 */
	int selectTopRankOfCustIdByCode(Long custId,int type);
	
}
