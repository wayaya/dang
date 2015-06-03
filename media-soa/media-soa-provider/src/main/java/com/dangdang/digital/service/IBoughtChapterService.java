package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.BoughtChapter;

/**
 * 
 * Description: 已购章节service接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月26日 上午10:49:36 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IBoughtChapterService extends IBaseService<BoughtChapter, Long> {

	/**
	 * 
	 * Description: 获取已购章节列表信息
	 * 
	 * @Version1.0 2014年12月29日 下午2:44:53 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param boughtDetailId
	 * @param start
	 * @param count
	 * @return
	 */
	public List<BoughtChapter> getBoughtChapterListByBoughtDetailId(Long boughtDetailId, Integer start, Integer count);

	/**
	 * 
	 * Description: 根据boughtDetailId获取boughtChapter数量
	 * @Version1.0 2015年1月5日 下午2:50:58 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param boughtDetailId
	 * @return
	 */
	public Integer getBoughtChapterCountByBoughtDetailId(Long boughtDetailId);

}
