package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IChapterDao;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.service.IChapterService;

@Service(value = "chapterService")
public class ChapterServiceImpl extends BaseServiceImpl<Chapter, Long> implements IChapterService {

	@Resource
	private IChapterDao chapterDao;

	@Override
	public IBaseDao<Chapter> getBaseDao() {
		return chapterDao;
	}	

}
