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
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.ReturnBookReviewVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

@Component("hapigetDiscoveryReviewList")
public class GetDiscoveryReviewListProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetDiscoveryReviewListProcessor.class);

	@Resource
	private IBookReviewApi bookReviewApi;

	@Resource
	private ReturnBeanUtils returnBeanUtils;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {

		// 入参：discoveryId
		String discoveryIdStr = request.getParameter("discoveryId");
		// 入参： 开始
		String startStr = request.getParameter("start");
		// 入参： 结束
		String endStr = request.getParameter("end");
		// 入参 1:原创发现 2:精品阅读
		String typeStr = request.getParameter("type");

		if (!checkParams(discoveryIdStr, startStr, endStr, typeStr)) {
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
			int type = Integer.valueOf(typeStr);
			int pageSize = end + 1 - start;
			int pageIndex = start / pageSize + 1;
			Long discoveryId = Long.valueOf(discoveryIdStr);
			List<BookReview> bookReviewList = new ArrayList<BookReview>();
			if (type == 1) {
				bookReviewList = bookReviewApi.querypageBookReviewBySubjectIdSubjectType(discoveryId,
						BookReview.SUBJECT_TYPE_MEDIA_ARTICAL, pageIndex, pageSize, BookReview.AUDIT_STATUS_PASS);
			}
			if (type == 2) {
				bookReviewList = bookReviewApi.querypageBookReviewBySubjectIdSubjectType(discoveryId,
						BookReview.SUBJECT_TYPE_ESSENCE, pageIndex, pageSize, BookReview.AUDIT_STATUS_PASS);
			}
			List<Long> discoveryList = new ArrayList<Long>();
			discoveryList.add(discoveryId);
			Map<String, Long> countMap = bookReviewApi.queryBookReviewCount(discoveryList,
					BookReview.SUBJECT_TYPE_MEDIA);
			if (countMap != null && countMap.containsKey(String.valueOf(discoveryId))) {
				sender.put("total", countMap.get(String.valueOf(discoveryId)));
			} else {
				sender.put("total", 0);
			}
			if (CollectionUtils.isEmpty(bookReviewList)) {
				sender.put("bookReviewList", null);
				sender.success(response);
				return;
			}
			// 入参 ：token
			String token = request.getParameter("token");
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
