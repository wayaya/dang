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
import com.dangdang.bookreview.api.bean.BookReviewPriase;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 添加书评接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月18日 下午6:19:39 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapiaddBookReviewPriaseprocessor")
public class AddBookReviewPriaseProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddBookReviewPriaseProcessor.class);

	@Resource
	private IBookReviewApi bookReviewApi;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参： 书评Id
		String bookReviewIdStr = request.getParameter("bookReviewId");
		// 入参： token
		String token = request.getParameter("token");

		if (!checkParams(bookReviewIdStr, token)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			Long bookReviewId = Long.valueOf(bookReviewIdStr);
			// 获取token
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			// 未登录用户直接返回
			if (custVo == null || custVo.getCustId() == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			BookReviewPriase bookReviewPriase = bookReviewApi.getBookReviewPriaseByReviewIdAndCustId(bookReviewIdStr,
					String.valueOf(custVo.getCustId()));
			if (bookReviewPriase != null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_19004.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_19004.getErrorMessage(), response);
				return;
			}

			bookReviewPriase = new BookReviewPriase();
			bookReviewPriase.setCreateTime(new Date());
			bookReviewPriase.setReviewId(bookReviewId);
			bookReviewPriase.setCustId(custVo.getCustId());
			bookReviewPriase.setStar(false);
			Map<String, Object> returnMap = bookReviewApi.addBookReviewPriase(bookReviewPriase);
			// 如果返回信息包含错误信息，则返回失败
			if (returnMap != null && returnMap.containsKey("errorMsg")) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_19002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_19002.getErrorMessage() + "原因：" + returnMap.get("errorMsg"), response);
				return;
			}
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
