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
 * Description: 获取购物车接口 修改商品的勾选状态，勾选指定的购物项id，如果item_ids和batch_ids都为空，那么全部勾选购物车所有购买项
 * All Rights Reserved.
 * @version 1.0  2015年5月25日 上午11:56:18  by 于楠（yunan@dangdang.com）创建
 */
@Component("hapicheckItemCartprocessor")
public class CheckItemCartProcessor extends BaseApiProcessor {
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
		String item_ids = request.getParameter("itemIds");
		String batch_ids = request.getParameter("batchIds");

		@SuppressWarnings("unchecked")
		Map<String, Object> paramsCheck = CollectionUtils.buildMap(
				"cart_id", cartId,
				"from_platform", fromPlatform,
				"item_ids", item_ids,
				"batch_ids", batch_ids);
		CartResult resultCheck = cartApi.checkItem(paramsCheck);
		if (!resultCheck.isSucc()) {
			sender.fail(resultCheck.getErrorCode(), "勾选购物车失败！", response);
			return;
		}
			
		sender.put("result", resultCheck.getData());
		sender.success(response);
	}
	
}
