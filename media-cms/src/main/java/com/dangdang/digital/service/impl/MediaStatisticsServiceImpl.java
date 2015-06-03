package com.dangdang.digital.service.impl;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IMediaStatisticsDao;
import com.dangdang.digital.model.MediaStatistics;
import com.dangdang.digital.service.IMediaStatisticsService;


/**
 * MediaStatistics Manager.
 */
@Service
public class MediaStatisticsServiceImpl extends BaseServiceImpl<MediaStatistics, Long> implements IMediaStatisticsService {

	@Resource
	IMediaStatisticsDao dao;
	
	public IBaseDao<MediaStatistics> getBaseDao() {
		return dao;
	}
	
	public List<MediaStatistics> getMediaShortInfo(Map<String,Object> paramObj){
		return dao.getMediaShortInfo(paramObj);
	}
	
}
