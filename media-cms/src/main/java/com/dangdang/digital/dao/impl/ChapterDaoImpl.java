package com.dangdang.digital.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IChapterDao;
import com.dangdang.digital.model.Chapter;


@Repository
public class ChapterDaoImpl extends BaseDaoImpl<Chapter> implements IChapterDao {

	@Override
	public String getContent(Long id) {
		return (String)getSqlSessionQueryTemplate().selectOne("ChapterMapper.getContent",id);
	}

	@Override
	public void chgOrder(Chapter chapter) {
		update("ChapterMapper.chgOrder", chapter);
		update("ChapterMapper.chgOrderPrev", chapter);
	}

	@Override
	public void delChapterByMediaId(Long mediaId) {
		delete("ChapterMapper.delChapterByMediaId", mediaId);
	}
	
}
