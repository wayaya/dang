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

import com.dangdang.bookreview.api.IBookReviewApi;
import com.dangdang.bookreview.api.bean.BookReview;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.utils.DesUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.ReturnBookReviewVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 获取我的书评接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月24日 上午10:17:47 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetUserBookViewListprocessor")
public class GetUserBookViewListProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetUserBookViewListProcessor.class);

	@Resource
	private IBookReviewApi bookReviewApi;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Resource
	private ReturnBeanUtils returnBeanUtils;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参： custId
		String encryptCustId = request.getParameter("custId");
		// 入参： 开始
		String startStr = request.getParameter("start");
		// 入参： 结束
		String endStr = request.getParameter("end");

		try {
			int start = Integer.valueOf(startStr);
			Integer end = Integer.valueOf(endStr);
			if (end < start || end < 0 || start < 0) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			int pageSize = end + 1 - start;
			// 获取custId
			Long custId = null;
			if (StringUtils.isNotBlank(encryptCustId)) {
				custId = DesUtils.decryptCustId(encryptCustId);
			} else {
				// 入参：token
				String token = request.getParameter("token");
				UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
				if (custVo == null) {
					sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
					return;
				}
				custId = custVo.getCustId();
			}
				
			List<Integer> subjectTypeList = new ArrayList<Integer>();
			subjectTypeList.add(BookReview.SUBJECT_TYPE_MEDIA);
			subjectTypeList.add(BookReview.SUBJECT_TYPE_MEDIA_ARTICAL);
			List<BookReview> bookReviewList = bookReviewApi.queryBookReviewByCustIdSubjectType(custId, start,
					pageSize, BookReview.AUDIT_STATUS_PASS, subjectTypeList);
			if (CollectionUtils.isEmpty(bookReviewList)) {
				sender.put("bookReviewList", null);
				return;
			}
			// 入参 ：token
			String token = request.getParameter("token");
			Long myCustId = null;
			if (StringUtils.isNotBlank(token)) {
				UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
				if (custVo != null) {
					myCustId = custVo.getCustId();
				}
			}
			List<ReturnBookReviewVo> returnBookReviewList = returnBeanUtils.getReturnBookReviewVoList(bookReviewList,
					myCustId);
			sender.put("bookReviewList", returnBookReviewList);
			sender.put("total", bookReviewApi.queryBookReviewByCustIdSubjectTypeCount(custId,
					BookReview.AUDIT_STATUS_PASS, subjectTypeList));
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}

}
