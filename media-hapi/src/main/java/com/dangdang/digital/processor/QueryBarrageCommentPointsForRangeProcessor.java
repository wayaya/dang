package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.base.comment.client.api.IBarrageCommentApi;
import com.dangdang.base.comment.client.vo.BarrageCommentApiParameterVo;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;

/**
 * 
 * Description: 获取指定范围内的弹幕点 All Rights Reserved.
 * 
 * @version 1.0 2015年1月6日 上午11:29:58 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapiqueryBarrageCommentPointsForRangeprocessor")
public class QueryBarrageCommentPointsForRangeProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryBarrageCommentPointsForRangeProcessor.class);

	@Resource
	private IBarrageCommentApi barrageCommentApi;

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

		if (!checkParams(offsetWordsStr, chapterIdStr, characterStartIndexStr, mediaIdStr)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}

		try {
			Long chapterId = Long.valueOf(chapterIdStr);
			Long mediaId = Long.valueOf(mediaIdStr);
			Long characterStartIndex = Long.valueOf(characterStartIndexStr);
			if (characterStartIndex < 0) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}

			Integer offsetWords = Integer.valueOf(offsetWordsStr);
			BarrageCommentApiParameterVo barrageCommentApiParameterVo = new BarrageCommentApiParameterVo();
			barrageCommentApiParameterVo.setOffsetWords(offsetWords);
			barrageCommentApiParameterVo.setMediaId(mediaId);
			barrageCommentApiParameterVo.setChapterId(chapterId);
			barrageCommentApiParameterVo.setCharacterStartIndex(characterStartIndex);

			List<Integer> pointList = barrageCommentApi.queryBarrageCommentPointsForRange(barrageCommentApiParameterVo);
			sender.put("pointList", pointList);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
