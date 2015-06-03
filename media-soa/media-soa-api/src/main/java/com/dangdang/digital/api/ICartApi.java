package com.dangdang.digital.api;

import java.util.Map;

import com.dangdang.digital.model.CartResult;
import com.dangdang.digital.vo.MobileCartResultVo;


/**
 * Description: 购物车接口
 * All Rights Reserved.
 * @version 1.0  2015年5月25日 下午1:56:27  by 于楠（yunan@dangdang.com）创建
 */
public interface ICartApi {
	
	public MobileCartResultVo getCart(final Map<String, Object> params, String custId) throws Exception;
	
	/**
	 * 添加购物车
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public CartResult appendCart(Map<String, Object> params) throws Exception;
	
	/**
	 * 删除购物车
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public CartResult deleteCart(Map<String, Object> params) throws Exception;
	
	/**
	 * 结算检查购物车
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public CartResult balanceCart(Map<String, Object> params) throws Exception;
	
	/**
	 * 更新购物车
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public CartResult updateCart(Map<String, Object> params) throws Exception;
	
	public CartResult uncheckItem(Map<String, Object> params) throws Exception;
	
	public CartResult checkItem(Map<String, Object> params) throws Exception;
	
	public CartResult getChangedProduct(Map<String, Object> params) throws Exception;
}
