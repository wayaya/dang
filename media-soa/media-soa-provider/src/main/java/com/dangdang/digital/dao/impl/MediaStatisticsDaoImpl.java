package com.dangdang.digital.dao.impl;

import java.util.HashMap;
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
@SuppressWarnings("unchecked")
public class MediaStatisticsDaoImpl extends BaseDaoImpl<MediaStatistics> implements IMediaStatisticsDao{
	public List<MediaStatistics> getMedia(Map<String,Object> paramObj){
		return this.selectList("MediaStatisticsMapper.getData",paramObj);
	}
	
	public List<Long> getSaleIdList(Map<String,Object> paramObj){
		return (List<Long>) getSqlSessionQueryTemplate().selectList("MediaStatisticsMapper.getSaleIdList",paramObj);
	
	}

	@Override
	public Long getSaleTotalCount(Map<String, Object> paramObj) {
		// TODO Auto-generated method stub
		return (Long) getSqlSessionQueryTemplate().selectOne("MediaStatisticsMapper.getSaleTotalCount",paramObj);
	}
	public List<Long> getSaleIdListByCategoryCodeAndDimension(final int limitStart,final int count,String categoryPath,String dimension ){
		Map<String,Object> paramObj = new HashMap<String,Object>();
		if(dimension.equalsIgnoreCase("update")){
			//更新榜
			dimension ="media_chapter_change_date";
		}else if(dimension.equalsIgnoreCase("comment_star")){
			dimension ="star_count,comment_count ";
		}else {
			dimension =dimension+"_count";
		}
		paramObj.put("limitStart", limitStart*count);
		paramObj.put("count", count);
		paramObj.put("categoryPath", categoryPath);
		paramObj.put("dimension", dimension);
		return (List<Long>) getSqlSessionQueryTemplate().selectList("MediaStatisticsMapper.getSaleIdListByCategoryCodeAndDimension",paramObj);
	}

	@Override
	public List<MediaStatistics> getBySaleIds(List<Long> saleId) {
		return selectList("MediaStatisticsMapper.getBySaleId", saleId);
	}

	
}