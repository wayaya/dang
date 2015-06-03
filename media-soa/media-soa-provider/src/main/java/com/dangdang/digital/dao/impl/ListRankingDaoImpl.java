package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IListRankingDao;
import com.dangdang.digital.model.ListRanking;
/**
 * ListRanking DAO.
 */
@Repository
public class ListRankingDaoImpl extends BaseDaoImpl<ListRanking> implements IListRankingDao{
	@SuppressWarnings("unchecked")
	public List<Long> getListRankingSaleIdList(Map<String,Object> paramObj){
		return (List<Long>) getSqlSessionQueryTemplate().selectList("ListRankingMapper.getListRankingSaleIdList", paramObj);
	}

	@Override
	public Long getListRankingSaleCount(Map<String, Object> paramObj) {
		return  (Long) getSqlSessionQueryTemplate().selectOne("ListRankingMapper.getListRankingSaleCount", paramObj);
	}
}