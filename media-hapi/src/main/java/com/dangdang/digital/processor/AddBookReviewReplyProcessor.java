package com.dangdang.digital.processor;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.bookreview.api.IBookReviewApi;
import com.dangdang.bookreview.api.bean.BookReviewReply;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 添加书评接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月18日 下午6:19:39 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapiaddBookReviewReplyprocessor")
public class AddBookReviewReplyProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddBookReviewReplyProcessor.class);

	@Resource
	private IBookReviewApi bookReviewApi;

	@Resource
	private ICacheApi cacheApi;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参： 书评Id
		String bookReviewIdStr = request.getParameter("bookReviewId");
		// 入参： token
		String token = request.getParameter("token");
		// 入参： 回复内容
		String content = request.getParameter("content");

		if (!checkParams(bookReviewIdStr, token, content)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			Long bookReviewId = Long.valueOf(bookReviewIdStr);
			// 获取token
			UserTradeBaseVo custVo = cacheApi.getUserWholeInfoByToken(token);
			// 未登录用户直接返回
			if (custVo == null || custVo.getCustId() == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			BookReviewReply bookReviewReply = new BookReviewReply();
			bookReviewReply.setCreateTime(new Date());
			bookReviewReply.setCustId(custVo.getCustId());
			bookReviewReply.setReplyContent(content);
			bookReviewReply.setReviewId(bookReviewId);
			bookReviewReply.setCustName(custVo.getNickname());
			Map<String, Object> returnMap = bookReviewApi.addBookReviewReply(bookReviewReply);
			// 如果返回信息包含错误信息，则返回失败
			if (returnMap != null && returnMap.containsKey("errorMsg")) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_19003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_19003.getErrorMessage() + "原因：" + returnMap.get("errorMsg"), response);
			}
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
