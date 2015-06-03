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

import com.dangdang.bookreview.api.IBookReviewApi;
import com.dangdang.bookreview.api.bean.BookReview;
import com.dangdang.bookreview.api.bean.BookReviewReply;
import com.dangdang.common.api.ICommonApi;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Discovery;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.MediaCoverPicUrlUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.ReturnBookReviewReplyVo;
import com.dangdang.digital.vo.ReturnBookReviewVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 获取书的评论的接口 Description: All Rights Reserved.
 * 
 * @version 1.0 2014年12月16日 下午3:26:40 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetBookReviewReplyListprocessor")
public class GetBookReviewReplyListProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetBookReviewReplyListProcessor.class);

	@Resource
	private IBookReviewApi bookReviewApi;

	@Resource
	private ICommonApi commonApi;

	@Resource
	private ICacheApi cacheApi;

	@Resource
	private ReturnBeanUtils returnBeanUtils;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参：书评Id
		String bookReviewIdStr = request.getParameter("bookReviewId");
		// 入参： 开始
		String startStr = request.getParameter("start");
		// 入参： 结束
		String endStr = request.getParameter("end");

		if (!checkParams(bookReviewIdStr, startStr, endStr)) {
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
				return;
			}
			Long pageSize = end + 1 - start;
			Long bookReviewId = Long.valueOf(bookReviewIdStr);
			// 入参 ：token
			String token = request.getParameter("token");
			Long custId = null;
			if (StringUtils.isNotBlank(token)) {
				UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
				if (custVo != null) {
					custId = custVo.getCustId();
				}
			}
			// 书评信息
			BookReview bookReview = bookReviewApi.findBookReviewByReviewId(bookReviewId);
			String title = null;
			String coverPic = null;
			try {
				if (bookReview.getSubjectType() == BookReview.SUBJECT_TYPE_MEDIA) {
					title = cacheApi.getMediaSaleFromCache(bookReview.getSubjectId()).getName();
					coverPic = MediaCoverPicUrlUtil.getMediaCoverPic(bookReview.getSubjectId());
				} else if (bookReview.getSubjectType() == BookReview.SUBJECT_TYPE_MEDIA_ARTICAL) {
					Discovery discovery = cacheApi.getDisCoveryFromCache(bookReview.getSubjectId());
					if (discovery != null) {
						title = discovery.getTitle();
						coverPic = ConfigPropertieUtils.getString("media.resource.discover.http.path")
								+ discovery.getPic1Path();
					}

				}
			} catch (Exception e) {
				LOGGER.error("获取书评title异常", e);
			}
			ReturnBookReviewVo returnBookReivew = returnBeanUtils.getReturnBookReviewVoSingle(bookReview, custId,
					title, coverPic);
			sender.put("bookReview", returnBookReivew);

			LOGGER.info("调用queryBookReviewReplyByReviewId接口，查询回复，入参，bookReviewId：" + bookReviewId + ",start:" + start
					+ ",pageSize:" + pageSize);
			List<BookReviewReply> replyList = bookReviewApi.queryBookReviewReplyByReviewId(bookReviewId, start,
					pageSize);
			if (replyList != null) {
				LOGGER.info("queryBookReviewReplyByReviewId接口返回数据数量：" + replyList.size());
			}
			LOGGER.info("调用queryBookReviewReplyCountByReviewId接口，查询回复数量，入参，bookReviewId：" + bookReviewId);
			Integer total = bookReviewApi.queryBookReviewReplyCountByReviewId(bookReviewId);
			LOGGER.info("queryBookReviewReplyByReviewId接口返回数据数量：" + total);
			if (total != null) {
				sender.put("total", total);
			} else {
				sender.put("total", 0);
			}
			if (CollectionUtils.isEmpty(replyList)) {
				sender.put("replyList", null);
				sender.success(response);
				return;
			}

			// 转换vo
			List<ReturnBookReviewReplyVo> returnReplyList = returnBeanUtils.getReturnBookReviewReplyVoList(replyList,
					custId);

			sender.put("replyList", returnReplyList);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
