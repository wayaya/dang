package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.base.comment.client.api.IBarrageCommentApi;
import com.dangdang.base.comment.client.vo.BarrageCommentVo;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.ReturnBarrageCommentVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 查询用户的弹幕评论 All Rights Reserved.
 * 
 * @version 1.0 2015年1月6日 下午4:58:19 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapiqueryCustBarrageCommentListsprocessor")
public class QueryCustBarrageCommentListsProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryCustBarrageCommentListsProcessor.class);

	@Resource
	private IBarrageCommentApi barrageCommentApi;
	@Resource
	private ICacheApi cacheApi;
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {

		// 入参：token
		String token = request.getParameter("token");
		// 入参： 书籍Id
		String mediaIdStr = request.getParameter("mediaId");
		// 上次获取的弹幕id
		String lastBarrageCommentIdStr = request.getParameter("lastBarrageCommentId");
		// 滑动方式 "UP" "DOWN"
		String slideWay = request.getParameter("slideWay");
		// 入参： 排序方式 ASC DESC
		String sequenceWay = request.getParameter("sequenceWay");

		if (!checkParams(token, slideWay)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			// 获取token
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			// 未登录用户直接返回
			if (custVo == null || custVo.getCustId() == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			Long mediaId = null;
			if (mediaIdStr != null) {
				mediaId = Long.valueOf(mediaIdStr);
			}
			Long lastBarrageCommentId = null;
			if (StringUtils.isNotBlank(lastBarrageCommentIdStr)) {
				lastBarrageCommentId = Long.valueOf(lastBarrageCommentIdStr);
			}
			if (StringUtils.isBlank(sequenceWay)) {
				sequenceWay = "DESC";
			}

			List<BarrageCommentVo> barrageCommentList = barrageCommentApi.queryCustBarrageCommentLists(
					custVo.getCustId(), mediaId, lastBarrageCommentId, sequenceWay, slideWay);

			if (CollectionUtils.isEmpty(barrageCommentList)) {
				sender.put("barrageCommentList", null);
				sender.success(response);
				return;
			}
			List<String> custIds = new ArrayList<String>();
			List<Long> mediaIds = new ArrayList<Long>();
			List<Long> chapterIds = new ArrayList<Long>();
			for (BarrageCommentVo barrageCommentVo : barrageCommentList) {
				custIds.add(String.valueOf(barrageCommentVo.getCustId()));
				if (!mediaIds.equals(barrageCommentVo.getMediaId())) {
					mediaIds.add(barrageCommentVo.getMediaId());
				}
				if (!chapterIds.equals(barrageCommentVo.getChapterId())) {
					chapterIds.add(barrageCommentVo.getChapterId());
				}
			}
			List<MediaCacheBasicVo> mediaList = cacheApi.batchGetMediaBasicFromCache(mediaIds);
			Map<Long, MediaCacheBasicVo> mediaMap = new HashMap<Long, MediaCacheBasicVo>();
			for (MediaCacheBasicVo media : mediaList) {
				mediaMap.put(media.getMediaId(), media);
			}
			List<ChapterCacheBasicVo> chapterList = cacheApi.batchGetChapterBasicFromCache(chapterIds);
			Map<Long, ChapterCacheBasicVo> chapterMap = new HashMap<Long, ChapterCacheBasicVo>();
			for (ChapterCacheBasicVo chapter : chapterList) {
				chapterMap.put(chapter.getId(), chapter);
			}

			List<ReturnBarrageCommentVo> returnList = ReturnBeanUtils.getReturnBarrageCommentVoList(barrageCommentList,
					null, mediaMap, chapterMap);
			sender.put("barrageCommentList", returnList);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
