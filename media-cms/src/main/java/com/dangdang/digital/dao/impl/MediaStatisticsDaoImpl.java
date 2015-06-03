package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IMediaStatisticsDao;
import com.dangdang.digital.dao.impl.BaseDaoImpl;
import com.dangdang.digital.model.MediaStatistics;

/**
 * MediaStatistics DAO.
 */
@Repository
public class MediaStatisticsDaoImpl extends BaseDaoImpl<MediaStatistics> implements IMediaStatisticsDao{
	public List<MediaStatistics> getMediaShortInfo(Map<String,Object> paramObj){
		   return selectList("MediaStatistics", paramObj);
	}

}