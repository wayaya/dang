package com.dangdang.digital.service;
import java.util.List;

import com.dangdang.digital.model.ListRanking;
import com.dangdang.digital.service.IBaseService;

/**
 * ListRanking Manager.
 */
public interface IListRankingService extends IBaseService<ListRanking, Integer> {
	
	/**
	 * 
	 * Description: 查询包含指定saleId的当期的榜单信息
	 * @Version1.0 2015年1月7日 下午5:12:15 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param saleIds
	 * @return
	 */
	public List<ListRanking> getListRankingBySaleIds(List<Long> saleIds);
	/**
	 * 
	 * Description: 根据榜单类型编号,获取该榜单下最新的数据
	 * @Version1.0 2014年12月9日 下午6:02:26 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param offset 请求记录偏移量
	 * @param count 请求记录条数
	 * @param type 榜单类型编号 
	 * @return  榜单表中Media基础数据
	 */
	public List<ListRanking> getListRanking(int offset,int count,String type);
	
	public List<Long> getListRankingSaleIdList(int offset,int count,String type);
	
	/**
	 * 
	 * Description: 根据榜单编号得到该榜单下的media的总数与getListRankingMediaIdList查询一致
	 * @Version1.0 2014年12月13日 下午5:00:03 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param type
	 * @return
	 */
	public Long getListRankingSaleTotalCount(String type);
}
