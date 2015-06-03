package com.dangdang.digital.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IBoughtDetailDao;
import com.dangdang.digital.model.BoughtDetail;
import com.dangdang.digital.service.IBoughtDetailService;

@Service
public class BoughtDetailServiceImpl extends BaseServiceImpl<BoughtDetail, Long> implements IBoughtDetailService{

	@Resource
	private IBoughtDetailDao boughtDetailDao;

	@Override
	public IBaseDao<BoughtDetail> getBaseDao() {
		return boughtDetailDao;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BoughtDetail> getBoughtDetailListByBoughtId(Long boughtId, Integer start, Integer count) {
		if (start == null) {
			start = 0;
		}
		if (count == null) {
			count = Integer.MAX_VALUE;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boughtId", boughtId);
		return (List<BoughtDetail>) boughtDetailDao.getSqlSessionQueryTemplate().selectList("BoughtDetailMapper.getAll", paramMap,
				new RowBounds(start, count));
	}
	
	@Override
	public Integer getBoughtDetailCountByBoughtId(Long boughtId){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("boughtId", boughtId);
		return boughtDetailDao.getCount(paramMap);
	}

}
