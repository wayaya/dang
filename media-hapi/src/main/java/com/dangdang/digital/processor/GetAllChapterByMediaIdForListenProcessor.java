package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.service.IStoreUpService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.ReturnChapterVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 听书获取章节列表 All Rights Reserved.
 * 
 * @version 1.0 2015年1月30日 上午9:21:47 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetAllChapterByMediaIdForListenprocessor")
public class GetAllChapterByMediaIdForListenProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetAllChapterByMediaIdForListenProcessor.class);

	@Resource
	private IChapterService chapterService;
	@Resource
	private ICacheApi cacheApi;
	@Resource
	private IStoreUpService storeUpService;
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		getAllChapterByMediaId(request, response, sender);
	}

	/**
	 * 
	 * Description: 根据图书id获取所有章节列表
	 * 
	 * @Version1.0 2014年12月1日 下午3:21:33 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param request
	 * @param response
	 * @param sender
	 * @throws Exception
	 */
	private void getAllChapterByMediaId(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参：图书id
		String mediaIdStr = request.getParameter("mediaId");
		// 入参：章节id
		String chapterIdStr = request.getParameter("chapterId");
		// 入参：查询起始章节
		String startStr = request.getParameter("start");
		// 入参：查询截止章节
		String endStr = request.getParameter("end");
		if (!checkParamsNotAllAreEmpty(mediaIdStr, chapterIdStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			Long mediaId;
			if (StringUtils.isNotEmpty(mediaIdStr)) {
				mediaId = Long.valueOf(mediaIdStr);
			} else {
				Long chapterId = Long.valueOf(chapterIdStr);
				ChapterCacheBasicVo chapter = cacheApi.getChapterBasicFromCache(chapterId);
				if (chapter == null) {
					sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
					return;
				}
				mediaId = chapter.getMediaId();
			}
			MediaCacheBasicVo media = cacheApi.getMediaBasicFromCache(mediaId);
			if (media == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}

			List<Long> chapterIds = null;
			if (StringUtils.isBlank(startStr) && StringUtils.isBlank(endStr)) {
				chapterIds = cacheApi.getChapterIdsFromCache(mediaId, null, null);
			} else if (StringUtils.isBlank(endStr)) {
				Integer start = Integer.valueOf(startStr);
				chapterIds = cacheApi.getChapterIdsFromCache(mediaId, start, null);
			} else if (StringUtils.isBlank(startStr)) {
				Integer end = Integer.valueOf(endStr);
				chapterIds = cacheApi.getChapterIdsFromCache(mediaId, null, end);
			} else {
				Integer start = Integer.valueOf(startStr);
				Integer end = Integer.valueOf(endStr);
				if (start > end) {
					sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
					return;
				}
				chapterIds = cacheApi.getChapterIdsFromCache(mediaId, start, end);
			}
			if (CollectionUtils.isEmpty(chapterIds)) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}

			List<ChapterCacheBasicVo> chapterList = cacheApi.batchGetChapterBasicFromCache(chapterIds);
			if (CollectionUtils.isEmpty(chapterList)) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}

			Integer total = chapterService.getCountByMediaId(mediaId);

			List<ReturnChapterVo> returnChapterList = new ArrayList<ReturnChapterVo>();
			for (ChapterCacheBasicVo chapter : chapterList) {
				ReturnChapterVo returnChapter = new ReturnChapterVo();
				returnChapter.setDuration(chapter.getDuration());
				returnChapter.setFilePath(chapter.getFilePath());
				returnChapter.setFileSize(chapter.getWordCnt());
				returnChapter.setId(chapter.getId());
				returnChapter.setIndex(chapter.getIndex());
				returnChapter.setIsFree(returnChapter.getIsFree());
				returnChapter.setTitle(chapter.getTitle());
				returnChapterList.add(returnChapter);
			}
			
			// 入参：token
			String token = request.getParameter("token");
			if (StringUtils.isNotEmpty(token)) {
				// 获取token
				UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
				if (custVo != null) {
					boolean isStore = storeUpService.isStoreUp(custVo.getCustId(), media.getSaleId(), "media");
					if (isStore) {
						sender.put("isStore", 1);
					} else {
						sender.put("isStore", 0);
					}
				}
			}
			
			// 返回值：章节列表
			sender.put("contents", returnChapterList);
			// 返回值：章节总数
			sender.put("total", total);
			sender.success(response);

		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,mediaIds:" + request.getParameter("mediaIds"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}

}
