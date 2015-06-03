package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICpPullChapterDao;
import com.dangdang.digital.model.CpPullChapter;
import com.dangdang.digital.service.ICpPullChapterService;

@Service
public class CpPullChapterServieImpl extends BaseServiceImpl<CpPullChapter, Long>
implements ICpPullChapterService{

	@Resource
	private ICpPullChapterDao cpPullChapterDao;
	@Override
	public IBaseDao<CpPullChapter> getBaseDao() {
		return cpPullChapterDao;
	}
	@Override
	public String getLastChapterId(String cpMediaId) {
		return cpPullChapterDao.getLastChapterId(cpMediaId);
	}
	
	@Override
	public List<CpPullChapter> findByCpChapterIds(List<String> cpChapterIds) {
		return cpPullChapterDao.findByCpChapterIds(cpChapterIds);
	}
	@Override
	public void delCpPullChapterByMediaId(Long mediaId) {
		cpPullChapterDao.delCpPullChapterByMediaId(mediaId);
	}

}
