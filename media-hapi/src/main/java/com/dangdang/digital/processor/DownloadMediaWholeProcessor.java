package com.dangdang.digital.processor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.IUserAuthorityApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.UserAuthorityResultVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 下载章节书籍接口 All Rights Reserved.
 * 
 * @version 1.0 2014年11月29日 上午9:03:08 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapidownloadMediaWholeprocessor")
public class DownloadMediaWholeProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadMediaProcessor.class);

	@Resource
	private IChapterService chapterService;

	@Resource(name = "cacheApi")
	private ICacheApi cacheApi;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Resource(name = "userAuthorityApi")
	IUserAuthorityApi userAuthorityApi;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参：mediaId
		String mediaIdStr = request.getParameter("mediaId");
		// 入参：个人信息token
		String token = request.getParameter("token");
		// 增加错误标识,如果下载成功会覆盖
		response.setHeader("statusCode", String.valueOf(ResultSender.FAIL_CODE));
		if (!checkParams(mediaIdStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}

		try {
			Long mediaId = Long.valueOf(mediaIdStr);
			// 1、获取章节并判空
			MediaCacheBasicVo media = cacheApi.getMediaBasicFromCache(mediaId);
			if (media == null) {
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
			// 2、获取用户信息
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			// 3、未登录用户直接返回
			if (custVo == null || custVo.getCustId() == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			// 4、验证权限
			UserAuthorityResultVo result = userAuthorityApi.queryAuthority(custVo.getCustId(), mediaId, null);
			if (result != null && result.isHasMediaAuthority()) {
				// 5、如果有权限下载
				this.downloadChapter(request, response, media, sender, null);
				return;
			}
			// 6、验证顾客是否有包月权限
			if (result != null && result.getMonthlyEndTime() != null && result.getMonthlyEndTime().after(new Date())) {
				// 7、如果有权限下载,传入包月截止时间
				this.downloadChapter(request, response, media, sender, result.getMonthlyEndTime());
				return;
			}
			// 8、返回错误信息，没有权限
			sender.fail(ErrorCodeEnum.ERROR_CODE_10004.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10004.getErrorMessage(), response);

			return;

		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,mediaIds:" + request.getParameter("chapterIds"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}

	}

}
