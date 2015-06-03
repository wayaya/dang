package com.dangdang.digital.processor.discovery;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.bookreview.api.IBookReviewApi;
import com.dangdang.bookreview.api.bean.BookReview;
import com.dangdang.bookreview.api.bean.BookReviewPriase;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 赞精品文章对应评论
 * All Rights Reserved.
 * @version 1.0  2015年2月27日 下午4:59:40  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapiaddDigestBookReviewPriaseprocessor")
public class AddDigestBookReviewPriaseProcessor extends BaseApiProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(AddDigestBookReviewPriaseProcessor.class);
	
	@Resource
	private IBookReviewApi bookReviewApi;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		//精品对应评论id
		String bookReviewId = request.getParameter("bookReviewId");
		if(!checkParams(token, bookReviewId)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			//校验登录
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			if (custVo == null || custVo.getCustId() == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			//校验评论是否有效
			BookReview bookReview = bookReviewApi.findBookReviewByReviewId(Long.valueOf(bookReviewId));
			if(bookReview == null){
				sender.fail(ErrorCodeEnum.ERROR_CODE_22023.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22023.getErrorMessage(), response);
				return;
			}
			//同一个用户对一个评论只能赞一次
			BookReviewPriase bookReviewPriase = bookReviewApi.getBookReviewPriaseByReviewIdAndCustId(bookReviewId, String.valueOf(custVo.getCustId()));
			if(bookReviewPriase != null){
				sender.fail(ErrorCodeEnum.ERROR_CODE_19004.getErrorCode(), ErrorCodeEnum.ERROR_CODE_19004.getErrorMessage(), response);
				return;
			}
			
			BookReviewPriase priase = new BookReviewPriase();
			priase.setCreateTime(new Date());
			priase.setCustId(custVo.getCustId());
			priase.setReviewId(Long.valueOf(bookReviewId));
			Map<String, Object> returnMap = bookReviewApi.addBookReviewPriase(priase);
			if (MapUtils.isNotEmpty(returnMap) && returnMap.containsKey("errorMsg")) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_22020.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22020.getErrorMessage(), response);
				return;		
			}
			sender.success(response);
			return;
		} catch (Exception e) {
			LogUtil.error(logger, e, "赞精品文章对应评论失败了...");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
			return;	
		}
	}

}
