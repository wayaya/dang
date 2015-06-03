package com.dangdang.digital.manager;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.digital.model.CartResult;
import com.dangdang.digital.utils.InterfaceManager;

/**
 * 主站购物车相关接口.
 * @author yangming
 *
 */
public abstract class CartManager {
	
	private static final String MOBILE_UPDATE_SHOPPING_CART_V2 = "new.shopping.cart.update.v2";
	private static final String MOBILE_APPEND_SHOPPING_CART_V2 = "new.shopping.cart.append.v2";
	private static final String MOBILE_GET_SHOPPING_CART_V2 = "new.shopping.cart.get.v2";
	private static final String MOBILE_DELETE_SHOPPING_CART_V2 = "new.shopping.cart.delete.v2";
	private static final String MOBILE_UNCKECK_SHOPPING_CART_V2 = "new.shopping.cart.uncheck.v2";
	private static final String MOBILE_CKECK_SHOPPING_CART_V2 = "new.shopping.cart.check.v2";
	private static final String MOBILE_CHANGED_SHOPPING_CART_V2 = "new.shopping.cart.changed.v2";
	private static final String MOBILE_BALANCE_SHOPPING_CART_V2 = "new.shopping.cart.balance.v2";
	
	
	
	private CartManager() {
	}

	private static final Logger LOG = LoggerFactory.getLogger(CartManager.class);
		
	/**
	 * 添加购物车V2.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static CartResult appendCart(final Map<String, Object> params) throws Exception {
		CartResult result = null;			
		try {	
			Map<String, String> actionMap = null;
			try {
				actionMap = InterfaceManager.getParamCapsuleOfHttpUrl(MOBILE_APPEND_SHOPPING_CART_V2, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String response = InterfaceManager.getUrlInterfaceResultWithMethod(actionMap);
			result = assembleCartResultV2(response);			
		} catch (Exception e) {
			LOG.error("添加购物车失败!", e);
			throw new RuntimeException("添加购物车失败!", e);
		}		
		return result;
	}
	
	/**
	 * 获取购物车V2.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static CartResult getCart(final Map<String, Object> params) throws Exception {
		CartResult result = null;			
		try {				
			Map<String, String> actionMap = null;
			try {
				actionMap = InterfaceManager.getParamCapsuleOfHttpUrl(MOBILE_GET_SHOPPING_CART_V2, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String response = InterfaceManager.getUrlInterfaceResultWithMethod(actionMap);
			result = assembleCartResultV2(response);			
		} catch (Exception e) {
			LOG.error("获取购物车失败!", e);
			throw new RuntimeException("获取购物车失败!", e);
		}		
		return result;
	}
	
	/**
	 * Description: 取消勾选
	 * @Version1.0 2015年5月27日 下午4:26:42 by 于楠（yunan@dangdang.com）创建
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static CartResult uncheckItem(final Map<String, Object> params) throws Exception {
		CartResult result = null;			
		try {				
			Map<String, String> actionMap = null;
			try {
				actionMap = InterfaceManager.getParamCapsuleOfHttpUrl(MOBILE_UNCKECK_SHOPPING_CART_V2, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String response = InterfaceManager.getUrlInterfaceResultWithMethod(actionMap);
			result = assembleCartResultV2(response);			
		} catch (Exception e) {
			LOG.error("获取购物车失败!", e);
			throw new RuntimeException("获取购物车失败!", e);
		}		
		return result;
	}
	
	/**
	 * Description: 勾选
	 * @Version1.0 2015年5月27日 下午4:26:42 by 于楠（yunan@dangdang.com）创建
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static CartResult checkItem(final Map<String, Object> params) throws Exception {
		CartResult result = null;			
		try {				
			Map<String, String> actionMap = null;
			try {
				actionMap = InterfaceManager.getParamCapsuleOfHttpUrl(MOBILE_CKECK_SHOPPING_CART_V2, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String response = InterfaceManager.getUrlInterfaceResultWithMethod(actionMap);
			result = assembleCartResultV2(response);			
		} catch (Exception e) {
			LOG.error("获取购物车失败!", e);
			throw new RuntimeException("获取购物车失败!", e);
		}		
		return result;
	}
	
	/**
	 * Description: 变价查询
	 * @Version1.0 2015年5月27日 下午4:26:42 by 于楠（yunan@dangdang.com）创建
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static CartResult getChangedProduct(final Map<String, Object> params) throws Exception {
		CartResult result = null;			
		try {				
			Map<String, String> actionMap = null;
			try {
				actionMap = InterfaceManager.getParamCapsuleOfHttpUrl(MOBILE_CHANGED_SHOPPING_CART_V2, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String response = InterfaceManager.getUrlInterfaceResultWithMethod(actionMap);
			result = assembleCartResultV2(response);			
		} catch (Exception e) {
			LOG.error("获取购物车失败!", e);
			throw new RuntimeException("获取购物车失败!", e);
		}		
		return result;
	}

	
	/**
	 * 更新购物车.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static CartResult updateCart(final Map<String, Object> params) throws Exception {
		CartResult result = null;			
		try {				
			Map<String, String> actionMap = null;
			try {
				actionMap = InterfaceManager.getParamCapsuleOfHttpUrl(MOBILE_UPDATE_SHOPPING_CART_V2, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String response = InterfaceManager.getUrlInterfaceResultWithMethod(actionMap);
			result = assembleCartResultV2(response);			
		} catch (Exception e) {
			LOG.error("更新购物车失败!", e);
			throw new RuntimeException("更新购物车失败!", e);
		}		
		return result;
	}
	
	/**
	 * 删除购物车V2.
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static CartResult deleteCart(final Map<String, Object> params) throws Exception {
		CartResult result = null;			
		try {	
			Map<String, String> actionMap = null;
			try {
				actionMap = InterfaceManager.getParamCapsuleOfHttpUrl(MOBILE_DELETE_SHOPPING_CART_V2, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String response = InterfaceManager.getUrlInterfaceResultWithMethod(actionMap);
			result = assembleCartResultV2(response);			
		} catch (Exception e) {
			LOG.error("删除购物车失败!", e);
			throw new RuntimeException("删除购物车失败!", e);
		}		
		return result;
	}
	
	/**
	 * 结算检查购物车
	 * @param params
	 * @return
	 */
	public static CartResult balanceCart(Map<String, Object> params) {
		CartResult result = null;			
		try {	
			Map<String, String> actionMap = null;
			try {
				actionMap = InterfaceManager.getParamCapsuleOfHttpUrl(MOBILE_BALANCE_SHOPPING_CART_V2, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String response = InterfaceManager.getUrlInterfaceResultWithMethod(actionMap);
			result = assembleCartResultV2(response);			
		} catch (Exception e) {
			LOG.error("结算检查购物车失败!", e);
			throw new RuntimeException("结算检查购物车失败!", e);
		}
		return result;
	}
	
	/**
	 * 将Json解析的Map对象中的数据封装成OperateCartResult对象.
	 * @param resultMap
	 * @return
	 */
	private static CartResult assembleCartResult(String response) {		
		JSONObject json = JSONObject.parseObject(response);
		CartResult cartResult = new CartResult();
		if (json.containsKey("statusCode")) {
			cartResult.setStatusCode(Integer.parseInt(json.remove("statusCode").toString()));
		}
		if (json.containsKey("errors")) {
			
			JSONArray jsonArray = json.getJSONArray("errors");
			Iterator<Object> iterator = jsonArray.iterator();
			while(iterator.hasNext()){
				JSONObject item = (JSONObject) iterator.next();
				cartResult.setErrorMsg(item.remove("message").toString());
			}
		}
		cartResult.setErrorCode(Integer.parseInt(json.remove("errorCode").toString()));
		cartResult.setData(json);
		return cartResult;	
	}
	
	private static CartResult assembleCartResultV2(String response) {		
		JSONObject json = JSONObject.parseObject(response);
		CartResult cartResult = new CartResult();
		if (json.containsKey("statusCode")) {
			cartResult.setStatusCode(Integer.parseInt(json.remove("statusCode").toString()));
		}
		if (json.containsKey("errors")) {
			JSONArray jsonArray = json.getJSONArray("errors");
			Iterator<Object> iterator = jsonArray.iterator();
			while(iterator.hasNext()){
				JSONObject item = (JSONObject) iterator.next();
				cartResult.setErrorMsg(item.remove("message").toString());
				cartResult.setErrorCode(Integer.parseInt(item.remove("code").toString()));
			}
		}
		if(cartResult.getErrorCode() == 0){
			cartResult.setErrorCode(Integer.parseInt(json.remove("code").toString()));
		}
		cartResult.setData(json);
		return cartResult;	
	}
	
	public static void main(String args[]){
		String response ="{\"body\":{\"all_cart_items\":[],\"calculate_result\":{\"error_code\":0},\"check_result\":{\"code\":\"PRODUCT_NOT_EXIST\"},\"error_code\":0,\"input_product_id_item\":[{\"count\":1,\"exist_count\":0,\"product_id\":1900411433,\"unit\":1,\"unit_count\":1}],\"products\":[]},\"errors\":[{\"business_status\":\"PRODUCT_NOT_EXIST\",\"code\":102,\"message\":\"该商品已下架\"}]}";
		JSONObject json = JSONObject.parseObject(response);
		if (json.containsKey("errors")) {
			JSONArray jsonArray = json.getJSONArray("errors");
			Iterator<Object> iterator = jsonArray.iterator();
			while(iterator.hasNext()){
				JSONObject item = (JSONObject) iterator.next();
				System.out.println(item.get("message"));
			}
		}
	}

}
