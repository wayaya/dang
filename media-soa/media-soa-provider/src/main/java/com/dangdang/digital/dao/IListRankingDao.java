package com.dangdang.digital.dao;


import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.ListRanking;


public interface IListRankingDao extends IBaseDao<ListRanking> {
		 
	public List<Long> getListRankingSaleIdList(Map<String,Object> paramObj);
	
	/**
	 * 
	 * Description: 查询指定榜单类型下media的数量
	 * @Version1.0 2014年12月13日 下午5:02:27 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @return
	 */
	public Long getListRankingSaleCount(Map<String,Object> paramObj);
}