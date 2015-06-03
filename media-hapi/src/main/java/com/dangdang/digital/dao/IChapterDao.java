package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Chapter;

public interface IChapterDao extends IBaseDao<Chapter> {
	
	/**
	 * 
	 * Description: 根据图书id查询最大章节号
	 * 
	 * @Version1.0 2014年12月1日 下午2:04:39 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaId
	 * @return
	 */
	public Integer getMaxIndexOrderByMediaId(Long mediaId);

	/**
	 * 
	 * Description: 根据图书id查询章节数量
	 * 
	 * @Version1.0 2014年12月3日 下午5:07:00 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param paramMap
	 * @return
	 */
	public Integer getCountByMediaId(Map<String, Object> paramMap);

	/**
	 * 
	 * Description: 根据书籍id，开始和数量查询章节列表
	 * 
	 * @Version1.0 2014年12月4日 上午11:32:16 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param paramMap
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Chapter> getAllChapterByMediaId(Map<String, Object> paramMap, Integer start, Integer count);

	/**
	 * 
	 * Description: 通过书籍id集合获取章节数量
	 * 
	 * @Version1.0 2014年12月5日 下午6:20:59 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaIds
	 * @return
	 */
	public List<Map<String, Object>> getMaxIndexOrderByMediaIds(List<Long> mediaIds);
}
