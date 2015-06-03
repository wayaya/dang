package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.CpPullChapter;

public interface ICpPullChapterDao extends IBaseDao<CpPullChapter> {
	public String getLastChapterId(String cpMediaId);
	
	public List<CpPullChapter> findByCpChapterIds(List<String> cpChapterIds);

	public void delCpPullChapterByMediaId(Long mediaId);
}
