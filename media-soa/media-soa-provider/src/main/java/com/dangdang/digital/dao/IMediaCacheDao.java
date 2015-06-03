package com.dangdang.digital.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.dangdang.digital.model.Media;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheWholeVo;

/**
 * 
 * Description: media缓存dao All Rights Reserved.
 * 
 * @version 1.0 2014年12月6日 上午10:55:44 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IMediaCacheDao {

	/**
	 * 
	 * Description: 获取media basic缓存，如果没有从数据库获取并加入缓存
	 * 
	 * @Version1.0 2014年12月6日 上午11:24:19 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public MediaCacheBasicVo getMediaCacheBasicVo(Long mediaId) throws IllegalAccessException,
			InvocationTargetException;

	/**
	 * 
	 * Description: 保存media basic缓存
	 * 
	 * @Version1.0 2014年12月6日 上午11:50:23 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaCacheBasicVo
	 * @return
	 */
	public MediaCacheBasicVo setMediaCacheBasicVo(Media media);

	/**
	 * 
	 * Description: 删除media basic缓存
	 * 
	 * @Version1.0 2014年12月6日 下午6:15:12 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaId
	 */
	public void deleteCacheBasicVo(Long mediaId);

	/**
	 * 
	 * Description:获取media whole缓存，如果没有从数据库获取并加入缓存
	 * 
	 * @Version1.0 2014年12月6日 下午6:16:22 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public MediaCacheWholeVo getMediaCacheWholeVo(Long mediaId) throws IllegalAccessException,
			InvocationTargetException;

	/**
	 * 
	 * Description:增加media whole缓存
	 * 
	 * @Version1.0 2014年12月6日 下午6:16:25 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param media
	 * @return
	 */
	public MediaCacheWholeVo setMediaCacheWholeVo(Media media);

	/**
	 * 
	 * Description:删除media whole缓存
	 * 
	 * @Version1.0 2014年12月6日 下午6:16:29 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaId
	 */
	public void deleteCacheWholeVo(Long mediaId);

	/**
	 * 
	 * Description:批量获取media basic缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:02 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaIds
	 * @return
	 */
	public List<MediaCacheBasicVo> mGetMediaCacheBasicVo(List<Long> mediaIds);

	/**
	 * 
	 * Description:批量设置media basic缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:06 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param basicVoMap
	 */
	public void mSetMediaCacheBasicVo(List<Media> medias);

	/**
	 * 
	 * Description:批量删除media basic缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:12 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaIds
	 */
	public void mDeleteCacheBasicVo(List<Long> mediaIds);

	/**
	 * 
	 * Description:批量获取media whole缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:15 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaIds
	 * @return
	 */
	public List<MediaCacheWholeVo> mGetMediaCacheWholeVo(List<Long> mediaIds);

	/**
	 * 
	 * Description:批量设置media whole缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:19 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param wholeVoMap
	 */
	public void mSetMediaCacheWholeVo(List<Media> medias);

	/**
	 * 
	 * Description:批量删除media whole缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:22 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaIds
	 */
	public void mDeleteCacheWholeVo(List<Long> mediaIds);
	
	/**
	 * 
	 * Description: 清除media whole缓存
	 * 
	 * @Version1.0 2014年12月8日 下午2:11:25 by 许文轩（xuwenxuan@dangdang.com）创建
	 */
	public void cleanWholeVo();

	/**
	 * 
	 * Description: 清除media basic缓存
	 * 
	 * @Version1.0 2014年12月8日 下午2:11:53 by 许文轩（xuwenxuan@dangdang.com）创建
	 */
	public void cleanBasicVo();

}
