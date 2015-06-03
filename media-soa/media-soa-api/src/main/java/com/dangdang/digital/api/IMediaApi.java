package com.dangdang.digital.api;

/**
 * 
 * Description: 原创对外接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月25日 下午6:17:51 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IMediaApi {

	/**
	 * 
	 * Description: 通过章节id获取章节字数
	 * 
	 * @Version1.0 2014年12月25日 下午6:20:37 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterId
	 * @return
	 * @throws Exception 
	 */
	Long getWordCntByChapterId(Long chapterId) throws Exception;

}
