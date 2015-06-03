package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ActivityTypeEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.ActivityCacheVo;
import com.dangdang.digital.model.ActivitySaleCacheVo;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.ContentsVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;

/**
 * 
 * Description: 根据图书id获取所有章节列表 All Rights Reserved.
 * 
 * @version 1.0 2014年12月2日 上午10:06:24 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetAllChapterByMediaIdprocessor")
public class GetAllChapterByMediaIdProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetAllChapterByMediaIdProcessor.class);

	@Resource
	private IChapterService chapterService;
	@Resource
	private ICacheApi cacheApi;

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

			Map<Long, ContentsVo> contents = null;
			if (StringUtils.isBlank(startStr) && StringUtils.isBlank(endStr)) {
				contents = cacheApi.getContentsFromCache(mediaId, null, null);
			} else if (StringUtils.isBlank(endStr)) {
				Integer start = Integer.valueOf(startStr);
				if (start < 0) {
					sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
					return;
				}
				contents = cacheApi.getContentsFromCache(mediaId, start, null);
			} else if (StringUtils.isBlank(startStr)) {
				Integer end = Integer.valueOf(endStr);
				if (end < 0) {
					sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
					return;
				}
				contents = cacheApi.getContentsFromCache(mediaId, null, end);
			} else {

				Integer start = Integer.valueOf(startStr);
				Integer end = Integer.valueOf(endStr);
				if (end < start || end < 0 || start < 0) {
					sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
					return;
				}
				contents = cacheApi.getContentsFromCache(mediaId, start, end);
			}
			if (contents == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}
			// 转换为集合
			List<ContentsVo> returnList = new ArrayList<ContentsVo>();
			for (ContentsVo vo : contents.values()) {
				// TODO 暂时将没有卷id的置为1
				if (vo.getVolumeId() == null) {
					vo.setVolumeId(1l);
				}
				returnList.add(vo);
			}

			Integer total = chapterService.getCountByMediaId(mediaId);

			Integer isTimeFree = 0;
			List<ActivitySaleCacheVo> activitySaleList = cacheApi.getActivitySaleCacheWithActivityBySaleId(media
					.getSaleId());
			if (!CollectionUtils.isEmpty(activitySaleList)) {
				ActivitySaleCacheVo activitySale = activitySaleList.get(0);
				ActivityCacheVo activity = activitySale.getActivityCacheVo();
				// 判断活动是否有效
				if (activitySale.getStatus() == Constans.ACTIVITYINFO_STATUS_VALID
						&& activity.getStatus() == Constans.ACTIVITYINFO_STATUS_VALID) {
					Date now = new Date();
					// 判断是否在活动有效期
					if (now.before(activitySale.getEndTime()) && now.after(activitySale.getStartTime())
							&& now.before(activity.getEndTime()) && now.after(activity.getStartTime())) {
						// 判断活动类型是否限免
						if (activity.getActivityTypeId() == ActivityTypeEnum.TIME_FREE.getKey()) {
							isTimeFree = 1;
						}
					}
				}
			}
			// 是否限免
			sender.put("isTimeFree", isTimeFree);
			// 返回值：章节列表
			sender.put("contents", returnList);
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
