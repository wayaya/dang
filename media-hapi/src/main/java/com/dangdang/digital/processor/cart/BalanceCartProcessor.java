package com.dangdang.digital.processor.cart;

import java.util.Map;

import javacommon.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICartApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.MainsiteException;
import com.dangdang.digital.exception.MobileApiException;
import com.dangdang.digital.model.CartResult;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.ucenter.api.service.IUcenterApi;
import com.dangdang.ucenter.model.DangdangUser;

/**
 * 结算检查购物车
 * @author maqiang
 *
 */
@Component("hapibalanceCartprocessor")
public class BalanceCartProcessor extends BaseApiProcessor {
	@Resource
	private ICartApi cartApi;
	@Resource
	private IUcenterApi ucenterApi;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		String fromPlatform = request.getParameter("fromPlatform");
		String cartId = request.getParameter("cartId");
		String token = request.getParameter("token");
		if (!checkParams(fromPlatform, cartId)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		if (StringUtils.isNotBlank(token)) {
			DangdangUser user = ucenterApi.getDangdangUserByToken(token);
			if (user == null) {
				throw MobileApiException.userValidateFailed();
			} else {
				// 登录状态传用户id
				cartId = String.valueOf(user.getId());
			}
		}
						
		Map<String, Object> params = CollectionUtils.buildMap(
				"customer_id", cartId,
				"from_platform", fromPlatform);
		CartResult result = cartApi.balanceCart(params);	
		if (!result.isSucc()) {
			throw MainsiteException.balanceCartError(result.getErrorCode(), result.getErrorMsg());
		}
		sender.success(response);
	}

}
