package com.dangdang.digital.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IUsercmsRoleDao;
import com.dangdang.digital.model.RoleFunctionality;
import com.dangdang.digital.model.UsercmsRole;
/**
 * 
 * Description: 用户角色关联dao实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午11:20:50  by 魏嵩（weisong@dangdang.com）创建
 */

@Repository
public class UsercmsRoleDaoImpl extends BaseDaoImpl<UsercmsRole> implements IUsercmsRoleDao {
	@Resource(name = "master_sqlSession")
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int insertList(List<UsercmsRole> list) {
		return this.sqlSession.insert("UsercmsRoleMapper.insertList", list);
	}

}
