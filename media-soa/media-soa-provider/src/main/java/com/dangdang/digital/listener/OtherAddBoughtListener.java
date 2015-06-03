package com.dangdang.digital.listener;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.dangdang.digital.service.IBoughtService;
import com.dangdang.digital.vo.AddBoughtMessage;

/**
 * 
 * Description: 除订单外其它情况添加已购信息
 * All Rights Reserved.
 * @version 1.0  2015年1月7日 上午11:23:32  by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class OtherAddBoughtListener {
	private static final Logger logger = Logger.getLogger(OtherAddBoughtListener.class);

	@Resource
	private IBoughtService boughtService;

	public void handleMessage(AddBoughtMessage addBoughtMessage) {
		try {
			logger.info("接收除订单外其它情况添加已购信息消息：" + addBoughtMessage.toString());
			// 添加已购信息
			boughtService.addBought(addBoughtMessage);
		} catch (Exception e) {
			logger.error("除订单外其它情况添加已购信息消息处理异常", e);
		}
	}
	

}
