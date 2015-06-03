package com.dangdang.digital.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ISpecialTopicDao;
import com.dangdang.digital.model.SpecialTopic;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月8日 下午6:42:31  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Repository
public class SpecialTopicDaoImpl extends BaseDaoImpl<SpecialTopic> implements ISpecialTopicDao{

	@Override
	public SpecialTopic getLastValidSpecialTopic(String deviceType,
			String channelTypes) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("deviceType", deviceType);
		paramMap.put("channelTypes", channelTypes);
		return (SpecialTopic)super.getSqlSessionQueryTemplate().selectOne("SpecialTopicMapper.getLastValidSpecialTopic", paramMap);
	}

	@Override
	public Integer getHistoryValidSpecialTopicPageCount(String deviceType, String channelTypes) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceType", deviceType);
		paramMap.put("channelTypes", channelTypes);
		return (Integer) getSqlSessionQueryTemplate().selectOne("SpecialTopicMapper.getHistoryValidSpecialTopicPageCount", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpecialTopic> getHistoryValidSpecialTopicPageData(String deviceType, String channelTypes, Integer start, Integer limit) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceType", deviceType);
		paramMap.put("channelTypes", channelTypes);
		paramMap.put("start", start);
		paramMap.put("limit", limit);
		return (List<SpecialTopic>) getSqlSessionQueryTemplate().selectList("SpecialTopicMapper.getHistoryValidSpecialTopicPageData", paramMap);
	}

	@Override
	public List<SpecialTopic> getHomePageSpecialTopicList(final String deviceType,final String channelType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceType", deviceType);
		paramMap.put("channelTypes", channelType);
		return this.selectList("SpecialTopicMapper.getHomePageSpecialTopicList",paramMap);
	}

}
