package com.dangdang.digital.processor;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.bookreview.api.IBookReviewApi;
import com.dangdang.bookreview.api.bean.BookReview;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.ReturnDigestBookReviewVo;
import com.dangdang.digital.vo.UserTradeBaseVo;
import com.google.common.collect.Lists;

/**
 * Description: 精品文章对应评论列表页
 * All Rights Reserved.
 * @version 1.0  2015年2月27日 下午6:38:33  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapigetDigestBookReviewListprocessor")
public class GetDigestBookReviewListProcessor extends BaseApiProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(GetDigestBookReviewListProcessor.class);
	
	@Resource
	private IBookReviewApi bookReviewApi;
	
	@Resource
	private IDigestService digestService;
	
	@Resource
	private ReturnBeanUtils returnBeanUtils;
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		String digestIdStr = request.getParameter("digestId");
		String pageIndexStr = StringUtils.isNotBlank(request.getParameter("pageIndex"))?request.getParameter("pageIndex"):"1";
		String pageSizeStr = StringUtils.isNotBlank(request.getParameter("pageSize"))?request.getParameter("pageSize"):String.valueOf(DIGEST_DEFAULT_PAGE_SIZE);
		if(!checkParams(digestIdStr)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		Long digestId = Long.valueOf(digestIdStr);
		Integer pageIndex = Integer.valueOf(pageIndexStr);
		Integer pageSize = Integer.valueOf(pageSizeStr);
		
		try {
			Digest digest = digestService.findDigestById(digestId);
			if(digest == null){
				sender.fail(ErrorCodeEnum.ERROR_CODE_22017.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22017.getErrorMessage(), response);
				return;
			}
			List<BookReview> bookReviews = bookReviewApi.querypageBookReviewBySubjectIdSubjectType(digestId, BookReview.SUBJECT_TYPE_ESSENCE, pageIndex, pageSize, BookReview.AUDIT_STATUS_PASS);
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			Long custId = null;
			if(custVo != null){
				custId = custVo.getCustId();
			}
			List<ReturnDigestBookReviewVo> digestBookReviewVoList = returnBeanUtils.getReturnDigestBookReviewVoList(bookReviews, String.valueOf(custId));
			sender.put("digestBookReviewList", digestBookReviewVoList);
			sender.put("hasNext", hasNextPage(digestBookReviewVoList, pageSize));
			//文章评论数
			List<Long> subjectIds = Lists.newArrayList(digestId);
			Map<String, Long> countMap = bookReviewApi.queryBookReviewCount(subjectIds, BookReview.SUBJECT_TYPE_ESSENCE);
			if(MapUtils.isNotEmpty(countMap)){
				Long reallyCount = countMap.get(String.valueOf(digestId));
				if(reallyCount != null && reallyCount != 0L){
					sender.put("reviewCount", Integer.parseInt(reallyCount.toString()));
				}
			}
			sender.success(response);
			return;
		} catch (Exception e) {
			LogUtil.error(logger, e, "获取精品:"+ digestIdStr +"对应的评论列表请求失败...");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
			return;
		}
	}

}
