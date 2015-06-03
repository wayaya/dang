package com.dangdang.digital.processor.cart;
import java.util.Map;

import javacommon.util.CollectionUtils;
import javacommon.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICartApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.MobileApiException;
import com.dangdang.digital.model.CartResult;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.ucenter.api.service.IUcenterApi;
import com.dangdang.ucenter.model.DangdangUser;

/**
 * Description: 变价查询购物车接口 获取购物车中价格和库存有变化的品
 * All Rights Reserved.
 * @version 1.0  2015年5月25日 上午11:56:18  by 于楠（yunan@dangdang.com）创建
 */
@Component("hapigetChangedProductprocessor")
public class GetChangedProductProcessor extends BaseApiProcessor {
	@Resource
	ICartApi  cartApi; 
	@Resource
	private IUcenterApi ucenterApi;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		
		String cartId = request.getParameter("cartId");	
		String fromPlatform = request.getParameter("fromPlatform");
		if(!checkParams(cartId, fromPlatform)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		String token = request.getParameter("token");
		String cust_id="";
		if(StringUtils.isNotEmpty(token)){
			DangdangUser user = ucenterApi.getDangdangUserByToken(token);
			if (user == null) {
				throw MobileApiException.userValidateFailed();
			}else{
				cust_id = user.getId()+"";
				cartId = cust_id;
			}
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> params = CollectionUtils.buildMap(
				"cart_id", cartId,
				"from_platform", fromPlatform);
		CartResult result = cartApi.getChangedProduct(params);
		if (!result.isSucc()) {
			sender.fail(result.getErrorCode(), "获取购物车变价查询信息失败！", response);
			return;
		}

		sender.put("result", result.getData());
		sender.success(response);
	}
	
}
