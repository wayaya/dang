package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IMediaDao;
import com.dangdang.digital.model.Media;

@Repository(value = "mediaDao")
public class MediaDaoImpl extends BaseDaoImpl<Media> implements IMediaDao {

	@Override
	public void saveCatetorys(Media media) {
		insert("MediaMapper.saveCatetorys", media);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Long> getAllMediasByAuthor(Map<String, Object> paramMap, Integer start, Integer count) {
		return (List<Long>) getSqlSessionQueryTemplate().selectList("MediaMapper.getAllMediasByAuthor", paramMap,
				new RowBounds(start, count));
	}

	@Override
	public Long getMediasCountByAuthor(Map<String, Object> paramMap) {
		try {
			Object result = getSqlSessionTemplate().selectOne("MediaMapper.getMediasCountByAuthor", paramMap);
			return (Long) result;
		} catch (Exception e) {
			return new Long(0);
		}
	}
}
