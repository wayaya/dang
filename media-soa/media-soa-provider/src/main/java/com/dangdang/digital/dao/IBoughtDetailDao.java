package com.dangdang.digital.dao;

import java.util.Map;

import com.dangdang.digital.model.BoughtDetail;

/**
 * 
 * Description: 已购详情Dao接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月26日 上午10:45:36 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IBoughtDetailDao extends IBaseDao<BoughtDetail> {

	/**
	 * 
	 * Description: 根据参数查询总数
	 * 
	 * @Version1.0 2015年1月5日 下午2:22:56 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param paramMap
	 * @return
	 */
	public Integer getCount(Map<String, Object> paramMap);

}
