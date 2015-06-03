package com.dangdang.digital.dao.impl;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ICustomerBehaviorRecordDao;
import com.dangdang.digital.model.CustomerBehaviorRecord;

@Repository
public class CustomerBehaviorRecordDaoImpl extends BaseDaoImpl<CustomerBehaviorRecord> implements ICustomerBehaviorRecordDao{
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
}
