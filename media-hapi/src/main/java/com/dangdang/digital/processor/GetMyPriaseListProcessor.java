package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * 
 * Description:  接口废弃，暂时保留，向前兼容。获取我点赞过的书评列表 All Rights Reserved.
 * 
 * @version 1.0 2014年12月23日 上午9:47:53 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetMyPriaseListprocessor")
@Deprecated
public class GetMyPriaseListProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetMyPriaseListProcessor.class);

	@Resource
	private IBookReviewApi bookReviewApi;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Resource
	private ICommonApi commonApi;

	@Resource
	private ReturnBeanUtils returnBeanUtils;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参： token
		String token = request.getParameter("token");
		// 入参： 开始
		String startStr = request.getParameter("start");
		// 入参： 结束
		String endStr = request.getParameter("end");

		if (!checkParams(token)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			Long start = Long.valueOf(startStr);
			Long end = Long.valueOf(endStr);
			if (end < start || end < 0 || start < 0) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				sender.success(response);
				return;
			}
			Long pageSize = end + 1 - start;
			// 获取token
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			// 未登录用户直接返回
			if (custVo == null || custVo.getCustId() == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				sender.success(response);
				return;
			}
			List<Integer> subjectTypeList = new ArrayList<Integer>();
			subjectTypeList.add(BookReview.SUBJECT_TYPE_MEDIA);
			subjectTypeList.add(BookReview.SUBJECT_TYPE_MEDIA_ARTICAL);
			List<BookReview> bookReviewList = bookReviewApi.queryBookReviewByPriaseCustIdSubjectType(
					custVo.getCustId(), subjectTypeList, start, pageSize);
			if (CollectionUtils.isEmpty(bookReviewList)) {
				sender.put("bookReviewList", null);
				sender.success(response);
				return;
			}
			// 获取用户信息
			List<String> custIdList = new ArrayList<String>();
			for (BookReview bookReview : bookReviewList) {
				custIdList.add(String.valueOf(bookReview.getCustomerId()));
			}
			Map<String, Map<String, String>> returnMap = commonApi.getBatchCustInfos(custIdList, 60 * 60 * 2);
			if (returnMap != null) {
				for (BookReview bookReview : bookReviewList) {
					if (returnMap.containsKey(String.valueOf(bookReview.getCustomerId()))
							&& returnMap.get(String.valueOf(bookReview.getCustomerId())) != null) {
						bookReview.setCustImg(returnMap.get(String.valueOf(bookReview.getCustomerId())).get("custImg"));
						bookReview.setCustNickName(returnMap.get(String.valueOf(bookReview.getCustomerId())).get(
								"nickName"));
					}
				}
			}
			List<ReturnBookReviewVo> returnBookReviewList = returnBeanUtils.getReturnBookReviewVoList(bookReviewList,
					custVo.getCustId());
			sender.put("bookReviewList", returnBookReviewList);
			sender.put("total",
					bookReviewApi.queryBookReviewByPriaseCustIdSubjectTypeCount(custVo.getCustId(), subjectTypeList));
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
