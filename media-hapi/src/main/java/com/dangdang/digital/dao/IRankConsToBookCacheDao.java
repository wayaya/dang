package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.vo.ReturnRankConsVo;

/**
 * Description: 壕赏榜单dao
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 下午5:47:20  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IRankConsToBookCacheDao{
	
	/**
	 * Description: 从cache根据榜单标识获取壕赏榜单
	 * @Version1.0 2014年12月27日 下午3:37:09 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @param start
	 * @param end
	 * @return
	 */
	List<ReturnRankConsVo> getRankConsToBookByCodeCache(String code);
	/**
	 * Description:  更新cache壕赏榜单根据榜单标识
	 * @Version1.0 2014年12月27日 下午3:37:09 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @param list
	 */
	void setRankConsToBookByCodeCache(String code,List<ReturnRankConsVo>list);
	
	/**
	 * Description: 删除cache壕赏榜单根据榜单标识
	 * @Version1.0 2014年12月27日 下午3:37:12 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 */
	void deleteRankConsToBookByCodeCache(String code);
	
	
	/**
	 * Description: 获取总榜数据
	 * @Version1.0 2015年1月20日 下午6:16:59 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @return
	 */
	List<ReturnRankConsVo> getRewardTotalRankCache();
	
	/**
	 * Description:  更新cache壕赏榜单根据榜单标识
	 * @Version1.0 2014年12月27日 下午3:37:09 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 * @param list
	 */
	void setRewardTotalRankCache(List<ReturnRankConsVo>list) throws Exception;
	
	/**
	 * Description: 删除cache壕赏榜单根据榜单标识
	 * @Version1.0 2014年12月27日 下午3:37:12 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 */
	void deleteRewardTotalRankCache();
}