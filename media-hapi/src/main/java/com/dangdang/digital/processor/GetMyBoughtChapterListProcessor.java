package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.IOrderApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.BoughtChapter;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 获取已购章节列表 All Rights Reserved.
 * 
 * @version 1.0 2014年12月29日 下午4:07:40 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetMyBoughtChapterListprocessor")
public class GetMyBoughtChapterListProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetMyBoughtChapterListProcessor.class);

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Resource
	private IOrderApi orderApi;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参： token
		String token = request.getParameter("token");
		// 入参： 开始
		String startStr = request.getParameter("start");
		// 入参： 结束
		String endStr = request.getParameter("end");
		// 入参： 已购id
		String boughtDetailIdStr = request.getParameter("boughtDetailId");

		if (!checkParams(token)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			Integer start = 0;
			Integer end = Integer.MAX_VALUE - 1;
			if (StringUtils.isNotBlank(startStr)) {
				start = Integer.valueOf(startStr);
			}
			if (StringUtils.isNotBlank(endStr)) {
				end = Integer.valueOf(endStr);
			}
			if (end < start || end < 0 || start < 0) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			int count = end - start + 1;
			// 获取token
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			// 未登录用户直接返回
			if (custVo == null || custVo.getCustId() == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			Long boughtDetailId = Long.valueOf(boughtDetailIdStr);
			List<BoughtChapter> boughtChapterList = orderApi.getBoughtChapterListByBoughtDetailId(boughtDetailId,
					start, count);
			sender.put("boughtChapterList", boughtChapterList);
			sender.put("total", orderApi.getBoughtChapterCountByBoughtDetailId(boughtDetailId));
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
