package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IActivityInfoDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICatetoryDao;
import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.service.IActivityInfoService;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午2:13:48  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Service
public class ActivityInfoServiceImpl extends BaseServiceImpl<ActivityInfo, Integer>  implements IActivityInfoService{
	@Resource
	private IActivityInfoDao activityInfoDao;
	
	@Override
	public void insert(ActivityInfo activityInfo) {
		activityInfoDao.insert(activityInfo);
	}

	@Override
	public void delete(Integer id) {
		activityInfoDao.delete(id);
	}

	@Override
	public IBaseDao<ActivityInfo> getBaseDao() {
		return activityInfoDao;
	}
}