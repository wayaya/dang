package com.dangdang.digital.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.digital.model.CartResult;
import com.dangdang.digital.model.UserEbook;
import com.dangdang.digital.service.IUserEbookService;
import com.dangdang.ebook.api.common.ProductImgUrlGenerator;

/**
 * 移动操作购物车结果.
 * 
 * @author dangdang
 * 
 */
public class MobileCartResult {

	private CartResult cartResult;

	public MobileCartResult(CartResult cartResult) {
		this.cartResult = cartResult;
	}

	public Object getData(String cust_id, IUserEbookService userEbookService, Map<String, Object> params) {
		Object data = cartResult.getData();
		JSONObject json = JSONObject.parseObject(data.toString());
		if (json.containsKey("cartinfo")) {
			try {

				Object object = json.get("cartinfo");
				Map cartInfoMap;
				if (object instanceof JSONArray) {
					/** 老的购物车json处理接口 */
					JSONArray array = (JSONArray) json.get("cartinfo");
					cartInfoMap = (Map) array.get(0);
				} else {
					/** 新的购物车json处理接口 */
					cartInfoMap = (Map) object;
				}
				if (cartInfoMap.containsKey("shop")) {
					List shopList = (List) cartInfoMap.get("shop");
					if(CollectionUtils.isNotEmpty(shopList)){
					// 由于我们这边都是自己的产品 所以商店信息就只取第一个
					JSONObject json_shop = JSONObject.parseObject(shopList.get(0).toString());
					cartInfoMap.remove("shop");
					Map shopMap = json_shop;
					cartInfoMap.putAll(shopMap);
					}
				}
				if (cartInfoMap.containsKey("products")) {
					Map productsMap = new HashMap();
					List<Map> productList = (List) cartInfoMap.get("products");
					// 处理促销和一键支付中的map中的key
//					List list_key = new ArrayList();
					for (int j = productList.size() - 1; j >= 0; j--) {
						Map productMap = productList.get(j);
						Long productId = Long.valueOf(productMap.get("product_id").toString());

						if (StringUtils.isNotEmpty(cust_id)) {
							Map<String, Object> parameter = new HashMap<String, Object>();
							parameter.put("productId", productId);
							parameter.put("custId", cust_id);
							parameter.put("state", UserEbook.STATE_LIVE);
							List listRepeat = userEbookService.getBaseDao().selectList("UserEbookMapper.getAll", parameter);
							if (null != listRepeat && listRepeat.size() > 0) {
								// 如果已购图书中存在这个书，将对应的书的key保存，方便从productsMap中删除掉。
								productList.remove(j);
								// 根据客户端的需求，此处增加重复购买将购物车数据删除功能。
								params.put("product_id", productId);
								params.remove("cust_type");
								CartManager.deleteCart(params);
								continue;
							}
						}
						productMap.put("cover", ProductImgUrlGenerator.L.getUrl(productId));
						if (productMap.containsKey("promotion_type")) {
							String promotion_type = productMap.get("promotion_type") + "";
							if ("102".equals(promotion_type)) {
								productMap.put("sale_price", productMap.get("vip_price"));
							}
						}
						productsMap.put(productId + "", productMap);
					}

					cartInfoMap.put("products", productsMap);
					if (object instanceof JSONArray) {
						json.remove("cartinfo");
						json.put("cartinfo", cartInfoMap);
					}
				} else {
					Map<String, Object> cartReturnMap = new HashMap<String, Object>();
					Map<String, Object> productsAllMap = new HashMap<String, Object>();

					Iterator<Map.Entry> iter = cartInfoMap.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = iter.next();
						Map realCartMap = (Map) JSONObject.parseObject(entry.getValue().toString());
						if (realCartMap.containsKey("products")) {
							Map productsMap = (Map) realCartMap.get("products");
							for (Object key : productsMap.keySet()) {
								Map productMap = (Map) productsMap.get(key);
								Long productId = Long.valueOf(productMap.get("product_id").toString());
								productsMap.put("cover", ProductImgUrlGenerator.L.getUrl(productId));
							}
							productsAllMap.putAll(productsMap);
						}
						cartReturnMap.putAll(realCartMap);
						cartReturnMap.put("products", productsAllMap);
					}

					json.remove("cartinfo");
					json.put("cartinfo", cartReturnMap);
				}

			} catch (Exception e) {
				return data;
			}
		}
		return json;
	}

	public int getErrorCode() {
		return cartResult.getErrorCode();
	}

	public boolean isSucc() {
		return cartResult.isSucc();
	}

	public List<String> getPidsFromData() {
		Object data = cartResult.getData();
		List<String> pids = new ArrayList<String>();
		JSONObject json = JSONObject.parseObject(data.toString());
		if (json.containsKey("cartinfo")) {
			try {
				Object object = json.get("cartinfo");
				Map cartInfoMap;
				if (object instanceof JSONArray) {
					/** 老的购物车json处理接口 */
					JSONArray array = (JSONArray) json.get("cartinfo");
					cartInfoMap = (Map) array.get(0);
				} else {
					/** 新的购物车json处理接口 */
					cartInfoMap = (Map) object;
				}
				if (cartInfoMap.containsKey("products")) {
//					Map productsMap = new HashMap();
					List<Map> productList = (List) cartInfoMap.get("products");
					for (int j = productList.size() - 1; j >= 0; j--) {
						Map productMap = productList.get(j);
						String productId = productMap.get("product_id").toString();
						pids.add(productId);
					}
				}
			} catch (Exception e) {
				return pids;
			}
		}
		return pids;
	}
}
