package com.dangdang.digital.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IActivitySaleDao;
import com.dangdang.digital.model.ActivitySale;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午2:13:00  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Repository
public class ActivitySaleDaoImpl extends BaseDaoImpl<ActivitySale> implements IActivitySaleDao{

	@Override
	public void insert(ActivitySale ActivitySale) {
		this.insert("ActivitySaleMapper.insert",ActivitySale);
	}

	@Override
	public void update(ActivitySale ActivitySale) {
		this.update("ActivitySaleMapper.updateByPrimaryKey",ActivitySale);
	}

	@Override
	public void delete(Integer id) {
		this.delete("ActivitySaleMapper.deleteByPrimaryKey", id);
	}
}