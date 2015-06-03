package com.dangdang.digital.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IRoleFunctionalityDao;
import com.dangdang.digital.model.RoleFunctionality;
/**
 * 
 * Description: 角色功能关联管理dao实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午11:20:50  by 魏嵩（weisong@dangdang.com）创建
 */

@Repository
public class RoleFunctionalityDaoImpl extends BaseDaoImpl<RoleFunctionality> implements IRoleFunctionalityDao{

	@Resource(name = "master_sqlSession")
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int insertList(List<RoleFunctionality> list) {

		return this.sqlSession.insert("RoleFunctionalityMapper.insertList", list);
	}

}
