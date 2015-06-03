package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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

	@Resource
	IUserBehaviorDao userBehaviorDao;

	@Override
	public IBaseDao<UserBehavior> getBaseDao() {
		return userBehaviorDao;
	}

}
