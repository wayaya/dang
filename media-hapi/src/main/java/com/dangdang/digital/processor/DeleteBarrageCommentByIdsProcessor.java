package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.dangdang.base.comment.client.api.IBarrageCommentApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;

/**
 * 
 * Description: 删除弹幕接口
 * All Rights Reserved.
 * @version 1.0  2015年1月6日 上午11:35:17  by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapideleteBarrageCommentByIdsprocessor")
public class DeleteBarrageCommentByIdsProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteBarrageCommentByIdsProcessor.class);

	@Resource
	private IBarrageCommentApi barrageCommentApi;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 弹幕id集合
		String barrageCommentIdListStr = request.getParameter("barrageCommentIdList");

		if (!checkParams(barrageCommentIdListStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			List<Long> barrageCommentIdList = JSONArray.parseArray(barrageCommentIdListStr, Long.class);
			barrageCommentApi.deleteBarrageCommentByIds(barrageCommentIdList);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		} catch (com.alibaba.fastjson.JSONException e) {
			LogUtil.error(LOGGER, e, "json转换错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
