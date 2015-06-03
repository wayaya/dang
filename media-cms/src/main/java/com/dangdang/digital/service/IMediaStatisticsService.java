package com.dangdang.digital.service;
import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.MediaStatistics;
import com.dangdang.digital.service.IBaseService;


/**
 * MediaStatistics Manager.
 */
public interface IMediaStatisticsService extends IBaseService<MediaStatistics, Long> {
	/**
	 * 
	 * Description: 根据查询参数查询Media的基本信息
	 * 				查询条件:更新时间,分类(男女),上架时间等
	 * @Version1.0 2014年12月9日 下午4:18:49 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @return
	 */
	public List<MediaStatistics> getMediaShortInfo(Map<String,Object>paramObj);
	
	
	
	
}
