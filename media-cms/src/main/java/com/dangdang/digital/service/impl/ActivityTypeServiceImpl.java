package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IActivityTypeDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.model.ActivityType;
import com.dangdang.digital.service.IActivityTypeService;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午2:13:56  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Service
public class ActivityTypeServiceImpl  extends BaseServiceImpl<ActivityType, Integer>  implements IActivityTypeService{

	@Resource IActivityTypeDao activityTypeDao;
	
	@Override
	public void insert(ActivityType activityType) {
		activityTypeDao.insert(activityType);
	}

	@Override
	public void delete(Integer id) {
		activityTypeDao.delete(id);
	}

	@Override
	public IBaseDao<ActivityType> getBaseDao() {
		return activityTypeDao;
	}
}