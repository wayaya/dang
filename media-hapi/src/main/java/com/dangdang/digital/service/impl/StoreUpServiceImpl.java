package com.dangdang.digital.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IStoreUpDao;
import com.dangdang.digital.model.StoreUp;
import com.dangdang.digital.service.IStoreUpService;


/**
 * StoreUp Manager.
 */
@Service(value="storeUpService")
public class StoreUpServiceImpl extends BaseServiceImpl<StoreUp, Long> implements IStoreUpService {

	@Resource
	IStoreUpDao dao;
	
	public IBaseDao<StoreUp> getBaseDao() {
		return dao;
	}

	@Override
	public int getTotalCount(Long custId, String type,String platform) {
		Map<String,Object> paramObj = new HashMap<String,Object>(2);
		paramObj.put("custId", custId);
		paramObj.put("type", type);
		paramObj.put("platform", platform);
		return dao.getTotalCount(paramObj);
	}

	@Override
	public List<Long> getStoreUpObjectIdListByCustId(int offset, int count, Long custId,String type,String platform) {
		Map<String,Object> paramObj = new HashMap<String,Object>(4);
		paramObj.put("custId", custId);
		paramObj.put("type", type);
		paramObj.put("platform", platform);
		paramObj.put("limit_offset", offset);
		paramObj.put("limit_count", count);
		return dao.getStoreUpObjectIdListByCustId(paramObj);
	}
	
	@Override
	public List<Long> getStoreUpObjectIdListByCustIdAndTargetIds(Long custId, String type, String platform,
			List<Long> targetIds) {
		Map<String, Object> paramObj = new HashMap<String, Object>(4);
		paramObj.put("custId", custId);
		paramObj.put("type", type);
		paramObj.put("platform", platform);
		paramObj.put("targetIds", targetIds);
		return dao.getStoreUpObjectIdListByCustIdAndTargetIds(paramObj);
	}

	@Override
	public boolean isStoreUp(Long custId, Long targetId, String type) {
		StoreUp su = new StoreUp();
		su.setCustId(custId);
		su.setTargetId(targetId);
		su.setType(type);
		return dao.selectOne("StoreUpMapper.isStoreUp", su)==null?false:true;
	}
	
	@Override
	public List<Long> getStoreUpDigestIdsByCustId(Long custId, String type, String platform) {
		Map<String,Object> paramObj = new HashMap<String,Object>(4);
		paramObj.put("custId", custId);
		paramObj.put("type", type);
		paramObj.put("platform", platform);
		return dao.getStoreUpDigestIdsByCustId(paramObj);
	}
	
}
