package com.dangdang.digital.dao.impl;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ISearchAssociateDao;
import com.dangdang.digital.model.SearchAssociate;

@Repository
public class SearchAssociateDaoImpl extends BaseDaoImpl<SearchAssociate> implements ISearchAssociateDao {
	
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
	public void insert(SearchAssociate searchAssociate) {
		this.getSqlSessionTemplate().insert("SearchAssociateMapper.insert",searchAssociate);
	}

	@Override
	public void update(SearchAssociate searchAssociate) {
		this.getSqlSessionTemplate().update("SearchAssociateMapper.updateByPrimaryKey",searchAssociate);
	}
}
