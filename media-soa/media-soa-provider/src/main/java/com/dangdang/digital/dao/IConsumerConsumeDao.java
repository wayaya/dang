package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.ConsumerConsume;

/**
 * 
 * Description: 用户消费信息dao All Rights Reserved.
 * 
 * @version 1.0 2014年11月13日 下午6:20:35 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IConsumerConsumeDao extends IBaseDao<ConsumerConsume> {

	/**
	 * Description: 获取用户打赏的书的id 列表
	 * 
	 * @Version1.0 2014年12月6日 上午11:15:30 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	List<Long> selectUserconsumeBooksIds(Long custId);

	/**
	 * 
	 * Description: 获取其它消费列表
	 * 
	 * @Version1.0 2015年1月5日 上午9:38:34 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Map<String, Object>> getOtherConsumeList(Long custId, Integer start, Integer count);

	/**
	 * 
	 * Description:根据参数查询数量
	 * 
	 * @Version1.0 2015年1月5日 下午2:57:18 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param paramMap
	 * @return
	 */
	public Integer getCount(Map<String, Object> paramMap);

}