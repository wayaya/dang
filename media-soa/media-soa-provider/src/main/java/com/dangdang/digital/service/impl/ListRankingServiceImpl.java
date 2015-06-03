package com.dangdang.digital.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IListRankingDao;
import com.dangdang.digital.model.ListRanking;
import com.dangdang.digital.service.IListRankingService;


/**
 * ListRanking Manager.
 */
@Service
public class ListRankingServiceImpl extends BaseServiceImpl<ListRanking, Integer> implements IListRankingService {

	@Resource
	IListRankingDao dao;
	
	public IBaseDao<ListRanking> getBaseDao() {
		return dao;
	}
	
	public List<ListRanking> getListRanking(int offset,int count,String type){
		Map<String,Object> paramObj = new HashMap<String,Object>(4);
		paramObj.put("list_type", type);
		paramObj.put("limit_offset",offset);
		paramObj.put("limit_count",count);
		return 	dao.selectList("ListRankingMapper.getListRanking", paramObj);
	}
	
	public List<Long> getListRankingSaleIdList(int offset,int count,String type){
		Map<String,Object> paramObj = new HashMap<String,Object>(4);
		paramObj.put("list_type", type);
		paramObj.put("limit_offset",offset);
		paramObj.put("limit_count",count);
		return 	dao.getListRankingSaleIdList(paramObj);
	}

	@Override
	public Long getListRankingSaleTotalCount(String type) {
		Map<String,Object> paramObj = new HashMap<String,Object>(2);
		paramObj.put("list_type", type);
		return dao.getListRankingSaleCount(paramObj);
	}

	@Override
	public List<ListRanking> getListRankingBySaleIds(List<Long> saleIds) {
		return dao.selectList("ListRankingMapper.getListRankingBySaleIds", saleIds);
	}
}
