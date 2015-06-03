package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.dangdang.bookreview.api.IBookReviewApi;
import com.dangdang.bookreview.api.bean.BookReview;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.IUserAuthorityApi;
import com.dangdang.digital.constant.ActivityTypeEnum;
import com.dangdang.digital.constant.BehaviorTypeEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.ActivityCacheVo;
import com.dangdang.digital.model.ActivitySaleCacheVo;
import com.dangdang.digital.model.UserMonthly;
import com.dangdang.digital.service.IStoreUpService;
import com.dangdang.digital.service.IUserBehaviorService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.ReturnMediaVo;
import com.dangdang.digital.vo.ReturnSaleVo;
import com.dangdang.digital.vo.UserAuthorityCacheVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 通过章节id获取media信息接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月11日 下午6:28:57 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetMediasBySaleIdprocessor")
public class GetMediasBySaleIdProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetMediasBySaleIdProcessor.class);

	@Resource
	private ICacheApi cacheApi;

	@Resource
	private IBookReviewApi bookReviewApi;

	@Resource
	IStoreUpService storeUpService;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Resource(name = "userAuthorityApi")
	IUserAuthorityApi userAuthorityApi;

	@Resource
	IUserBehaviorService userBehaviorService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参： 销售主体id
		String saleIdStr = request.getParameter("saleId");
		// 入参：是否来源与搜索
		String fromSearch = request.getParameter("fromSearch");
		// 平台来源
		String platformSource = request.getParameter("platformSource");
		if (!checkParams(saleIdStr, platformSource)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		// 验证平台来源
		if (PlatformSourceEnum.getBySource(platformSource) == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			Long saleId = Long.valueOf(saleIdStr);
			MediaSaleCacheVo mediaSale = cacheApi.getMediaSaleFromCache(saleId);
			if (mediaSale == null || CollectionUtils.isEmpty(mediaSale.getMediaList())) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}

			// 转换vo
			ReturnSaleVo returnMediaSale = ReturnBeanUtils.getReturnSaleDetailVo(mediaSale);
			List<Long> saleIdList = new ArrayList<Long>();
			saleIdList.add(saleId);
			// 书评数
			Map<String, Long> returnBookReviewCountMap = bookReviewApi.queryBookReviewCount(saleIdList,
					BookReview.SUBJECT_TYPE_MEDIA);
			LOGGER.info("单品页调用queryBookReviewCount接口获取书评数量,参数:" + saleIdList + "," + BookReview.SUBJECT_TYPE_MEDIA
					+ ",结果：" + returnBookReviewCountMap);
			if (returnBookReviewCountMap != null && returnBookReviewCountMap.containsKey(String.valueOf(saleId))) {
				returnMediaSale.getMediaList().get(0)
						.setBookReviewCount(returnBookReviewCountMap.get(String.valueOf(saleId)));
			} else {
				returnMediaSale.getMediaList().get(0).setBookReviewCount(0l);
			}
			// 评星
			Map<String, Float> returnScoreMap = bookReviewApi.queryBookScoreBySubjectIdSubjectType(saleIdList,
					BookReview.SUBJECT_TYPE_MEDIA);
			LOGGER.info("单品页调用queryBookScoreBySubjectIdSubjectType接口获取评星,参数:" + saleIdList + ","
					+ BookReview.SUBJECT_TYPE_MEDIA + ",结果：" + returnScoreMap);
			if (returnScoreMap != null && returnScoreMap.containsKey(String.valueOf(saleId))) {
				returnMediaSale.getMediaList().get(0)
						.setScore(Float.valueOf(String.valueOf(returnScoreMap.get(String.valueOf(saleId)))));
			} else {
				returnMediaSale.getMediaList().get(0).setScore((new Float(4)));
			}

			// 入参： token
			String token = request.getParameter("token");
			Long custId = null;
			if (StringUtils.isNotBlank(token)) {
				UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
				if (custVo != null) {
					custId = custVo.getCustId();
					boolean isStore = storeUpService.isStoreUp(custVo.getCustId(), saleId, "media");
					if (isStore) {
						// 设置是否收藏
						returnMediaSale.setIsStore(1);
					} else {
						returnMediaSale.setIsStore(0);
					}
					// 获取购买权限
					UserAuthorityCacheVo authority = cacheApi.getUserAuthorityCacheVoByCustIdAndMediaId(
							custVo.getCustId(), returnMediaSale.getMediaList().get(0).getMediaId());
					// 获取包月权限
					UserMonthly userMonthly = userAuthorityApi.findUserMonthlyNowByCustId(custVo.getCustId(),
							returnMediaSale.getMediaList().get(0).getMediaId(), null);
					if (authority != null) {
						// 设置购买权限
						if (Constans.USER_AUTHORITY_MEIDA == authority.getAuthorityType()) {
							returnMediaSale.getMediaList().get(0).setIsWholeAuthority(1);
							returnMediaSale.getMediaList().get(0).setIsChapterAuthority(1);
						} else if (Constans.USER_AUTHORITY_CHAPTER == authority.getAuthorityType()) {
							returnMediaSale.getMediaList().get(0).setIsWholeAuthority(0);
							returnMediaSale.getMediaList().get(0).setIsChapterAuthority(1);
						} else {
							returnMediaSale.getMediaList().get(0).setIsWholeAuthority(0);
							returnMediaSale.getMediaList().get(0).setIsChapterAuthority(0);
						}
					} else {
						returnMediaSale.getMediaList().get(0).setIsWholeAuthority(0);
						returnMediaSale.getMediaList().get(0).setIsChapterAuthority(0);
					}
					if (userMonthly != null && userMonthly.getMonthlyEndTime() != null) {
						// 设置包月权限
						returnMediaSale.getMediaList().get(0)
								.setMonthlyEndTime(userMonthly.getMonthlyEndTime().getTime());
					}

				}

			}
			// 获取活动列表，本期一个销售主体只有一个活动 2015-01-12
			List<ActivitySaleCacheVo> activitySaleList = cacheApi.getActivitySaleCacheWithActivityBySaleId(saleId);
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

						returnMediaSale.setActivityType(activity.getActivityTypeId());
						returnMediaSale.setActivityEndTime(activity.getEndTime().getTime());
						// 本期只有一口价和限免两个活动
						if (activity.getActivityTypeId() == ActivityTypeEnum.A_PRICE.getKey()) {
							// 一口价
							returnMediaSale.setActivityPrice(activitySale.getSalePrice());
						} else if (activity.getActivityTypeId() == ActivityTypeEnum.TIME_FREE.getKey()) {
							// 限免
							returnMediaSale.setActivityPrice(0l);
						}
					}
				}
			}

			// 判断是否下架
			if (mediaSale.getShelfStatus() == 0) {
				sender.putErrorInfo("mediaSale", returnMediaSale);
				sender.fail(ErrorCodeEnum.ERROR_CODE_10010.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10010.getErrorMessage(), response);
				return;
			}
			if ("1".equals(fromSearch)) {
				for (ReturnMediaVo media : returnMediaSale.getMediaList()) {
					userBehaviorService.addUserBehavior(BehaviorTypeEnum.SEARCH, media.getMediaId(), saleId, 1, custId,
							platformSource);
				}
			}

			sender.put("mediaSale", returnMediaSale);
			sender.success(response);
		} catch (NumberFormatException e) {
			LOGGER.error("参数错误,mediaIds:" + request.getParameter("mediaIds"), e);
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}

}
