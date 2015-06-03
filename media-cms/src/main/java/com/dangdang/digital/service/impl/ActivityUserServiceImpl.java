package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IActivityUserDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.service.IActivityUserService;

/**
 * Description:和活动【排行、福袋等】用户信息Service实现类 
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午2:22:34  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Service
public class ActivityUserServiceImpl extends BaseServiceImpl<ActivityUser,Long> implements IActivityUserService {

	@Resource
	IActivityUserDao activityUserDao;
	
	@Override
	public IBaseDao<ActivityUser> getBaseDao() {
		// TODO Auto-generated method stub
		return activityUserDao;
	}

}
