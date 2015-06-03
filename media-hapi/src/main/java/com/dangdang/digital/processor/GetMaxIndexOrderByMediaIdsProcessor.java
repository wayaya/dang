package com.dangdang.digital.processor;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;

/**
 * 
 * Description: 书架接口，通过书籍id集合获取章节数量 追更接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月5日 下午5:40:51 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetMaxIndexOrderByMediaIdsprocessor")
public class GetMaxIndexOrderByMediaIdsProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetMaxIndexOrderByMediaIdsProcessor.class);

	@Resource
	private IChapterService mediaChapterService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参：图书id集合
		String mediaIdsStr = request.getParameter("mediaIds");
		if (!checkParams(mediaIdsStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			List<Long> mediaIdList = JSONArray.parseArray(mediaIdsStr, Long.class);
			List<Map<String, Object>> maxIndexOrderList = mediaChapterService.getMaxIndexOrderByMediaIds(mediaIdList);
			sender.put("maxIndexOrderList", maxIndexOrderList);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误,mediaIds:" + request.getParameter("mediaIds"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		} catch (JSONException e) {
			LogUtil.error(LOGGER, e, "参数错误,mediaIds:" + request.getParameter("mediaIds"));
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}

}
