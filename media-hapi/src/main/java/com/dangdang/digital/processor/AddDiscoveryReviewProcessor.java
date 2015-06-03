package com.dangdang.digital.processor;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.bookreview.api.IBookReviewApi;
import com.dangdang.bookreview.api.bean.BookReview;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.service.IDiscoveryService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

@Component("hapiaddDiscoveryReviewprocessor")
public class AddDiscoveryReviewProcessor extends BaseApiProcessor{

	@Resource
	private IBookReviewApi bookReviewApi;
	
	@Resource
	private IDiscoveryService discoveryService;
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {

		// 入参：token
		String token = request.getParameter("token");
		// 入参： 发现Id
		String discoveryIdStr = request.getParameter("discoveryId");
		// 入参： 评论标题
		String title = request.getParameter("title");
		// 入参： 评论内容
		String comment = request.getParameter("comment");
		// 入参：星级
		String scoreStr = request.getParameter("score");

		//类型  1:原创发现  2:当心好文
		String typeStr = request.getParameter("type");
				
		if (!checkParams(token, discoveryIdStr, title, comment, scoreStr,typeStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			// 获取token
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			Long discoveryId = Long.valueOf(discoveryIdStr);
			Float score = Float.valueOf(scoreStr);
			int type = Integer.valueOf(typeStr);
			// 未登录用户直接返回
			if (custVo == null || custVo.getCustId() == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			BookReview bookReview = new BookReview();
			bookReview.setCustomerId(custVo.getCustId());
			bookReview.setSubjectId(discoveryId);
			if(type==1){   
				bookReview.setSubjectType(BookReview.SUBJECT_TYPE_MEDIA_ARTICAL);
			}else if(type == 2){
				bookReview.setSubjectType(BookReview.SUBJECT_TYPE_ESSENCE);
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
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_20002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_20002.getErrorMessage(), response);
		}
	
	}

}
