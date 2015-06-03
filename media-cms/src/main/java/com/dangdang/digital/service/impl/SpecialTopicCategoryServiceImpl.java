package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ISpecialTopicDao;
import com.dangdang.digital.model.SpecialTopicCategory;
import com.dangdang.digital.dao.ISpecialTopicCategoryDao;
import com.dangdang.digital.service.ISpecialTopicCategoryService;
/**
 * 
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年3月4日 下午6:05:53  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Service
public class SpecialTopicCategoryServiceImpl extends BaseServiceImpl<SpecialTopicCategory, Long> implements ISpecialTopicCategoryService {

	@Resource
	ISpecialTopicCategoryDao dao;
	
	@Resource
	ISpecialTopicDao contentDao;
	
	public IBaseDao<SpecialTopicCategory> getBaseDao() {
		return dao;
	}

	@Override
	public List<SpecialTopicCategory> getAll() {
		return dao.getAll();
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int deleteById(final Long id){
		contentDao.delete("SpecialTopicMapper.deleteByType", id);
		return super.deleteById(id);
	}
	
}
