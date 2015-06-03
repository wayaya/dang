package com.dangdang.digital.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.dangdang.digital.exception.ApiException;
import com.dangdang.digital.model.Chapter;

public interface IChapterService extends IBaseService<Chapter, Long> {

	/**
	 * 
	 * Description: 通过章节id获取http下载路径
	 * 
	 * @Version1.0 2014年11月29日 上午9:02:05 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterId
	 * @return
	 */
	public Chapter getHttpPathByChapterId(Long chapterId);

	/**
	 * 
	 * Description: 根据传入章节id获取zip
	 * 
	 * @Version1.0 2014年11月29日 上午10:44:04 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterIds
	 * @return
	 * @throws ApiException
	 */
	public File getZipByChapterIds(List<Long> chapterIds) throws ApiException;

	/**
	 * 
	 * Description: 判断章节是否是该图书的最后一章
	 * 
	 * @Version1.0 2014年12月1日 下午2:08:22 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param MediaId
	 * @return
	 */
	public boolean isTheLastChapter(Long MediaId);

	/**
	 * 
	 * Description: 根据图书id获取所有章节信息
	 * 
	 * @Version1.0 2014年12月4日 上午11:47:26 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaId
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Chapter> getAllChapterByMediaIds(Long mediaId, Integer start, Integer count);

	/**
	 * 
	 * Description: 根据图书id获取所有章节信息
	 * 
	 * @Version1.0 2014年12月4日 上午11:47:29 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaId
	 * @return
	 */
	public List<Chapter> getAllChapterByMediaIds(Long mediaId);

	/**
	 * 
	 * Description: 验证章节是否是免费章节
	 * 
	 * @Version1.0 2014年12月4日 上午10:01:23 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterIdList
	 * @return
	 */
	public boolean isFreeChapters(List<Long> chapterIdList);

	/**
	 * 
	 * Description: 验证章节是否免费
	 * 
	 * @Version1.0 2014年12月4日 上午10:11:47 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterId
	 * @return
	 */
	public boolean isFreeChapters(Long chapterId);

	/**
	 * 
	 * Description: 根据书籍id获取章节数量
	 * 
	 * @Version1.0 2014年12月4日 上午11:55:48 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaId
	 * @return
	 * @throws ApiException
	 */
	public Integer getCountByMediaId(Long mediaId) throws ApiException;

	/**
	 * 
	 * Description: 通过书籍id集合获取章节数量
	 * 
	 * @Version1.0 2014年12月5日 下午6:24:45 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaIds
	 * @return
	 */
	public List<Map<String, Object>> getMaxIndexOrderByMediaIds(List<Long> mediaIds);

}
