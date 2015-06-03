package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Media;

public interface IMediaDao extends IBaseDao<Media> {

	public void saveCatetorys(Media media);

	/**
	 * 
	 * Description: 通过saleid获取media列表
	 * @Version1.0 2014年12月11日 上午9:47:17 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param saleId
	 * @return
	 */
	public List<Long> getMediaListBySaleId(Long saleId);

	/**
	 * 
	 * Description: 通过mediaIds获取分类信息
	 * @Version1.0 2014年12月17日 下午4:35:18 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaIds
	 * @return
	 */
	public List<Map<String, Object>> getCategorysByMediaId(List<Long> mediaIds);

	/**
	 * 
	 * Description: 通过mediaId获取分类
	 * @Version1.0 2014年12月17日 下午4:40:17 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaId
	 * @return
	 */
	public Map<String, Object> getCategorysByMediaId(Long mediaId);

}
