package com.dangdang.digital.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.ChapterCacheWholeVo;

/**
 * 
 * Description: chapter缓存dao All Rights Reserved.
 * 
 * @version 1.0 2014年12月6日 上午10:55:44 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IChapterCacheDao {

	/**
	 * 
	 * Description: 获取chapterbasic缓存，如果没有从数据库获取并加入缓存
	 * 
	 * @Version1.0 2014年12月6日 上午11:24:19 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ChapterCacheBasicVo getChapterCacheBasicVo(Long chapterId) throws IllegalAccessException,
			InvocationTargetException;

	/**
	 * 
	 * Description: 保存chapterbasic缓存
	 * 
	 * @Version1.0 2014年12月6日 上午11:50:23 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterCacheBasicVo
	 * @return
	 */
	public ChapterCacheBasicVo setChapterCacheBasicVo(Chapter chapter);

	/**
	 * 
	 * Description: 删除chapterbasic缓存
	 * 
	 * @Version1.0 2014年12月6日 下午6:15:12 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterId
	 */
	public void deleteCacheBasicVo(Long chapterId);

	/**
	 * 
	 * Description:获取chapterwhole缓存，如果没有从数据库获取并加入缓存
	 * 
	 * @Version1.0 2014年12月6日 下午6:16:22 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ChapterCacheWholeVo getChapterCacheWholeVo(Long chapterId) throws IllegalAccessException,
			InvocationTargetException;

	/**
	 * 
	 * Description:增加chapterwhole缓存
	 * 
	 * @Version1.0 2014年12月6日 下午6:16:25 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapter
	 * @return
	 */
	public ChapterCacheWholeVo setChapterCacheWholeVo(Chapter chapter);

	/**
	 * 
	 * Description:删除chapterwhole缓存
	 * 
	 * @Version1.0 2014年12月6日 下午6:16:29 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterId
	 */
	public void deleteCacheWholeVo(Long chapterId);

	/**
	 * 
	 * Description:批量获取chapter basic缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:02 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterIds
	 * @return
	 */
	public List<ChapterCacheBasicVo> mGetChapterCacheBasicVo(List<Long> chapterIds);

	/**
	 * 
	 * Description:批量设置chapter basic缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:06 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param basicVoMap
	 */
	public void mSetChapterCacheBasicVo(List<Chapter> chapters);

	/**
	 * 
	 * Description:批量删除chapter basic缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:12 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterIds
	 */
	public void mDeleteCacheBasicVo(List<Long> chapterIds);

	/**
	 * 
	 * Description:批量获取chapter whole缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:15 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterIds
	 * @return
	 */
	public List<ChapterCacheWholeVo> mGetChapterCacheWholeVo(List<Long> chapterIds);

	/**
	 * 
	 * Description:批量设置chapter whole缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:19 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param wholeVoMap
	 */
	public void mSetChapterCacheWholeVo(List<Chapter> chapters);

	/**
	 * 
	 * Description:批量删除chapter whole缓存
	 * 
	 * @Version1.0 2014年12月8日 上午10:56:22 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterIds
	 */
	public void mDeleteCacheWholeVo(List<Long> chapterIds);

	/**
	 * 
	 * Description: 清除chapter whole缓存
	 * 
	 * @Version1.0 2014年12月8日 下午2:11:25 by 许文轩（xuwenxuan@dangdang.com）创建
	 */
	public void cleanWholeVo();

	/**
	 * 
	 * Description: 清除chapter basic缓存
	 * 
	 * @Version1.0 2014年12月8日 下午2:11:53 by 许文轩（xuwenxuan@dangdang.com）创建
	 */
	public void cleanBasicVo();

}
