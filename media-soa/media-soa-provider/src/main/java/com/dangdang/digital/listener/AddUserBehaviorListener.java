package com.dangdang.digital.listener;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.dangdang.digital.model.UserBehavior;
import com.dangdang.digital.service.IUserBehaviorService;

/**
 * 
 * Description: 用户行为信息监听 All Rights Reserved.
 * 
 * @version 1.0 2015年1月30日 下午12:26:15 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class AddUserBehaviorListener {
	private static final Logger logger = Logger.getLogger(AddUserBehaviorListener.class);

	@Resource
	private IUserBehaviorService userBehaviorService;

	public void handleMessage(UserBehavior userBehavior) {
		try {
			logger.info("接收添加用户行为消息：" + userBehavior.toString());
			// 保存用户行为信息
			userBehaviorService.save(userBehavior);
			logger.info("保存用户行为信息成功：" + userBehavior.toString());
		} catch (Exception e) {
			logger.error("保存用户行为信息异常", e);
		}
	}

}
