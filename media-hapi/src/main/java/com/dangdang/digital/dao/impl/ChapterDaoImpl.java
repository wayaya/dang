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

	@Override
	public Integer getMaxIndexOrderByMediaId(Long mediaId) {
		try {
			Object result = getSqlSessionTemplate().selectOne("ChapterMapper.getMaxIndexOrderByMediaId", mediaId);
			return (Integer) result;
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "根据mediaId查询最大章节序号出错。");
			return 0;
		}
	}

	@Override
	public Integer getCountByMediaId(Map<String, Object> paramMap) {
		try {
			Object result = getSqlSessionTemplate().selectOne("ChapterMapper.pageCount", paramMap);
			return (Integer) result;
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "根据图书id查询章节数量出错。");
			return 0;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Chapter> getAllChapterByMediaId(Map<String, Object> paramMap, Integer start, Integer count) {
		return (List<Chapter>) getSqlSessionQueryTemplate().selectList("ChapterMapper.getChaptersByMediaId",
				paramMap, new RowBounds(start, count));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getMaxIndexOrderByMediaIds(List<Long> mediaIds) {
		Map<String, List<Long>> map = new HashMap<String, List<Long>>();
		map.put("mediaIds", mediaIds);
		return (List<Map<String, Object>>) this.getSqlSessionQueryTemplate().selectList(
				"ChapterMapper.getMaxIndexOrderByMediaIds", map);
	}

	
}
