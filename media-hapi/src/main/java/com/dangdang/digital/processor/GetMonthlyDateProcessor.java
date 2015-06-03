package com.dangdang.digital.processor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.IUserAuthorityApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.UserMonthly;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 获取用户包月截止日期 All Rights Reserved.
 * 
 * @version 1.0 2014年12月30日 下午8:59:32 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetMonthlyDateprocessor")
public class GetMonthlyDateProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetMonthlyDateProcessor.class);

	@Resource(name = "userAuthorityApi")
	IUserAuthorityApi userAuthorityApi;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参：token
		String token = request.getParameter("token");
		try {
			if (StringUtils.isBlank(token)) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			if (custVo == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			// 获取包月权限
			UserMonthly userMonthly = userAuthorityApi.findUserMonthlyNowByCustId(custVo.getCustId(), null, null);
			if (userMonthly != null && userMonthly.getMonthlyEndTime() != null) {
				sender.put("monthlyEndTime", userMonthly.getMonthlyEndTime().getTime());
			}
			sender.success(response);

		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}
}
