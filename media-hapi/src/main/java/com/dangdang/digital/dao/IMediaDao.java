package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Media;

public interface IMediaDao extends IBaseDao<Media> {

	public void saveCatetorys(Media media);

	/**
	 * 
	 * Description: 获取作者所有书籍列表
	 * @Version1.0 2014年12月13日 下午4:56:43 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param paramMap
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Long> getAllMediasByAuthor(Map<String, Object> paramMap, Integer start, Integer count);

	/**
	 * 
	 * Description: 获取作者所有书籍数量
	 * @Version1.0 2014年12月13日 下午5:07:57 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param paramMap
	 * @return
	 */
	public Long getMediasCountByAuthor(Map<String, Object> paramMap);
}
