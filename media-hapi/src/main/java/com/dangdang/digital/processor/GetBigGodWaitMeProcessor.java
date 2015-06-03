package com.dangdang.digital.processor;

import java.util.ArrayList;
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
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.MediaCoverPicUrlUtil;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;
import com.dangdang.digital.utils.TupleUtils.ResultTwoTuple;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.AuthorCacheVo;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.ReturnMediaVo;

/**
 * 
 * Description: 获取作者最畅销的两本书 All Rights Reserved.
 * 
 * @version 1.0 2014年12月12日 下午5:44:11 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetBigGodWaitMeprocessor")
public class GetBigGodWaitMeProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetBigGodWaitMeProcessor.class);

	@Resource
	private ICacheApi cacheApi;

	@Resource(name = "mediaService")
	IMediaService mediaService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参： 频道
		String channelType = request.getParameter("channelType");
		if (!checkParams(channelType)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			// start,end请求参数判断
			ListResultTuple<Boolean, Integer, Integer, Integer, String> checkResult = RequestParamCheckerUtils
					.checkStartEndParam(request);
			if (!checkResult.success) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), checkResult.errorMsg, response);
				return;
			}
			/**
			 * 如果分开请求,第一次异步设置total时,接口可能查询不到total,因为还没有刷到从库中
			List<MediaSaleCacheVo> saleList = cacheApi.getMediaSaleByColumnCode(checkResult.start, checkResult.end,
					channelType + "_dsddw");
			
			long total = cacheApi.getColumnContentCount(channelType + "_dsddw");
			**/
			ResultTwoTuple<Long,List<MediaSaleCacheVo>> resultTuple = cacheApi.getMediaSaleByColumnCode(checkResult.start, checkResult.end,
					channelType + "_dsddw");
			List<MediaSaleCacheVo> saleList = resultTuple.listId;
			long total = resultTuple.total;
			if (CollectionUtils.isEmpty(saleList)) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_11003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_11003.getErrorMessage(), response);
				return;
			}
			// 转换vo
			List<ReturnMediaVo> returnMediaList = new ArrayList<ReturnMediaVo>();
			for (MediaSaleCacheVo sale : saleList) {
				if (sale != null && sale.getMediaList() != null && sale.getMediaList().size() > 0
						&& sale.getMediaList().get(0).getAuthorId() != null) {
					MediaCacheWholeVo media = sale.getMediaList().get(0);
					AuthorCacheVo author = cacheApi.getAuthorFromCache(media.getAuthorId());
					ReturnMediaVo returnMedia = new ReturnMediaVo();
					returnMedia.setAuthorId(author.getAuthorId());
					if (StringUtils.isNotBlank(author.getHeadPic())) {
						returnMedia.setAuthorHeadPic(ConfigPropertieUtils.getString("media.resource.author.http.path")
								+ "/" + author.getHeadPic());
					}
					returnMedia.setAuthorIntroduction(author.getIntroduction());
					returnMedia.setAuthorPenname(author.getPseudonym());
					returnMedia.setMediaId(media.getMediaId());
					returnMedia.setSaleId(media.getSaleId());
					returnMedia.setCoverPic(MediaCoverPicUrlUtil.getMediaCoverPic(media.getMediaId()));
					returnMediaList.add(returnMedia);
				}
			}

			sender.put("bigGodList", returnMediaList);
			sender.put("total", total);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,mediaIds:" + request.getParameter("mediaIds"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
