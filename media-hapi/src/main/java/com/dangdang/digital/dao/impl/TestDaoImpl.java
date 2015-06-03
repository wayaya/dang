package com.dangdang.digital.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ITestDao;
import com.dangdang.digital.model.Test;

/**
 * Description: EbookDao
 * All Rights Reserved.
 * @version 1.0  2014年6月10日 上午10:52:28  by 王志伟（wangzhiwei@dangdang.com）创建
 */
@Repository
public class TestDaoImpl extends BaseDaoImpl<Test> implements ITestDao{
	/**
	 * @param id
	 * @return
	 */
	public Test getTestById(Integer id){
		return (Test)this.selectMasterOne("TestMapper.selectByPrimaryKey", id);
	}
}