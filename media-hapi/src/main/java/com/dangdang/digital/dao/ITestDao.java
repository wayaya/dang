package com.dangdang.digital.dao;

import com.dangdang.digital.model.Test;

/**
 * Description: EbookDao
 * All Rights Reserved.
 * @version 1.0  2014年6月10日 上午10:52:28  by 王志伟（wangzhiwei@dangdang.com）创建
 */
public interface ITestDao{
	
	/**
	 * @param id
	 * @return
	 */
	public Test getTestById(Integer id);
}