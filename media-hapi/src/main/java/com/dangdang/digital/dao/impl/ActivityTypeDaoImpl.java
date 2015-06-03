package com.dangdang.digital.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IActivityTypeDao;
import com.dangdang.digital.model.ActivityType;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午2:13:56  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Repository
public class ActivityTypeDaoImpl extends BaseDaoImpl<ActivityType> implements IActivityTypeDao{

	@Override
	public void insert(ActivityType ActivityType) {
		this.insert("ActivityTypeMapper.insert",ActivityType);
	}

	@Override
	public void update(ActivityType ActivityType) {
		this.update("ActivityTypeMapper.updateByPrimaryKey",ActivityType);
	}

	@Override
	public void delete(Integer id) {
		this.delete("ActivityTypeMapper.deleteByPrimaryKey", id);
	}
}