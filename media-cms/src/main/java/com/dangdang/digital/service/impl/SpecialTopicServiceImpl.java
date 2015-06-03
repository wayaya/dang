package com.dangdang.digital.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ISpecialTopicDao;
import com.dangdang.digital.model.SpecialTopic;
import com.dangdang.digital.service.ISpecialTopicService;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月8日 下午6:46:18  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Service
public class SpecialTopicServiceImpl extends BaseServiceImpl<SpecialTopic,Integer> implements ISpecialTopicService{

	@Resource ISpecialTopicDao specialTopicDao;
	
	@Override
	public IBaseDao<SpecialTopic> getBaseDao() {
		return specialTopicDao;
	}
}
