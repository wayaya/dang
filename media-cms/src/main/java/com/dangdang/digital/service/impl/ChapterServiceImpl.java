package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IChapterDao;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.service.IChapterService;

@Service
public class ChapterServiceImpl extends BaseServiceImpl<Chapter, Long>
		implements IChapterService {

	@Resource
	private IChapterDao chapterDao;
	
	@Override
	public IBaseDao<Chapter> getBaseDao() {
		return chapterDao;
	}

	@Override
	public String getContent(Long id) {
		return chapterDao.getContent(id);
	}

	@Override
	public void chgOrder(Chapter chapter) {
		chapterDao.chgOrder(chapter);
	}
	
	@Override
	public int updateChapterBatch(List<Chapter> chapterList){
		return getBaseDao().update("ChapterMapper.updateBatch", chapterList);
	}

	@Override
	public void delChapterByMediaId(Long mediaId) {
		chapterDao.delChapterByMediaId(mediaId);
	}
}
