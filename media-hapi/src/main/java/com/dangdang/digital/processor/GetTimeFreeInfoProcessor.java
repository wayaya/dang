package com.dangdang.digital.processor;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ActivityTypeEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.ActivityCacheVo;
import com.dangdang.digital.model.ActivitySaleCacheVo;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.MediaCacheBasicVo;

/**
 * 
 * Description: 获取限免信息接口 All Rights Reserved.
 * 
 * @version 1.0 2015年2月4日 下午6:39:35 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetTimeFreeInfoprocessor")
public class GetTimeFreeInfoProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetTimeFreeInfoProcessor.class);

	@Resource
	private IChapterService chapterService;
	@Resource
	private ICacheApi cacheApi;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		getTimeFreeInfo(request, response, sender);
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
	private void getTimeFreeInfo(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参：图书id
		String mediaIdStr = request.getParameter("mediaId");
		if (!checkParamsNotAllAreEmpty(mediaIdStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {

			Long mediaId = Long.valueOf(mediaIdStr);
			MediaCacheBasicVo media = cacheApi.getMediaBasicFromCache(mediaId);
			if (media == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}

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
			// 返回值：章节总数
			sender.put("lastIndexOrder", media.getLastIndexOrder());
			sender.success(response);

		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,mediaId:" + request.getParameter("mediaId"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}

}
