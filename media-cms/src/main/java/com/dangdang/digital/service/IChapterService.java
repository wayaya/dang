package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.Chapter;

public interface IChapterService extends IBaseService<Chapter, Long> {
	public String getContent(Long id);
	
	public void chgOrder(Chapter chapter);
	
	public int updateChapterBatch(List<Chapter> chapterList);
	
	public void delChapterByMediaId(Long mediaId);
}
