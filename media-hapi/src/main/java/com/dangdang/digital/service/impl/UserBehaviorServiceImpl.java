package com.dangdang.digital.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.dangdang.digital.constant.BehaviorTypeEnum;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IUserBehaviorDao;
import com.dangdang.digital.model.UserBehavior;
import com.dangdang.digital.service.IUserBehaviorService;

/**
 * 
 * Description: 用户行为 All Rights Reserved.
 * 
 * @version 1.0 2015年1月30日 上午10:08:04 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Service
public class UserBehaviorServiceImpl extends BaseServiceImpl<UserBehavior, Long> implements IUserBehaviorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserBehaviorServiceImpl.class);
	@Resource
	IUserBehaviorDao userBehaviorDao;
	@Resource
	RabbitTemplate rabbitTemplate;

	@Override
	public IBaseDao<UserBehavior> getBaseDao() {
		return userBehaviorDao;
	}

	@Override
	public void addUserBehavior(BehaviorTypeEnum behaviorType, Long mediaId, Long saleId, Integer times, Long custId,String platform) {
		UserBehavior userBehavior = new UserBehavior();
		userBehavior.setBehaviorType(behaviorType.getType());
		userBehavior.setCreateTime(new Date());
		userBehavior.setCustId(custId);
		userBehavior.setMediaId(mediaId);
		userBehavior.setSaleId(saleId);
		if (times == null) {
			userBehavior.setTimes(1);
		} else {
			userBehavior.setTimes(times);
		}
		userBehavior.setPlatform(platform);
		rabbitTemplate.convertAndSend("media.add.user.behavior", userBehavior);
		LOGGER.info("发送添加用户行为消息成功" + userBehavior.toString());
	}

}
