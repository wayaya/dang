package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.IOrderApi;
import com.dangdang.digital.api.IUserAuthorityApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.service.IOrderMainService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.CreateOrderVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.UserAuthorityResultVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 下载章节书籍接口 All Rights Reserved.
 * 
 * @version 1.0 2014年11月29日 上午9:03:08 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapidownloadMediaBatchprocessor")
public class DownloadMediaBatchProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadMediaBatchProcessor.class);

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
		String chapterIdsStr = request.getParameter("chapterIds");
		// 入参：个人信息token
		String token = request.getParameter("token");
		// 入参：是否自动购买
		String autoBuy = request.getParameter("autoBuy");

		if (!checkParams(chapterIdsStr, mediaIdStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}

		try {
			List<Long> chapterIdList = JSONArray.parseArray(chapterIdsStr, Long.class);
			// 去重
			chapterIdList = this.getNoRepeatList(chapterIdList);
			Long mediaId = Long.valueOf(mediaIdStr);

			// 1、获取章节并判空
			List<ChapterCacheBasicVo> chapterList = cacheApi.batchGetChapterBasicFromCache(chapterIdList);
			MediaCacheBasicVo media = cacheApi.getMediaBasicFromCache(mediaId);
			if (CollectionUtils.isEmpty(chapterList) || media == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}
			// 1.5 判断是否强制下架
			if (media.getShelfStatus() == 0) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10009.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10009.getErrorMessage(), response);
				return;
			}
			// 2、验证是否是免费章节,免费章节直接下载
			List<Long> notFreeChpaterIdList = this.getNotFreeChapterIdList(chapterList);
			if (CollectionUtils.isEmpty(notFreeChpaterIdList)) {
				this.downloadChapter(request, response, chapterList, sender, null);
				return;
			}
			// 3、非免费章节，先通过token获取用户信息
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			// 4、未登录用户直接返回
			if (custVo == null || custVo.getCustId() == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			// 5、验证是否有购买权限
			UserAuthorityResultVo result = userAuthorityApi.queryAuthority(custVo.getCustId(), mediaId,
					notFreeChpaterIdList);
			if (result != null && result.isHasMediaAuthority()) {
				// 6、如果有权限下载
				this.downloadChapter(request, response, chapterList, sender, null);
				return;
			}
			// 7、验证顾客是否有包月权限
			if (result != null && result.getMonthlyEndTime() != null && result.getMonthlyEndTime().after(new Date())) {
				// 8、如果有权限下载,传入包月截止时间
				this.downloadChapter(request, response, chapterList, sender, result.getMonthlyEndTime());
				return;
			}
			// 9、验证是否自动购买
			if (Constans.AUTO_BUY_YES.equals(autoBuy)) {
				// 参数校验封装
				CreateOrderVo createOrderVo = new CreateOrderVo();
				createOrderVo.setCustId(custVo.getCustId());
				createOrderVo.setUsername(custVo.getUsername());
				createOrderVo.setChapterIds(notFreeChpaterIdList);
				if (orderMainService.checkAndEncapsulateParams(request, response, sender, createOrderVo, null)) {
					try {
						createOrderVo = orderApi.createAndSaveOrder(createOrderVo);
						if (StringUtils.isBlank(createOrderVo.getErrorCode())) {
							// 10、购买成功直接下载
							this.downloadChapter(request, response, chapterList, sender, null);
						} else {
							LOGGER.error("创建订单发生异常!" + createOrderVo.getErrorCode() + ":" + createOrderVo.getErrorMsg());
						}
					} catch (Exception e) {
						LOGGER.error("创建订单发生异常!" + e);
					}
				}
			}

			// 11、没有权限
			sender.fail(ErrorCodeEnum.ERROR_CODE_10004.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10004.getErrorMessage(), response);
			sender.success(response);
			return;

		} catch (NumberFormatException e) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		} catch (JSONException e) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}

	}

	/**
	 * 
	 * Description: chapterId去重
	 * 
	 * @Version1.0 2015年1月27日 上午11:26:49 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterIdList
	 * @return
	 */
	private List<Long> getNoRepeatList(List<Long> chapterIdList) {
		List<Long> noRepeatList = new ArrayList<Long>();
		for (Long chapterId : chapterIdList) {
			if (!noRepeatList.contains(chapterId)) {
				noRepeatList.add(chapterId);
			}
		}
		return noRepeatList;
	}

	/**
	 * 
	 * Description: 验证是否免费
	 * 
	 * @Version1.0 2015年1月15日 上午11:19:45 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param chapterList
	 * @return
	 */
	private List<Long> getNotFreeChapterIdList(List<ChapterCacheBasicVo> chapterList) {
		List<Long> chapterIdList = new ArrayList<Long>();
		for (ChapterCacheBasicVo chapter : chapterList) {
			if (Constans.IS_FREE_NO.equals(chapter.getIsFree())) {
				chapterIdList.add(chapter.getId());
			}
		}
		return chapterIdList;
	}
}
