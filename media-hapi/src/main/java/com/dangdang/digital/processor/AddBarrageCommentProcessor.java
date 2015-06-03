package com.dangdang.digital.processor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.base.comment.client.api.IBarrageCommentApi;
import com.dangdang.base.comment.client.vo.BarrageCommentApiParameterVo;
import com.dangdang.base.comment.client.vo.BarrageCommentVo;
import com.dangdang.base.commons.enums.DeviceTypeEnum;
import com.dangdang.base.commons.vo.MessageVo;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.utils.DesUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 添加弹幕接口 All Rights Reserved.
 * 
 * @version 1.0 2015年1月5日 下午5:26:05 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapiaddBarrageCommentprocessor")
public class AddBarrageCommentProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddBarrageCommentProcessor.class);

	@Resource
	private IBarrageCommentApi barrageCommentApi;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Resource
	private ICacheApi cacheApi;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参：token
		String token = request.getParameter("token");
		// 入参： 章节Id
		String chapterIdStr = request.getParameter("chapterId");
		// 入参： 书籍Id
		String mediaIdStr = request.getParameter("mediaId");
		// 当前页字符起始位置
		String characterStartIndexStr = request.getParameter("characterStartIndex");
		// 当前页字符结束位置
		String characterEndIndexStr = request.getParameter("characterEndIndex");
		// 入参： 评论内容
		String content = request.getParameter("content");
		// 入参：设备类型
		String deviceType = request.getParameter("deviceType");
		// 入参：是否匿名
		String isAnonymous = request.getParameter("isAnonymous");
		// 入参：章节字数
		String totalWordsStr = request.getParameter("totalWords");
		if (!checkParams(token, chapterIdStr, characterStartIndexStr, characterEndIndexStr, content, deviceType,
				isAnonymous)) {
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
			Long chapterId = Long.valueOf(chapterIdStr);
			Long mediaId = Long.valueOf(mediaIdStr);
			Long characterStartIndex = Long.valueOf(characterStartIndexStr);
			Long characterEndIndex = Long.valueOf(characterEndIndexStr);

			ChapterCacheBasicVo chapter = cacheApi.getChapterBasicFromCache(chapterId);
			if (chapter == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}
			Long totalWords = chapter.getWordCnt();
			if (StringUtils.isNotBlank(totalWordsStr)) {
				totalWords = Long.valueOf(totalWordsStr);
			}
			if ( StringUtils.isNotBlank( deviceType) && deviceType.equals("YC_Android")){
				deviceType = DeviceTypeEnum.DEVICE_TYPE_ANDROID.getDeviceType();
			}
			if ( StringUtils.isNotBlank( deviceType) && deviceType.equals("YC_ipad")){
				deviceType = DeviceTypeEnum.DEVICE_TYPE_IPAD.getDeviceType();
				
			}
			if ( StringUtils.isNotBlank( deviceType) && deviceType.equals("YC_iphone")){
				deviceType = DeviceTypeEnum.DEVICE_TYPE_IPHONE.getDeviceType();
			}
			BarrageCommentApiParameterVo barrageCommentApiParameterVo = new BarrageCommentApiParameterVo();
			barrageCommentApiParameterVo.setMediaId(mediaId);
			barrageCommentApiParameterVo.setCustId(custVo.getCustId());
			barrageCommentApiParameterVo.setChapterId(chapterId);
			barrageCommentApiParameterVo.setCharacterStartIndex(characterStartIndex);
			barrageCommentApiParameterVo.setCharacterEndIndex(characterEndIndex);
			barrageCommentApiParameterVo.setTotalWords(totalWords);
			barrageCommentApiParameterVo.setDeviceType(deviceType);
			barrageCommentApiParameterVo.setIsAnonymous(isAnonymous);
			barrageCommentApiParameterVo.setContent(content);
			try {
				MessageVo<BarrageCommentVo> returnMessage = barrageCommentApi
						.addBarrageComment(barrageCommentApiParameterVo);
				if (!returnMessage.isResult()) {
					sender.fail(ErrorCodeEnum.ERROR_CODE_19101.getErrorCode(),
							ErrorCodeEnum.ERROR_CODE_19101.getErrorMessage() + "原因：" + returnMessage.getErrorMessage(),
							response);
					return;
				}
			} catch (Exception e) {
				LOGGER.error("添加弹幕失败", e);
				sender.fail(ErrorCodeEnum.ERROR_CODE_19101.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_19101.getErrorMessage(), response);
				return;
			}
			// 返回加密后的custId
			sender.put("custId", DesUtils.encryptCustId(custVo.getCustId()));
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
