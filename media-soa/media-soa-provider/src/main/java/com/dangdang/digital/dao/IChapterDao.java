package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.Chapter;

public interface IChapterDao extends IBaseDao<Chapter> {

	/**
	 * 
	 * Description: 获取书的章节列表,根据orderindex排序
	 * 
	 * @Version1.0 2014年12月15日 下午4:27:14 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaId
	 * @return
	 */
	public List<Chapter> getAllChapterByMediaId(Long mediaId);

	/**
	 * 
	 * Description: 获取书的章节id列表，根据orderindex排序
	 * 
	 * @Version1.0 2014年12月15日 下午5:30:12 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaId
	 * @return
	 */
	public List<Long> getAllChapterIdsByMediaId(Long mediaId);

	/**
	 * 
	 * Description: 获取书的章节id列表，根据orderindex排序
	 * 
	 * @Version1.0 2014年12月15日 下午5:56:27 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param paramMap
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Long> getAllChapterIdsByMediaId(Long mediaId, Integer start, Integer count);

	/**
	 * 
	 * Description: 获取章节数量
	 * 
	 * @Version1.0 2014年12月15日 下午5:55:31 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param paramMap
	 * @return
	 */
	public Integer getCountByMediaId(Long mediaId);

}
