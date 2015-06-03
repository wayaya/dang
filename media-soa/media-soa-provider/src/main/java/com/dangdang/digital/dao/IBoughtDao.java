package com.dangdang.digital.dao;

import java.util.Map;

import com.dangdang.digital.model.Bought;

/**
 * 
 * Description: 已购信息dao接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月26日 上午10:45:50 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IBoughtDao extends IBaseDao<Bought> {

	/**
	 * 
	 * Description: 增量更新已购信息
	 * 
	 * @Version1.0 2015年1月4日 上午10:59:14 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param bought
	 * @return
	 */
	public int updateIncremental(Bought bought);

	/**
	 * 
	 * Description: 根据参数查询总数
	 * 
	 * @Version1.0 2015年1月5日 下午2:21:55 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param paramMap
	 * @return
	 */
	public Integer getCount(Map<String, Object> paramMap);

}
