/**
 * Description: ConsumerDepositServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:27:32  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IConsumerDepositDao;
import com.dangdang.digital.model.ConsumerDeposit;
import com.dangdang.digital.service.IConsumerDepositService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.HttpUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.vo.OrderDetailInfoVo;
import com.dangdang.digital.vo.OrderInfoVo;

/**
 * Description: 用户充值记录实体service实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年11月14日 上午10:27:32 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class ConsumerDepositServiceImpl extends
		BaseServiceImpl<ConsumerDeposit, Long> implements
		IConsumerDepositService {

	@Resource
	IConsumerDepositDao consumerDepositDao;
	private static final String ACTION_ORDER_SEARCH_BY_ORDERNO = "action.order.search.by.orderno";
	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	@Override
	public IBaseDao<ConsumerDeposit> getBaseDao() {
		return this.consumerDepositDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderInfoVo findOrderInfoVoByDepositOrderNo(String depositOrderNo) {
		OrderInfoVo orderInfoVo = null;
		StringBuffer realUrl = new StringBuffer();
		String url = ConfigPropertieUtils
				.getString(ACTION_ORDER_SEARCH_BY_ORDERNO);
		realUrl.append(url).append("?").append("order_id=")
				.append(depositOrderNo)
				.append("&param=orderdeliveryinfo,orderitem,orders");
		String orderInfoContent = HttpUtils.getContent(realUrl.toString(),
				HttpUtils.UTF8);
		LogUtil.info(logger, "查询充值记录的充值订单信息！充值订单号：{0}，订单详情：{1}",
				depositOrderNo, orderInfoContent);
		if (StringUtils.isBlank(orderInfoContent)) {
			return orderInfoVo;
		}
		Map<String, Object> mapOrderInfo = null;
		Map<String, Object> orderRelative = null;
		Map<String, Object> orders = null;
		List<Map<String, Object>> orderItemList = null;
		mapOrderInfo = (Map<String, Object>) JSON.parse(orderInfoContent);
		if (mapOrderInfo != null && mapOrderInfo.get("orderRelative") != null) {
			orderRelative = (Map<String, Object>) JSON.parse(mapOrderInfo.get(
					"orderRelative").toString());
		}
		if (orderRelative != null && orderRelative.get("orders") != null && orderRelative.get("orderItemList") != null) {
			orders = (Map<String, Object>) JSON.parse(orderRelative.get(
					"orders").toString());
			orderItemList = (List<Map<String, Object>>) JSON
					.parse(orderRelative.get("orderItemList").toString());
		}
		if (!CollectionUtils.isEmpty(orders)) {
			orderInfoVo = new OrderInfoVo();
			orderInfoVo.setCustEmail(String.valueOf(orders.get("cust_email")));
			orderInfoVo.setCustId(orders.get("cust_id") == null ? null : Long
					.valueOf(String.valueOf(orders.get("cust_id"))));
			orderInfoVo
					.setFromPlatform(orders.get("from_platform") == null ? null
							: Integer.valueOf(String.valueOf(orders
									.get("from_platform"))));
			String creationDateStr = String.valueOf(orders
					.get("order_creation_date"));
			try {
				orderInfoVo.setOrderCreationDate(StringUtils
						.isBlank(creationDateStr)
						|| creationDateStr.equals("null") ? null : DateUtils
						.parseDate(creationDateStr,
								new String[] { DATE_FORMAT_PATTERN }));
			} catch (Exception e) {
				LogUtil.error(logger, e,
						"时间格式转换失败！order_id:{0},order_creation_date:{1}",
						depositOrderNo, creationDateStr);
			}
			orderInfoVo.setOrderId(orders.get("order_id") == null ? null : Long
					.valueOf(String.valueOf(orders.get("order_id"))));
			orderInfoVo.setOrderSource(String.valueOf(orders
					.get("order_source")));
			orderInfoVo
					.setOrderStatus(orders.get("order_status") == null ? null
							: Integer.valueOf(String.valueOf(orders
									.get("order_status"))));
			orderInfoVo
					.setOrderType(orders.get("order_type") == null ? null
							: Integer.valueOf(String.valueOf(orders
									.get("order_type"))));
		}
		if (!CollectionUtils.isEmpty(orderItemList) && orderInfoVo != null) {
			for (Map<String, Object> orderItemInfo : orderItemList) {
				OrderDetailInfoVo orderDetailInfoVo = new OrderDetailInfoVo();
				orderDetailInfoVo
						.setProductId(orderItemInfo.get("product_id") == null ? null
								: Long.parseLong(String.valueOf(orderItemInfo
										.get("product_id"))));
				String barPriceStr = String.valueOf(orderItemInfo
						.get("bargin_price"));
				BigDecimal barPriceByFen = new BigDecimal(barPriceStr)
						.multiply(new BigDecimal(100));
				orderDetailInfoVo.setBarginPrice(barPriceByFen.intValue());
				String oriPriceStr = String.valueOf(orderItemInfo
						.get("original_price"));
				BigDecimal oriPriceByFen = new BigDecimal(oriPriceStr)
						.multiply(new BigDecimal(100));
				orderDetailInfoVo.setOriginalPrice(oriPriceByFen.intValue());
				orderDetailInfoVo.setOrderQuantity(orderItemInfo
						.get("order_quantity") == null ? 1 : Integer
						.valueOf(String.valueOf(orderItemInfo
								.get("order_quantity"))));
				orderDetailInfoVo.setProductName(orderItemInfo
						.get("product_name") == null
						|| StringUtils.isBlank(orderItemInfo
								.get("product_name").toString()) ? null
						: orderItemInfo.get("product_name").toString());
				orderInfoVo.getOrderDetailInfoVos().add(orderDetailInfoVo);
			}
		}
		return orderInfoVo;
	}

}
