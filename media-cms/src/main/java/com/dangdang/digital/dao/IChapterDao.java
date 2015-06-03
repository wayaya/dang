package com.dangdang.digital.dao;

import com.dangdang.digital.model.Chapter;

public interface IChapterDao extends IBaseDao<Chapter> {
	
	public String getContent(Long id);
	
	public void chgOrder(Chapter chapter);
	
	public void delChapterByMediaId(Long mediaId);
}
