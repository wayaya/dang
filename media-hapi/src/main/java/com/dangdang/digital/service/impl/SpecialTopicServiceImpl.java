package com.dangdang.digital.service.impl;


import java.util.List;

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
	
	@Override
	public SpecialTopic getLastValidSpecialTopic(String deviceType,
			String channelTypes) {
		return specialTopicDao.getLastValidSpecialTopic(deviceType, channelTypes);
	}
	
	@Override
	public Integer getHistoryValidSpecialTopicPageCount(String deviceType, String channelTypes) {
		return specialTopicDao.getHistoryValidSpecialTopicPageCount(deviceType, channelTypes);
	}

	@Override
	public List<SpecialTopic> getHistoryValidSpecialTopicPageData(String deviceType, String channelTypes, Integer start, Integer end) {
		return specialTopicDao.getHistoryValidSpecialTopicPageData(deviceType, channelTypes, start, end);
	}

	@Override
	public List<SpecialTopic> getHomePageSpecialTopicList(final String deviceType,final String channelType) {
		// TODO Auto-generated method stub
		return specialTopicDao.getHomePageSpecialTopicList(deviceType,channelType);
	}
}
