package com.dangdang.digital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IChapterDao;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.utils.LogUtil;

@Repository
public class ChapterDaoImpl extends BaseDaoImpl<Chapter> implements IChapterDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChapterDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Chapter> getAllChapterByMediaId(Long mediaId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mediaId", mediaId);
		return (List<Chapter>) getSqlSessionTemplate().selectList("ChapterMapper.getChaptersByMediaId", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getAllChapterIdsByMediaId(Long mediaId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mediaId", mediaId);
		return (List<Long>) getSqlSessionTemplate().selectList("ChapterMapper.getChapterIdsByMediaId", paramMap);
	}

	@Override
	public Integer getCountByMediaId(Long mediaId) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("mediaId", mediaId);
			Object result = getSqlSessionTemplate().selectOne("ChapterMapper.pageCount", paramMap);
			if (result == null) {
				return 0;
			}
			return (Integer) result;
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "根据图书id查询章节数量出错。");
			return 0;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Long> getAllChapterIdsByMediaId(Long mediaId, Integer start, Integer count) {
		return (List<Long>) getSqlSessionQueryTemplate().selectList("ChapterMapper.getChapterIdsByMediaId", mediaId,
				new RowBounds(start, count));
	}

}
