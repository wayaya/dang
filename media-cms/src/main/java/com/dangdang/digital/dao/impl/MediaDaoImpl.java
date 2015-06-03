package com.dangdang.digital.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IMediaDao;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.vo.MediaSimpleSearchReturnVo;

@Repository(value="mediaDao")
public class MediaDaoImpl extends BaseDaoImpl<Media> implements IMediaDao {

	@Override
	public void saveCatetorys(Media media) {
		insert("MediaMapper.saveCatetorys", media);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MediaSimpleSearchReturnVo> findSimpleSearchReturnByIds(List<Integer> ids) {
		return (List<MediaSimpleSearchReturnVo>) this.getSqlSessionQueryTemplate().selectList("MediaMapper.findSimpleSearchReturnByIds", ids);
	}

	@Override
	public void setIsFull(Media media) {
		this.update("MediaMapper.setIsFull", media);
	}

	@Override
	public void toShelf(Map para) {
		this.update("MediaMapper.toShelf", para);
	
	
	

}
	

	@Override
	public PageFinder<Long> queryEpubProductList(Query query) {
		int count = (Integer) getSqlSessionQueryTemplate().selectOne("MediaMapper.getEpubProductIdCount");
		List<Long> datas = (List<Long>) getSqlSessionQueryTemplate().selectList("MediaMapper.getEpubProductIdList", null,new RowBounds(query.getOffset(), query.getPageSize()));
		PageFinder<Long> pageFinder = new PageFinder<Long>(query.getPage(),query.getPageSize(), count, datas);
		return pageFinder;
	}

	@Override
	public PageFinder<Media> queryEpubMedia(Object params, Query query) {
		int count = (Integer) getSqlSessionQueryTemplate().selectOne("MediaMapper.pageCount", params);
		List<Media> datas = (List<Media>) getSqlSessionQueryTemplate().selectList("MediaMapper.queryEpubMedia", params,new RowBounds(query.getOffset(), query.getPageSize()));
		PageFinder<Media> pageFinder = new PageFinder<Media>(query.getPage(),query.getPageSize(), count, datas);
		return pageFinder;
	}


	@Override
	public List<Map<String, Object>> statisticsByMediaIds(Date startTime,Date endTime,List<Long> mediaIds) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("mediaIds", mediaIds);
		return (List<Map<String, Object>>) this.getSqlSessionQueryTemplate().selectList(
				"MediaMapper.statisticsByMediaIds", paramMap);
	}
	
	
	@Override
	public List<Map<String, Object>> statisticsBySaleIds(Date startTime,
			Date endTime, List<Long> saleIds) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("saleIds", saleIds);
		return (List<Map<String, Object>>) this.getSqlSessionQueryTemplate().selectList(
				"MediaMapper.statisticsBySaleIds", paramMap);
	}

}
