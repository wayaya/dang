package com.dangdang.digital.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IActivityInfoDao;
import com.dangdang.digital.model.ActivityInfo;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午2:13:48  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Repository
public class ActivityInfoDaoImpl extends BaseDaoImpl<ActivityInfo> implements IActivityInfoDao{

	@Override
	public void insert(ActivityInfo activityInfo) {
		this.insert("ActivityInfoMapper.insert",activityInfo);
	}

	@Override
	public void update(ActivityInfo activityInfo) {
		this.update("ActivityInfoMapper.updateByPrimaryKey",activityInfo);
	}

	@Override
	public void delete(Integer id) {
		this.delete("ActivityInfoMapper.deleteByPrimaryKey", id);
	}
}