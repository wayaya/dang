package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.base.comment.client.api.IBarrageCommentApi;
import com.dangdang.base.comment.client.vo.BarrageCommentApiParameterVo;
import com.dangdang.base.comment.client.vo.BarrageCommentVo;
import com.dangdang.common.api.ICommonApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.ReturnBarrageCommentVo;

/**
 * 
 * Description: 获取指定范围内新增弹幕列表 All Rights Reserved.
 * 
 * @version 1.0 2015年1月6日 下午4:58:19 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapiqueryNewBarrageCommentListsForRangeprocessor")
public class QueryNewBarrageCommentListsForRangeProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryNewBarrageCommentListsForRangeProcessor.class);

	@Resource
	private IBarrageCommentApi barrageCommentApi;
	@Resource
	private ICommonApi commonApi;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {

		// 入参：偏移字数
		String offsetWordsStr = request.getParameter("offsetWords");
		// 入参： 章节Id
		String chapterIdStr = request.getParameter("chapterId");
		// 入参： 书籍Id
		String mediaIdStr = request.getParameter("mediaId");
		// 当前页字符起始位置
		String characterStartIndexStr = request.getParameter("characterStartIndex");
		// 上次获取的弹幕id
		String lastBarrageCommentIdStr = request.getParameter("lastBarrageCommentId");
		// 滑动方式 "UP" "DOWN"
		String slideWay = request.getParameter("slideWay");
		// 入参： 排序方式 ASC DESC
		String sequenceWay = request.getParameter("sequenceWay");

		if (!checkParams(offsetWordsStr, chapterIdStr, mediaIdStr, characterStartIndexStr, slideWay)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			Long chapterId = Long.valueOf(chapterIdStr);
			Long mediaId = Long.valueOf(mediaIdStr);
			Long characterStartIndex = Long.valueOf(characterStartIndexStr);
			Integer offsetWords = Integer.valueOf(offsetWordsStr);
			if (characterStartIndex < 0 || offsetWords < 0) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			Long lastBarrageCommentId = null;
			if (lastBarrageCommentIdStr != null) {
				lastBarrageCommentId = Long.valueOf(lastBarrageCommentIdStr);
			}
			BarrageCommentApiParameterVo barrageCommentApiParameterVo = new BarrageCommentApiParameterVo();
			barrageCommentApiParameterVo.setMediaId(mediaId);
			barrageCommentApiParameterVo.setChapterId(chapterId);
			barrageCommentApiParameterVo.setOffsetWords(offsetWords);
			barrageCommentApiParameterVo.setCharacterStartIndex(characterStartIndex);
			barrageCommentApiParameterVo.setLastBarrageCommentId(lastBarrageCommentId);
			barrageCommentApiParameterVo.setSlideWay(slideWay);
			if (StringUtils.isBlank(sequenceWay)) {
				sequenceWay = "DESC";
			}
			barrageCommentApiParameterVo.setSequenceWay(sequenceWay);
			List<BarrageCommentVo> barrageCommentList = barrageCommentApi
					.queryNewBarrageCommentListsForRange(barrageCommentApiParameterVo);
			if (CollectionUtils.isEmpty(barrageCommentList)) {
				sender.put("barrageCommentList", null);
				sender.success(response);
				return;
			}
			List<String> custIds = new ArrayList<String>();
			for (BarrageCommentVo barrageCommentVo : barrageCommentList) {
				custIds.add(String.valueOf(barrageCommentVo.getCustId()));
			}
			Map<String, Map<String, String>> userInfoMap = commonApi.getBatchCustInfos(custIds, 60 * 60 * 2);
			List<ReturnBarrageCommentVo> returnList = ReturnBeanUtils.getReturnBarrageCommentVoList(barrageCommentList,
					userInfoMap, null, null);
			sender.put("barrageCommentList", returnList);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
