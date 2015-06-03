package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IActivitySaleDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.model.ActivitySale;
import com.dangdang.digital.service.IActivitySaleService;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午2:13:00  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Service
public class ActivitySaleServiceImpl  extends BaseServiceImpl<ActivitySale, Integer>  implements IActivitySaleService{
	@Resource
	private IActivitySaleDao activitySaleDao;
	
	@Override
	public void insert(ActivitySale activitySale) {
		activitySaleDao.insert(activitySale);
	}

	@Override
	public void delete(Integer id) {
		activitySaleDao.delete(id);
	}

	@Override
	public IBaseDao<ActivitySale> getBaseDao() {
		return activitySaleDao;
	}
	@Override
	public List<Long> getHotFreeSaleIdList(int activityId) {
		return activitySaleDao.getHotFreeSaleIdList(activityId);
	}

	@Override
	public String getHotFreeExpireTime(int activityId) {
		// TODO Auto-generated method stub
		return activitySaleDao.getHotFreeExpireTime(activityId);
	}
}