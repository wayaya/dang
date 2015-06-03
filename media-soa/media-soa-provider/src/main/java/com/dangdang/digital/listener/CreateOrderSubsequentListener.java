package com.dangdang.digital.listener;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.dangdang.digital.service.IBoughtService;
import com.dangdang.digital.vo.CreateOrderVo;

/**
 * 
 * Description: 创建订单后继处理消息监听
 * All Rights Reserved.
 * @version 1.0  2015年1月4日 上午11:29:10  by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class CreateOrderSubsequentListener {
	private static final Logger logger = Logger.getLogger(CreateOrderSubsequentListener.class);

	@Resource
	private IBoughtService boughtService;

	public void handleMessage(CreateOrderVo createOrderVo) {
		try {
			logger.info("接收订单后继处理消息：" + createOrderVo.getOrderMain().getOrderNo());
			// 1、添加已购信息
			boughtService.addBought(createOrderVo);
		} catch (Exception e) {
			logger.error("订单后继处理消息异常", e);
		}
	}
	
	public void handleMessage(Object obj) {
		try {
			logger.error("接收订单后继处理消息：obj："+obj.toString());
		} catch (Exception e) {
			logger.error("接收订单后继处理消息：", e);
		}
	}

}
