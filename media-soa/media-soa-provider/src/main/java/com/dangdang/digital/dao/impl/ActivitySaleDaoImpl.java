package com.dangdang.digital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IActivitySaleDao;
import com.dangdang.digital.model.ActivitySale;

/**
 * Description: All Rights Reserved.
 * 
 * @version 1.0 2014年11月17日 下午2:13:00 by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Repository
public class ActivitySaleDaoImpl extends BaseDaoImpl<ActivitySale> implements IActivitySaleDao {

	@Override
	public void insert(ActivitySale ActivitySale) {
		this.insert("ActivitySaleMapper.insert", ActivitySale);
	}

	@Override
	public void update(ActivitySale ActivitySale) {
		this.update("ActivitySaleMapper.updateByPrimaryKey", ActivitySale);
	}

	@Override
	public void delete(Integer id) {
		this.delete("ActivitySaleMapper.deleteByPrimaryKey", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getHotFreeSaleIdList(int activityId) {
		return (List<Long>) getSqlSessionQueryTemplate().selectList("ActivitySaleMapper.getHotFreeSaleIdList",
				activityId);
	}

	@Override
	public String getHotFreeExpireTime(int activityId) {
		return (String) getSqlSessionQueryTemplate().selectOne("ActivitySaleMapper.getHotFreeExpireTime", activityId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivitySale> getByActivityIdInUse(Integer activityId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("activityId", activityId);
		return (List<ActivitySale>) getSqlSessionTemplate().selectList("ActivitySaleMapper.getAllInUse", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivitySale> getBySaleIdInUse(Long saleId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("saleId", saleId);
		return (List<ActivitySale>) getSqlSessionTemplate().selectList("ActivitySaleMapper.getAllInUse", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivitySale> getByActivityIdListInUse(List<Integer> activityIdList) {
		return (List<ActivitySale>) getSqlSessionTemplate().selectList("ActivitySaleMapper.getAllInUseByActivityIds",
				activityIdList);
	}
}