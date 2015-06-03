package com.dangdang.digital.processor;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.bookreview.api.IBookReviewApi;
import com.dangdang.bookreview.api.bean.BookReview;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Discovery;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.service.IDiscoveryService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 添加书评接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月18日 下午6:19:39 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapiaddBookReviewprocessor")
public class AddBookReviewProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddBookReviewProcessor.class);

	@Resource
	private IBookReviewApi bookReviewApi;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private ICacheApi cacheApi;
	
	@Resource
	private IDiscoveryService discoveryService;
	
	@Resource
	private IDigestService digestService;

	@Transactional
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参：token
		String token = request.getParameter("token");
		// 入参： 销售主体Id
		String saleIdStr = request.getParameter("saleId");
		// 入参： 评论标题
		String title = request.getParameter("title");
		// 入参： 评论内容
		String comment = request.getParameter("comment");
		// 入参：星级
		String scoreStr = request.getParameter("score");
		// 入参：类型 0:原创 1:原创发现 2:精品阅读
		String typeStr = request.getParameter("type");
		if (!checkParams(token, saleIdStr, comment, scoreStr, typeStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			// 获取token
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			Long saleId = Long.valueOf(saleIdStr);
			Float score = Float.valueOf(scoreStr);
			// 分数参数验证
			if (score < 1.0 || score > 5.0 || (Math.ceil(score) != Math.floor(score))) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			int type = Integer.valueOf(typeStr);
			// 未登录用户直接返回
			if (custVo == null || custVo.getCustId() == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}

			BookReview bookReview = new BookReview();
			bookReview.setCustomerId(custVo.getCustId());
			bookReview.setSubjectId(saleId);
			if (type == 0) {
				bookReview.setSubjectType(BookReview.SUBJECT_TYPE_MEDIA);
			} else if (type == 1) {
				bookReview.setSubjectType(BookReview.SUBJECT_TYPE_MEDIA_ARTICAL);
			} else if (type == 2) {
				bookReview.setSubjectType(BookReview.SUBJECT_TYPE_ESSENCE);
			} else {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			bookReview.setTitle(title);
			bookReview.setComment(comment);
			bookReview.setReviewType(BookReview.REVIEW_TYPE_APP_PUB);
			bookReview.setCreateTime(new Date());
			bookReview.setReplyCount(0);
			bookReview.setAuditStatus(BookReview.AUDIT_STATUS_PASS);
			bookReview.setPraiseCount(0);
			bookReview.setFromType(BookReview.FROM_TYPE_UGC);
			bookReview.setScore(score);
			Map<String, String> returnMap = bookReviewApi.publishBookReview(bookReview);
			// 如果返回信息包含错误信息，则返回失败
			if (returnMap != null && returnMap.containsKey("errorMsg")) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_19001.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_19001.getErrorMessage() + "原因：" + returnMap.get("errorMsg"), response);
			}
			sender.success(response);
			if (type == 1){
				Discovery discovery = discoveryService.get(saleId);
				Integer reviewCnt = discovery.getReviewCnt();
				if(reviewCnt == null){
					reviewCnt = 0;
				}
				discovery.setReviewCnt(reviewCnt+1);
				discoveryService.update(discovery);
				cacheApi.setDiscoveryCache(discovery);
			}
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
