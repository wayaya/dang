package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.MediaStatistics;


public interface IMediaStatisticsDao extends IBaseDao<MediaStatistics> {
	public List<MediaStatistics> getMediaShortInfo(Map<String,Object> paramObj);

}