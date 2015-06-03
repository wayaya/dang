package com.dangdang.digital.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IStoreUpDao;
import com.dangdang.digital.model.StoreUp;

/**
 * StoreUp DAO.
 */
@Repository
public class StoreUpDaoImpl extends BaseDaoImpl<StoreUp> implements IStoreUpDao{

	@Override
	public int getTotalCount(Map<String, Object> paramObj) {
		return (Integer) getSqlSessionQueryTemplate().selectOne("StoreUpMapper.getTotalCount",paramObj);
	}

	@Override
	public List<Long> getStoreUpObjectIdListByCustId(
			Map<String, Object> paramObj) {
		return (List<Long>) getSqlSessionQueryTemplate().selectList("StoreUpMapper.getStoreUpObjectIdListByCustId",paramObj);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getStoreUpObjectIdListByCustIdAndTargetIds(Map<String, Object> paramObj) {
		return (List<Long>) getSqlSessionQueryTemplate().selectList(
				"StoreUpMapper.getStoreUpObjectIdListByCustIdAndTargetIds", paramObj);
	}

	@Override
	public List<Long> getStoreUpDigestIdsByCustId(Map<String, Object> paramObj) {
		return (List<Long>) getSqlSessionQueryTemplate().selectList("StoreUpMapper.getStoreUpDigestIdsByCustId",paramObj);
	}

}