package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ISpecialTopicCategoryDao;
import com.dangdang.digital.dao.impl.BaseDaoImpl;
import com.dangdang.digital.model.SpecialTopicCategory;
/**
 * 
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年3月4日 下午6:05:02  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Repository
public class SpecialTopicCategoryDaoImpl extends BaseDaoImpl<SpecialTopicCategory> implements ISpecialTopicCategoryDao{
	@Override
	public List<SpecialTopicCategory> getAll() {
		 return this.selectList("SpecialTopicCategoryMapper.getAll");
	}

}