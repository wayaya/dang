package com.dangdang.digital.service;
import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.ListRanking;
import com.dangdang.digital.service.IBaseService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

/**
 * ListRanking Manager.
 */
public interface IListRankingService extends IBaseService<ListRanking, Long> {
	
	public int insertBatch(Map<String,Object> paramObj);
	/**
	 * 
	 * Description: 批理更新 ListRanking
	 * @Version1.0 2014年12月22日 下午4:15:39 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param listRanking
	 * @return
	 */
	public int updateListRanking(List<ListRanking> listRanking);
	
	/**
	 * 
	 * Description: 	更新榜单数据状态
	 * @Version1.0 2014年12月24日 上午11:50:21 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param stats		状态值
	 * @param listIds	榜单内容编号
	 * @param modifer	修改人
	 * @return
	 */
	public int updateListRankingStatus(int stats,String listIds,String modifer);
	
	
	public PageFinder<Map<?, ?>> getSingelSales(Object params, Query query);
	
	
	/**
	 * 
	 * Description:再分类查看每个分类的各维度榜单数据
	 * @Version1.0 2015年4月3日 下午2:12:57 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param params
	 * @param query
	 * @return
	 */
	public PageFinder<ListRanking> getCategoryListRanking(Object params, Query query);
}
