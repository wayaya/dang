package com.dangdang.digital.processor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.BehaviorTypeEnum;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.service.IUserBehaviorService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 记录用户行为接口 All Rights Reserved.
 * 
 * @version 1.0 2015年2月12日 下午2:22:59 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapisaveUserBehaviorprocessor")
public class SaveUserBehaviorProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(SaveUserBehaviorProcessor.class);

	@Resource
	private ICacheApi cacheApi;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Resource
	IUserBehaviorService userBehaviorService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {

		// 入参：mediaId
		String mediaIdStr = request.getParameter("mediaId");
		// 入参：章节id
		String chapterIdStr = request.getParameter("chapterId");
		// 入参：个人信息token
		String token = request.getParameter("token");
		// 平台来源
		String platformSource = request.getParameter("platformSource");
		// 用户行为类型
		String behaviorType = request.getParameter("behaviorType");

		if (!checkParamsNotAllAreEmpty(chapterIdStr, mediaIdStr)) {
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

		// 验证行为类型
		if (StringUtils.isNotBlank(behaviorType) && BehaviorTypeEnum.getByType(behaviorType.toUpperCase()) == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}

		try {

			Long mediaId;
			if (StringUtils.isNotBlank(mediaIdStr)) {
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
			// 1、获取章节并判空
			MediaCacheBasicVo media = cacheApi.getMediaBasicFromCache(mediaId);
			if (media == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}
			Long custId = null;
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			if (custVo != null) {
				custId = custVo.getCustId();
			}
			userBehaviorService.addUserBehavior(BehaviorTypeEnum.getByType(behaviorType.toUpperCase()), mediaId, media.getSaleId(),
					1, custId, platformSource);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,mediaId:" + request.getParameter("chapterIds"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}

	}
}
