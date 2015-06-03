package com.dangdang.digital.dao;

import java.util.Map;

import com.dangdang.digital.model.BoughtChapter;

/**
 * 
 * Description: 已购章节信息接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月26日 上午10:46:07 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IBoughtChapterDao extends IBaseDao<BoughtChapter> {

	/**
	 * 
	 * Description: 根据参数查询数量
	 * 
	 * @Version1.0 2015年1月5日 下午2:23:53 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param paramMap
	 * @return
	 */
	public Integer getCount(Map<String, Object> paramMap);

}
