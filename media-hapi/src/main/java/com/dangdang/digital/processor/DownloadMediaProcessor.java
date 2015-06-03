package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.IOrderApi;
import com.dangdang.digital.api.IUserAuthorityApi;
import com.dangdang.digital.constant.ActivityTypeEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.ActivityCacheVo;
import com.dangdang.digital.model.ActivitySaleCacheVo;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.service.IOrderMainService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.CreateOrderVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.ReturnBuyInfo;
import com.dangdang.digital.vo.ShoppingViewForChapterVo;
import com.dangdang.digital.vo.UserAuthorityResultVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 下载章节书籍接口 All Rights Reserved.
 * 
 * @version 1.0 2014年11月29日 上午9:03:08 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapidownloadMediaprocessor")
public class DownloadMediaProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadMediaProcessor.class);

	@Resource
	private IChapterService chapterService;

	@Resource
	private ICacheApi cacheApi;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Resource(name = "orderApi")
	private IOrderApi orderApi;

	@Resource(name = "userAuthorityApi")
	IUserAuthorityApi userAuthorityApi;

	@Resource(name = "hapibuyMediaprocessor")
	private BuyMediaProcessor buyMediaProcessor;

	@Resource
	private IOrderMainService orderMainService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 增加错误标识,如果下载成功会覆盖
		response.setHeader("statusCode", String.valueOf(ResultSender.FAIL_CODE));

		// 入参：mediaId
		String mediaIdStr = request.getParameter("mediaId");
		// 入参：章节id
		String chapterIdStr = request.getParameter("chapterId");
		// 入参：个人信息token
		String token = request.getParameter("token");
		// 入参：是否自动购买
		String autoBuy = request.getParameter("autoBuy");

		if (!checkParams(chapterIdStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}

		try {
			Long chapterId = Long.valueOf(chapterIdStr);
			Long mediaId;
			if (StringUtils.isNotBlank(mediaIdStr)) {
				mediaId = Long.valueOf(mediaIdStr);
			} else {
				ChapterCacheBasicVo chapter = cacheApi.getChapterBasicFromCache(chapterId);
				if (chapter == null) {
					LOGGER.error("下载时章节为空" + chapterId);
					sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
					return;

				}
				mediaId = chapter.getMediaId();
			}
			// 1、获取章节并判空
			ChapterCacheBasicVo chapter = cacheApi.getChapterBasicFromCache(chapterId);
			MediaCacheBasicVo media = cacheApi.getMediaBasicFromCache(mediaId);
			if (chapter == null || media == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}
			// 1.5 判断是否强制下架
			if (media.getShelfStatus() == 0) {
				LOGGER.warn("下载时资源被强制下架,mediaId:" + mediaId);
				sender.fail(ErrorCodeEnum.ERROR_CODE_10009.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10009.getErrorMessage(), response);
				return;
			}
			// 2、验证是否是免费章节,免费章节直接下载
			if (Constans.IS_FREE_YES.equals(chapter.getIsFree())) {
				LOGGER.info("免费章节直接下载,chapterId" + chapterId);
				this.downloadChapter(request, response, chapter, sender, null);
				return;
			}
			// 3、非免费章节，先通过token获取用户信息
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			// 4、未登录用户直接返回
			if (custVo == null || custVo.getCustId() == null) {
				LOGGER.info("未登录用户下载收费章节,chapterId:" + chapterId + ",token:" + token);
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			// 5、验证是否有购买权限
			List<Long> chapterIds = new ArrayList<Long>();
			chapterIds.add(chapterId);
			UserAuthorityResultVo result = userAuthorityApi.queryAuthority(custVo.getCustId(), mediaId, chapterIds);
			if (result != null && result.isHasMediaAuthority()) {
				LOGGER.info("有权限直接下载,chapterId:" + chapterId);
				// 6、如果有权限下载
				this.downloadChapter(request, response, chapter, sender, null);
				return;
			}
			// 7、验证顾客是否有包月权限
			if (result != null && result.getMonthlyEndTime() != null && result.getMonthlyEndTime().after(new Date())) {
				LOGGER.info("包月下载,chapterId:" + chapterId + ",MonthlyEndTime:" + result.getMonthlyEndTime());
				// 8、如果有权限下载,传入包月截止时间
				this.downloadChapter(request, response, chapter, sender, result.getMonthlyEndTime());
				return;
			}

			// 8.5获取活动列表，本期一个销售主体只有一个活动 2015-01-12,判断是否限免
			boolean isTimeFree = false;
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
							isTimeFree = true;
							LOGGER.info("该资源参加限免活动，直接下载,chapterId:" + chapterId);
						}
					}
				}
			}

			// 9、验证是否自动购买
			if (Constans.AUTO_BUY_YES.equals(autoBuy) || isTimeFree) {
				// 参数校验封装
				CreateOrderVo createOrderVo = new CreateOrderVo();
				createOrderVo.setCustId(custVo.getCustId());
				createOrderVo.setUsername(custVo.getUsername());
				if (orderMainService.checkAndEncapsulateParams(request, response, sender, createOrderVo, chapter)) {
					try {
						createOrderVo = orderApi.createAndSaveOrder(createOrderVo);
						if (StringUtils.isBlank(createOrderVo.getErrorCode())) {
							LOGGER.info("自动购买成功,chapterId:" + chapterId);
							// 10、购买成功直接下载
							this.downloadChapter(request, response, chapter, sender, null);
						} else {
							LOGGER.error("创建订单发生异常!" + createOrderVo.getErrorCode() + ":" + createOrderVo.getErrorMsg());
						}
					} catch (Exception e) {
						LOGGER.error("创建订单发生异常!", e);
					}
				}
			}

			if (StringUtils.isBlank(request.getParameter("deviceType"))) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}

			// 11、没有权限返回 购买信息
			ShoppingViewForChapterVo buyInfo = orderApi.findShoppingViewForChapter(custVo.getCustId(), chapterId,
					request.getParameter("deviceType"), custVo.getUsername());
			ReturnBuyInfo returnBuyInfo = ReturnBeanUtils.getReturnBuyInfo(buyInfo);
			sender.put("buyInfo", returnBuyInfo);
			sender.success(response);
			return;

		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,mediaIds:" + request.getParameter("chapterIds"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}

	}
}
