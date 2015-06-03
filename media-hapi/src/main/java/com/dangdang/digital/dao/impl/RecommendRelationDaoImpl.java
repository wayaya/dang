package com.dangdang.digital.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IRecommendRelationDao;
import com.dangdang.digital.model.RecommendRelation;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.SafeConvert;

@Repository
public class RecommendRelationDaoImpl extends BaseDaoImpl<RecommendRelation> implements IRecommendRelationDao{

	@Resource(name = "search_master_sqlSession")
	private SqlSessionTemplate sqlSession;

	@Resource(name = "search_slave_sqlSession")
	private SqlSessionTemplate sqlSessionQurey;

	@Override
	public SqlSessionTemplate getSqlSessionQueryTemplate(){
		return this.sqlSessionQurey;
	}
	
	@Override
	public SqlSessionTemplate getSqlSessionTemplate(){
		return this.sqlSession;
	}

	@Override
	public List<Long> getMediaList(RecommendRelation queryObj) {
		
		List<Long> result = (List<Long>)this.getSqlSessionQueryTemplate().selectList("RecommendRelationMapper.getMediaList", SafeConvert.convertBeanToMap(queryObj));
		if(result == null || result.size() ==0){
			result = new ArrayList<Long>();
		}
		return result;
	}

	@Override
	public List<Long> getRelatedMediaList(RecommendRelation recommendRelation,
			Query query) {
		
		List<Long> result = (List<Long>)getSqlSessionTemplate().selectList("RecommendRelationMapper.getRelatedMediaList", SafeConvert.convertBeanToMap(recommendRelation),
				 	new RowBounds(query.getOffset(), query.getPageSize()));
		
		if(result == null || result.size() ==0){
			result = new ArrayList<Long>();
		}
		return result;
	}
	
}
