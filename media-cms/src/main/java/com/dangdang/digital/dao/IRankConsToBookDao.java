package com.dangdang.digital.dao;

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
//    RankConsToBook selectByPrimaryKey(Integer id);
	PageFinder<RankConsToBook> getPaperRankConsToBook(Map<String, String> paramMap, Query query);
}