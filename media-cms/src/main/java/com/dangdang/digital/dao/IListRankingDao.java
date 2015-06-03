package com.dangdang.digital.dao;

import java.util.Map;

import com.dangdang.digital.model.ListRanking;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;


public interface IListRankingDao extends IBaseDao<ListRanking> {
	
	public int insertBatch(Map<String,Object> paramObj);
	
	public int updateListRankingStatus(Map<String,Object> paramObj);
	
	/**
	 * 
	 * Description: 查出不在榜单里面的单品销售主体
	 * @Version1.0 2014年12月29日 下午2:24:47 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param params
	 * @param query
	 * @return
	 */
	public PageFinder<Map<?, ?>> getSingelSales(Object params, Query query);
	
	
	public PageFinder<ListRanking> getCategoryListRanking(Object params, Query query);
}