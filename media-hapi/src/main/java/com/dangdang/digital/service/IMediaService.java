package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.exception.ApiException;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.vo.MediaCacheBasicVo;

public interface IMediaService extends IBaseService<Media, Long> {

	public void saveCateSign(Media media, String cateIds);

	Map<Long, MediaCacheBasicVo> getMediaBasicCache(List<Long> buyAlsoBuyIds);

	/**
	 * 
	 * Description:
	 * 
	 * @Version1.0 2014年12月11日 下午4:51:44 by 魏嵩（weisong@dangdang.com）创建 调用dubbo接口
	 * @param buyAlsoBuyIds
	 * @return
	 */
	Map<Long, MediaCacheBasicVo> getMediaBasicMap(List<Long> buyAlsoBuyIds);

	/**
	 * 
	 * Description: 获取作者最畅销的两本书
	 * 
	 * @Version1.0 2014年12月13日 上午8:38:35 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param authorId
	 * @param channelType
	 * @return
	 * @throws ApiException
	 */
	public List<Long> getMediasByAuthorExceptThis(Long authorId, String channelType, Long mediaId, Integer start,
			Integer count) throws ApiException;

	/**
	 * 
	 * Description: 获取作者所有书的列表，按照销量排序
	 * 
	 * @Version1.0 2014年12月13日 下午5:24:17 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param authorId
	 * @param channelType
	 * @param start
	 * @param count
	 * @return
	 * @throws ApiException
	 */
	public List<Long> getAllMediasByAuthor(Long authorId, String channelType, Integer start, Integer count, Long mediaId)
			throws ApiException;

	/**
	 * 
	 * Description: 获取作者所有书的数量
	 * 
	 * @Version1.0 2014年12月13日 下午5:24:34 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param authorId
	 * @param channelType
	 * @return
	 */
	public Long getMediasCountByAuthor(Long authorId, String channelType);

}
