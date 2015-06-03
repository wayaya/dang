package com.dangdang.digital.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.vo.MediaSaleCacheVo;

/**
 * 
 * Description: mediaSale缓存dao All Rights Reserved.
 * 
 * @version 1.0 2014年12月6日 上午10:55:44 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IMediaSaleCacheDao {

	/**
	 * 
	 * Description: 获取mediaSale 缓存，如果没有从数据库获取并加入缓存
	 * 
	 * @Version1.0 2014年12月6日 上午11:24:19 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaSaleId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception 
	 */
	public MediaSaleCacheVo getMediaSaleCacheVo(Long mediaSaleId) throws IllegalAccessException,
			InvocationTargetException, Exception;

	/**
	 * 
	 * Description: 保存mediaSale 缓存
	 * 
	 * @Version1.0 2014年12月6日 上午11:50:23 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaSaleCacheVo
	 * @return
	 * @throws Exception 
	 */
	public MediaSaleCacheVo setMediaSaleCacheVo(MediaSale mediaSale) throws Exception;

	/**
	 * 
	 * Description: 删除mediaSale 缓存
	 * 
	 * @Version1.0 2014年12月6日 下午6:15:12 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaSaleId
	 */
	public void deleteCacheVo(Long mediaSaleId);

	/**
	 * 
	 * Description:批量获取mediaSale 缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:02 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaSaleIds
	 * @return
	 */
	public List<MediaSaleCacheVo> mGetMediaSaleCacheVo(List<Long> mediaSaleIds);

	/**
	 * 
	 * Description: 批量设置mediasale缓存
	 * @Version1.0 2014年12月11日 上午11:42:36 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaSales
	 * @param batchKey
	 * @throws Exception
	 */
	public void mSetMediaSaleCacheVo(List<MediaSale> mediaSales) throws Exception;

	/**
	 * 
	 * Description:批量删除mediaSale 缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:12 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaSaleIds
	 */
	public void mDeleteCacheVo(List<Long> mediaSaleIds);

	/**
	 * 
	 * Description: 清除mediaSale 缓存
	 * 
	 * @Version1.0 2014年12月8日 下午2:11:25 by 许文轩（xuwenxuan@dangdang.com）创建
	 */
	public void cleanMediaSaleCache();

}
