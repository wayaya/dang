package com.dangdang.digital.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IActivityRecordDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.model.ActivityRecord;
import com.dangdang.digital.service.IActivityRecordService;

/**
 * Description:  该用户活动参与 service 实现
 * All Rights Reserved.
 * @version 1.0  2014年12月5日 下午3:04:30  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Service
public class ActivityRecordServiceImpl extends BaseServiceImpl<ActivityRecord, Long> implements IActivityRecordService{

	@Resource 
	IActivityRecordDao activityRecordDao;  

	@Override
	public IBaseDao<ActivityRecord> getBaseDao() {
		return activityRecordDao;
	}

	@Override
	public List<ActivityRecord> selectUserActivityRecord(Long custId,int activityType,int start,int end) {
		return activityRecordDao.selectUserActivityRecord(custId, activityType,start,end);
	}

	@Override
	public List<ActivityRecord> selectLatestActivityRecord(
			int activityType, int amount) {
		return activityRecordDao.selectLatestActivityRecord(activityType, amount);
	}

	@Override
	public int selectUserActivityRecordCount(Long custId, int activityType) {
		return activityRecordDao.selectUserActivityRecordCount(custId, activityType);
	}

}
