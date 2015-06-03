package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.BoughtDetail;

/**
 * 
 * Description: 已购详情service接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月26日 上午10:50:11 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IBoughtDetailService extends IBaseService<BoughtDetail, Long> {

	/**
	 * 
	 * Description: 获取已购详情列表
	 * 
	 * @Version1.0 2014年12月29日 下午2:40:59 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param boughtId
	 * @param start
	 * @param count
	 * @return
	 */
	public List<BoughtDetail> getBoughtDetailListByBoughtId(Long boughtId, Integer start, Integer count);

	/**
	 * 
	 * Description: 根据boughtId获取boughtDetail数量
	 * 
	 * @Version1.0 2015年1月5日 下午2:49:37 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param boughtId
	 * @return
	 */
	public Integer getBoughtDetailCountByBoughtId(Long boughtId);

}
