package com.dangdang.digital.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.dangdang.digital.model.Author;
import com.dangdang.digital.vo.AuthorCacheVo;

/**
 * 
 * Description: Author缓存dao All Rights Reserved.
 * 
 * @version 1.0 2014年12月6日 上午10:55:44 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IAuthorCacheDao {

	/**
	 * 
	 * Description: 获取Author 缓存，如果没有从数据库获取并加入缓存
	 * 
	 * @Version1.0 2014年12月6日 上午11:24:19 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param AuthorId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public AuthorCacheVo getAuthorCacheVo(Long authorId) throws IllegalAccessException, InvocationTargetException,
			Exception;

	/**
	 * 
	 * Description: 保存Author 缓存
	 * 
	 * @Version1.0 2014年12月6日 上午11:50:23 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param AuthorCacheVo
	 * @return
	 * @throws Exception
	 */
	public AuthorCacheVo setAuthorCacheVo(Author author) ;

	/**
	 * 
	 * Description: 删除Author 缓存
	 * 
	 * @Version1.0 2014年12月6日 下午6:15:12 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param AuthorId
	 */
	public void deleteCacheVo(Long authorId);

	/**
	 * 
	 * Description:批量获取Author 缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:02 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param AuthorIds
	 * @return
	 */
	public List<AuthorCacheVo> mGetAuthorCacheVo(List<Long> authorIds);

	/**
	 * 
	 * Description: 批量设置mediasale缓存
	 * 
	 * @Version1.0 2014年12月11日 上午11:42:36 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param Authors
	 * @param batchKey
	 * @throws Exception
	 */
	public void mSetAuthorCacheVo(List<Author> authors) ;

	/**
	 * 
	 * Description:批量删除Author 缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:12 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param AuthorIds
	 */
	public void mDeleteCacheVo(List<Long> authorIds);

	/**
	 * 
	 * Description: 清除Author 缓存
	 * 
	 * @Version1.0 2014年12月8日 下午2:11:25 by 许文轩（xuwenxuan@dangdang.com）创建
	 */
	public void cleanAuthorCache();

}
