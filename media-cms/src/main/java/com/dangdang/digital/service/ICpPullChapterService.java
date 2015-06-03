package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.CpPullChapter;

public interface ICpPullChapterService extends IBaseService<CpPullChapter, Long> {

	public String getLastChapterId(String cpMediaId);
	
	public List<CpPullChapter> findByCpChapterIds(List<String> cpChapterIds);
	
	public void delCpPullChapterByMediaId(Long mediaId);
}
