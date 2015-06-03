package com.dangdang.digital.processor;

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
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.AuthorCacheVo;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.ReturnMediaVo;

/**
 * 
 * Description: 获取大神所有书籍列表 All Rights Reserved.
 * 
 * @version 1.0 2014年12月13日 下午5:27:01 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetAllMediasByAuthorprocessor")
public class GetAllMediasByAuthorProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetAllMediasByAuthorProcessor.class);

	@Resource
	private ICacheApi cacheApi;

	@Resource(name = "mediaService")
	IMediaService mediaService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参： 开始
		String startStr = request.getParameter("start");
		// 入参： 结束
		String endStr = request.getParameter("end");
		// 入参： 作者Id
		String authorIdStr = request.getParameter("authorId");
		if (!checkParams(authorIdStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			Integer start = 0;
			Integer end = Integer.MAX_VALUE - 1;
			if (StringUtils.isNotBlank(startStr)) {
				start = Integer.valueOf(startStr);
			}
			if (StringUtils.isNotBlank(endStr)) {
				end = Integer.valueOf(endStr);
			}
			if (end < start || end < 0 || start < 0) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			int count = end - start + 1;

			Long authorId = Long.valueOf(authorIdStr);

			// 获取meidiaId列表
			List<Long> mediaIdList = mediaService.getAllMediasByAuthor(authorId, null, start, count, null);
			if (CollectionUtils.isEmpty(mediaIdList)) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}
			List<MediaCacheWholeVo> mediaList = cacheApi.batchGetMediaWholeFromCache(mediaIdList);
			if (CollectionUtils.isEmpty(mediaList)) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}
			// 转换返回vo
			List<ReturnMediaVo> returnMediaList = ReturnBeanUtils.batchGetReturnMediaListVo(mediaList);
			// 获取media数量
			Long total = mediaService.getMediasCountByAuthor(authorId, null);
			// 获取作者信息
			AuthorCacheVo author = cacheApi.getAuthorFromCache(authorId);
			if (author != null) {
				sender.put("author", ReturnBeanUtils.getReturnAuthorVo(author));
			}
			sender.put("total", total);
			sender.put("mediaList", returnMediaList);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
