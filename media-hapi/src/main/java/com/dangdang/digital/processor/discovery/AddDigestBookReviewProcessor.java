package com.dangdang.digital.processor.discovery;

import java.util.Date;
import java.util.List;
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
import com.dangdang.common.api.ICommonApi;
import com.dangdang.common.api.IControApi;
import com.dangdang.common.enums.SendFrequenceEnum;
import com.dangdang.digital.api.IIllegalWordApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.FormatUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;
import com.google.common.collect.Lists;

/**
 * Description: 精品文章发表评论接口(支持不登录发表)
 * All Rights Reserved.
 * @version 1.0  2015年2月26日 下午3:23:10  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapiaddDigestBookReviewprocessor")
public class AddDigestBookReviewProcessor extends BaseApiProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(AddDigestBookReviewProcessor.class);
	
	//评论内容至少文字内容长度
	private static final int COMMENT_MIN_LENGTH = ConfigPropertieUtils.getInteger("comment.min.length", 2);
	//评论内容最大文字内容长度
	private static final int COMMENT_MAX_LENGTH = ConfigPropertieUtils.getInteger("comment.max.length", 600);
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private IDigestService digestService;
	
	@Resource
	private IBookReviewApi bookReviewApi;
	
	@Resource
	private ICommonApi commonApi;
	
	@Resource
	private IControApi controApi;
	
	@Resource
	private IIllegalWordApi illegalWordApi;
	
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		//登录用户token
		String token = request.getParameter("token");
		//精品id
		String digestIdStr = request.getParameter("digestId");
		//评论内容
		String oldComment = request.getParameter("comment");
		//校验必传参数
		if(!checkParams(token, oldComment, digestIdStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		//校验登录
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		
		//校验评论内容长度
		if(oldComment.trim().length() < COMMENT_MIN_LENGTH){
			sender.fail(ErrorCodeEnum.ERROR_CODE_22018.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22018.getErrorMessage(), response);
			return;
		}
		if(oldComment.trim().length() > COMMENT_MAX_LENGTH){
			sender.fail(ErrorCodeEnum.ERROR_CODE_22019.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22019.getErrorMessage(), response);
			return;
		}
		
		//过滤输入的html标签
		String comment = FormatUtils.Html2Text(oldComment.trim());
		Long custId = custVo.getCustId();
		
		//校验包含敏感词
		boolean includeIllegalWord = illegalWordApi.contentsIllegalWords(comment);
		if(includeIllegalWord){
			sender.fail(ErrorCodeEnum.ERROR_CODE_22024.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22024.getErrorMessage(), response);
			return;
		}
		
		//黑名单校验
		boolean isBlackUser = commonApi.checkUserIsBlackList(custId);
		if(isBlackUser){
			sender.fail(ErrorCodeEnum.ERROR_CODE_22021.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22021.getErrorMessage(), response);
			return;
		}
		
		//发表频率控制
		boolean isFrequence = controApi.isFrequence(SendFrequenceEnum.PUB_BOOK_REVIEW.getKey(), String.valueOf(custId));
		if(isFrequence){
			sender.fail(ErrorCodeEnum.ERROR_CODE_22022.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22022.getErrorMessage(), response);
			return;
		}
		
		try {
			Long digestId = Long.valueOf(digestIdStr);
			Digest digest = digestService.findDigestById(digestId);
			if(digest == null){
				sender.fail(ErrorCodeEnum.ERROR_CODE_22017.getErrorCode(),ErrorCodeEnum.ERROR_CODE_22017.getErrorMessage(), response);
				return;
			}
			
			BookReview bookReview = new BookReview();
			bookReview.setSubjectId(digestId);
			bookReview.setCustomerId(custVo.getCustId());
			bookReview.setComment(comment);
			bookReview.setCreateTime(new Date());
			bookReview.setAuditStatus(BookReview.AUDIT_STATUS_PASS);
			bookReview.setReviewType(BookReview.REVIEW_TYPE_APP_PUB);
			bookReview.setFromType(BookReview.FROM_TYPE_UGC);
			bookReview.setSubjectType(BookReview.SUBJECT_TYPE_ESSENCE);
			Map<String, String> returnMap = bookReviewApi.publishBookReview(bookReview);
			if (MapUtils.isNotEmpty(returnMap) && returnMap.containsKey("errorMsg")) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_22020.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22020.getErrorMessage(), response);
				return;		
			}
			
			//更新精品对应评论数
			List<Long> subjectIds = Lists.newArrayList(digestId);
			Map<String, Long> countMap = bookReviewApi.queryBookReviewCount(subjectIds, BookReview.SUBJECT_TYPE_ESSENCE);
			if(MapUtils.isNotEmpty(countMap)){
				Long reallyCount = countMap.get(String.valueOf(digestId));
				if(reallyCount != null){
					digest.setReviewCnt(Integer.parseInt(reallyCount.toString()));
					digestService.update(digest);
				}
				sender.put("reviewCount", digest.getReviewCnt());
			}
			sender.success(response);
			return;
		} catch (Exception e) {
			LogUtil.error(logger, e, "发表精品文章评论失败...");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
			return;	
		}
	}

}
