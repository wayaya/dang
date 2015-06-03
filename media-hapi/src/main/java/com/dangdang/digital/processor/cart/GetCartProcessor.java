package com.dangdang.digital.processor.cart;
import java.util.Iterator;
import java.util.Map;

import javacommon.util.CollectionUtils;
import javacommon.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.digital.api.ICartApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.MobileApiException;
import com.dangdang.digital.model.CartResult;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.MainSiteUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.MobileCartResultVo;
import com.dangdang.ucenter.api.service.IUcenterApi;
import com.dangdang.ucenter.model.DangdangUser;

/**
 * Description: 获取购物车接口
 * All Rights Reserved.
 * @version 1.0  2015年5月25日 上午11:56:18  by 于楠（yunan@dangdang.com）创建
 */
@Component("hapigetCartprocessor")
public class GetCartProcessor extends BaseApiProcessor {
	@Resource
	ICartApi  cartApi; 
	@Resource
	private IUcenterApi ucenterApi;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		String cartId = request.getParameter("cartId");	
		String fromPlatform = request.getParameter("fromPlatform");
		String temporaryCustId = request.getParameter("sync_customer_id");
		String token = request.getParameter("token");
		
		if(!checkParams(fromPlatform)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}

		int login = 0;
		String cust_id="";
		if(StringUtils.isNotEmpty(token)){
			//校验登录
			DangdangUser user = ucenterApi.getDangdangUserByToken(token);
			if (user == null) {
				throw MobileApiException.userValidateFailed();
			}else{
				cust_id = user.getId()+"";
				cartId = cust_id;
				login = 1;
			}
		}
		if(StringUtils.isBlank(cartId)){
			cartId = MainSiteUtils.generateCartId();
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> params = CollectionUtils.buildMap(
				"cart_id", cartId,
				"from_platform", fromPlatform,
				"login", login);
		if(StringUtils.isNotBlank(temporaryCustId)){
			params.put("sync_customer_id", temporaryCustId);
		}
		MobileCartResultVo result = cartApi.getCart(params, cust_id);
		if (!result.isSucc()) {
			sender.fail(result.getErrorCode(), "获取购物车失败！", response);
			return;
		}
		
		//筛选掉非自营纸书，取消勾选
		String item_ids = "";
		String batch_ids = "";
		JSONObject json = JSONObject.parseObject(result.getData().toString());
		if(json.containsKey("body")){
			JSONObject jsonBody = json.getJSONObject("body");
			//无促销及单品促销品列表
			if(jsonBody.containsKey("products")){
				JSONArray jsonArray = jsonBody.getJSONArray("products");
				Iterator<Object> iterator = jsonArray.iterator();
				while(iterator.hasNext()){
					JSONObject item = (JSONObject) iterator.next();
					String shopId = item.get("shop_id").toString();
					String itemId = item.get("item_id").toString();
					String batchId = item.get("batch_id").toString();
					if(!shopId.equals("0")){
						item_ids += itemId + ",";
						batch_ids += batchId + ",";
					}else{
						String productType = item.get("product_type").toString();
						if(!productType.equals("0")){
							item_ids += itemId + ",";
							batch_ids += batchId + ",";
						}
					}
				}
			}
			//集合促销列表
			if(jsonBody.containsKey("collections")){
				JSONArray jsonArray = jsonBody.getJSONArray("collections");
				Iterator<Object> iterator = jsonArray.iterator();
				while(iterator.hasNext()){
					JSONObject item = (JSONObject) iterator.next();
					//集合促销内的商品列表
					JSONArray jsonProductsArray = item.getJSONArray("product_list");
					Iterator<Object> iteratorProducts = jsonProductsArray.iterator();
					while(iteratorProducts.hasNext()){
						JSONObject itemProduct = (JSONObject) iteratorProducts.next();
						String shopId = itemProduct.get("shop_id").toString();
						String itemId = itemProduct.get("item_id").toString();
						String batchId = itemProduct.get("batch_id").toString();
						if(!shopId.equals("0")){
							item_ids += itemId + ",";
							batch_ids += batchId + ",";
						}else{
							String productType = itemProduct.get("product_type").toString();
							if(!productType.equals("0")){
								item_ids += itemId + ",";
								batch_ids += batchId + ",";
							}
						}
					}
				}
			}
		}
		if(item_ids.endsWith(",")){
			item_ids = item_ids.substring(0, item_ids.length() - 1);
		}
		if(batch_ids.endsWith(",")){
			batch_ids = batch_ids.substring(0, batch_ids.length() - 1);
		}
		//如果item_ids和batch_ids都为空，那么全部取消勾选购物车所有购买项
		if(StringUtils.isNotBlank(item_ids) || StringUtils.isNotBlank(batch_ids)){
			@SuppressWarnings("unchecked")
			Map<String, Object> paramsUncheck = CollectionUtils.buildMap(
					"cart_id", cartId,
					"from_platform", fromPlatform,
					"item_ids", item_ids,
					"batch_ids", batch_ids);
			CartResult resultUncheck = cartApi.uncheckItem(paramsUncheck);
			if (!resultUncheck.isSucc()) {
				sender.fail(resultUncheck.getErrorCode(), "取消勾选购物车失败！", response);
				return;
			}
		}
		
		result = cartApi.getCart(params, cust_id);
		if (!result.isSucc()) {
			sender.fail(result.getErrorCode(), "获取购物车失败！", response);
			return;
		}
			
		//过滤只包含纸书商品
		JSONObject jsonToFilter = JSONObject.parseObject(result.getData().toString());
		if(jsonToFilter.containsKey("body")){
			JSONObject jsonBody = jsonToFilter.getJSONObject("body");
			//无促销及单品促销品列表
			if(jsonBody.containsKey("products")){
				JSONArray jsonArray = jsonBody.getJSONArray("products");
				Iterator<Object> iterator = jsonArray.iterator();
				while(iterator.hasNext()){
					JSONObject item = (JSONObject) iterator.next();
					String shopId = item.get("shop_id").toString();
					if(!shopId.equals("0")){
						iterator.remove();
					}else{
						String productType = item.get("product_type").toString();
						if(!productType.equals("0")){
							iterator.remove();
						}
					}
				}
			}
			//集合促销列表
			if(jsonBody.containsKey("collections")){
				JSONArray jsonArray = jsonBody.getJSONArray("collections");
				Iterator<Object> iterator = jsonArray.iterator();
				while(iterator.hasNext()){
					JSONObject item = (JSONObject) iterator.next();
					//集合促销内的商品列表
					JSONArray jsonProductsArray = item.getJSONArray("product_list");
					Iterator<Object> iteratorProducts = jsonProductsArray.iterator();
					while(iteratorProducts.hasNext()){
						JSONObject itemProduct = (JSONObject) iteratorProducts.next();
						String shopId = itemProduct.get("shop_id").toString();
						if(!shopId.equals("0")){
							iterator.remove();
						}else{
							String productType = itemProduct.get("product_type").toString();
							if(!productType.equals("0")){
								iterator.remove();
							}
						}
					}
				}
			}
		}
		
		sender.put("result", jsonToFilter);
		sender.success(response);
	}
	
	public static void main(String args[]){
		String response ="{\"body\":{\"cart_id\":200000000547888,\"global_info\":{\"cart_prod_count\":1,\"pay_amount\":1.00,\"product_count\":1,\"total_favor\":0,\"total_money\":1.00},\"is_have_outlets\":false,\"products\":[{\"batch_id\":1427961835768,\"creation_date\":\"2015-04-02 16:03:55\",\"dis_price\":0,\"has_subsidy\":false,\"is_checked\":true,\"is_ebook_gift\":false,\"is_overseas\":0,\"is_pre_sale\":false,\"is_shop_vip\":false,\"is_stock_alarm\":false,\"is_vip\":false,\"item_count\":1,\"item_id\":5000000000027021,\"item_total_money\":1.00,\"live800\":\"\",\"main_product_id\":0,\"max_limit_buy\":-1,\"mobile_exclusive_price\":0,\"num_images\":1,\"original_price\":10.00,\"product_id\":1900703925,\"product_name\":\"投资项目评估(电子书)\",\"product_type\":98,\"promotion_list\":[],\"sale_price\":1.00,\"shop_id\":0,\"shop_name\":\"当当网\",\"shop_url\":\"www.dangdang.com\",\"stock\":9999,\"support_seven_days\":1,\"vip_price\":0}],\"shop_guarantees\":{},\"time_pass\":0}}";
		JSONObject json = JSONObject.parseObject(response);
			if(json.containsKey("body")){
				JSONObject jsonBody = json.getJSONObject("body");
				if(jsonBody.containsKey("products")){
					JSONArray jsonArray = jsonBody.getJSONArray("products");
					Iterator<Object> iterator = jsonArray.iterator();
					while(iterator.hasNext()){
						JSONObject item = (JSONObject) iterator.next();
						System.out.println(item.get("shop_id"));
						System.out.println(item.get("batch_id"));
						System.out.println(item.get("product_id"));
						System.out.println(item.get("product_type"));
						
				}
			}
		}
	}
}
