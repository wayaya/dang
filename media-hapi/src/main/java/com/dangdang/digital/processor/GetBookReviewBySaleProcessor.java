package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.dangdang.bookreview.api.IBookReviewApi;
import com.dangdang.bookreview.api.bean.BookReview;
import com.dangdang.common.api.ICommonApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.ReturnBookReviewVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 获取书的评论的接口 Description: All Rights Reserved.
 * 
 * @version 1.0 2014年12月16日 下午3:26:40 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetBookReviewBySaleprocessor")
public class GetBookReviewBySaleProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetBookReviewBySaleProcessor.class);

	@Resource
	private IBookReviewApi bookReviewApi;

	@Resource
	private ICommonApi commonApi;
	
	@Resource
	private ReturnBeanUtils returnBeanUtils;
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参：saleId
		String saleIdStr = request.getParameter("saleId");
		// 入参： 开始
		String startStr = request.getParameter("start");
		// 入参： 结束
		String endStr = request.getParameter("end");
		// 入参：类型 0:原创 1:原创发现 2:精品阅读
		String typeStr = request.getParameter("type");
		// 入参： token
		String token = request.getParameter("token");
		if (!checkParams(saleIdStr, startStr, endStr, typeStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			int start = Integer.valueOf(startStr);
			Integer end = Integer.valueOf(endStr);
			if (end < start || end < 0 || start < 0) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			int pageSize = end + 1 - start;
			int pageIndex = start / pageSize + 1;
			Long saleId = Long.valueOf(saleIdStr);
			int subjectType = BookReview.SUBJECT_TYPE_MEDIA;
			int type = Integer.valueOf(typeStr);
			if (type == 0) {
				subjectType = BookReview.SUBJECT_TYPE_MEDIA;
			} else if (type == 1) {
				subjectType = BookReview.SUBJECT_TYPE_MEDIA_ARTICAL;
			} else if (type == 2) {
				subjectType = BookReview.SUBJECT_TYPE_ESSENCE;
			} else {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			List<BookReview> bookReviewList = bookReviewApi.querypageBookReviewBySubjectIdSubjectType(saleId,
					subjectType, pageIndex, pageSize, BookReview.AUDIT_STATUS_PASS);
			List<Long> saleIdList = new ArrayList<Long>();
			saleIdList.add(saleId);
			Map<String, Long> countMap = bookReviewApi.queryBookReviewCount(saleIdList, subjectType);
			if (countMap != null && countMap.containsKey(String.valueOf(saleId))) {
				sender.put("total", countMap.get(String.valueOf(saleId)));
			} else {
				sender.put("total", 0);
			}
			if (CollectionUtils.isEmpty(bookReviewList)) {
				sender.put("bookReviewList", null);
				sender.success(response);
				return;
			}
			Long custId = null;
			if (StringUtils.isNotBlank(token)) {
				UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
				if (custVo != null) {
					custId = custVo.getCustId();
				}
			}
			// 转换vo
			List<ReturnBookReviewVo> returnBookReviewList = returnBeanUtils.getReturnBookReviewVoList(bookReviewList,
					custId);

			sender.put("bookReviewList", returnBookReviewList);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
