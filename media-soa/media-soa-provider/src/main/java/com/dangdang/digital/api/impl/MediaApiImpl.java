package com.dangdang.digital.api.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.IMediaApi;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.vo.ChapterCacheBasicVo;

/**
 * 
 * Description: 原创接口实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年12月25日 下午6:25:26 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("mediaApi")
public class MediaApiImpl implements IMediaApi {

	@Resource
	ICacheService cacheService;

	@Override
	public Long getWordCntByChapterId(Long chapterId) throws Exception {
		ChapterCacheBasicVo chapter = cacheService.getChapterBasicFromCache(chapterId);
		if (chapter == null) {
			return null;
		}
		return chapter.getWordCnt();
	}

}
