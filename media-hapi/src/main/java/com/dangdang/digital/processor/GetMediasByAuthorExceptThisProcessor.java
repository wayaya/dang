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
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.ReturnMediaVo;

/**
 * 
 * Description: 获取坐着最畅销的两本书 All Rights Reserved.
 * 
 * @version 1.0 2014年12月12日 下午5:44:11 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetMediasByAuthorExceptThisprocessor")
public class GetMediasByAuthorExceptThisProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetMediasByAuthorExceptThisProcessor.class);

	@Resource
	private ICacheApi cacheApi;

	@Resource(name = "mediaService")
	IMediaService mediaService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参： 频道
		String channelType = request.getParameter("channelType");
		// 入参：销售主体id
		String saleIdStr = request.getParameter("saleId");
		// 入参： 开始
		String startStr = request.getParameter("start");
		// 入参： 结束
		String endStr = request.getParameter("end");

		// 入参：作者Id
		String authorIdStr = request.getParameter("authorId");
		if (!checkParams(channelType, saleIdStr)) {
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

			Long saleId = Long.valueOf(saleIdStr);

			MediaSaleCacheVo sale = cacheApi.getMediaSaleFromCache(saleId);
			if (sale == null || CollectionUtils.isEmpty(sale.getMediaList()) || sale.getMediaList().get(0) == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}
			// 获取作者id
			Long authorId;
			if (StringUtils.isNotBlank(authorIdStr)) {
				authorId = Long.valueOf(authorIdStr);
			} else {
				authorId = sale.getMediaList().get(0).getAuthorId();
			}
			// 获取meidaId
			Long mediaId = sale.getMediaList().get(0).getMediaId();
			List<Long> mediaIdList = mediaService.getMediasByAuthorExceptThis(authorId, channelType, mediaId, start,
					count);
			List<ReturnMediaVo> returnMediaList = null;
			// 如果为空返回空列表，不返回错误
			if (CollectionUtils.isEmpty(mediaIdList)) {
				sender.put("mediaList", returnMediaList);
				sender.put("total", 0);
				sender.success(response);
				return;
			}
			List<MediaCacheWholeVo> mediaList = cacheApi.batchGetMediaWholeFromCache(mediaIdList);
			// 转换vo
			returnMediaList = ReturnBeanUtils.batchGetReturnMediaListVo(mediaList);
			sender.put("mediaList", returnMediaList);

			List<Long> allMedias = mediaService.getMediasByAuthorExceptThis(authorId, channelType, mediaId, 0,
					Integer.MAX_VALUE);
			// 设置总数
			sender.put("total", allMedias.size());
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,mediaIds:" + request.getParameter("mediaIds"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}

}
