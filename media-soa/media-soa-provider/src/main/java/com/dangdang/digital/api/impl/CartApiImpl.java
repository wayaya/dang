package com.dangdang.digital.api.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICartApi;
import com.dangdang.digital.manager.CartManager;
import com.dangdang.digital.manager.MobileCartResult;
import com.dangdang.digital.model.CartResult;
import com.dangdang.digital.service.IUserEbookService;
import com.dangdang.digital.vo.MobileCartResultVo;

@Component("cartApi")
public class CartApiImpl implements ICartApi {
	
	@Resource
	private IUserEbookService userEbookService;

	@Override
	public MobileCartResultVo getCart(Map<String, Object> params, String custId) throws Exception {
		CartResult cartResult = CartManager.getCart(params);
		MobileCartResult result = new MobileCartResult(cartResult);
		MobileCartResultVo mobileCartResultVo = new MobileCartResultVo();
		mobileCartResultVo.setCartResult(cartResult);
		if(StringUtils.isNotBlank(custId))
			mobileCartResultVo.setData(result.getData(custId, userEbookService, params));
		mobileCartResultVo.setErrorCode(result.getErrorCode());
		mobileCartResultVo.setPidsFromData(result.getPidsFromData());
		mobileCartResultVo.setSucc(result.isSucc());
	    return mobileCartResultVo;
	}

	@Override
	public CartResult appendCart(Map<String, Object> params) throws Exception {
		return CartManager.appendCart(params);
	}

	@Override
	public CartResult uncheckItem(Map<String, Object> params) throws Exception {
		return CartManager.uncheckItem(params);
	}

	@Override
	public CartResult checkItem(Map<String, Object> params) throws Exception {
		return CartManager.checkItem(params);
	}

	@Override
	public CartResult getChangedProduct(Map<String, Object> params)
			throws Exception {
		return CartManager.getChangedProduct(params);
	}

	@Override
	public CartResult deleteCart(Map<String, Object> params) throws Exception {
		return CartManager.deleteCart(params);
	}

	@Override
	public CartResult updateCart(Map<String, Object> params) throws Exception {
		return CartManager.updateCart(params);
	}

	@Override
	public CartResult balanceCart(Map<String, Object> params) throws Exception {
		return CartManager.balanceCart(params);
	}
}
