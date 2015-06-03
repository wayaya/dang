package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ICpPullChapterDao;
import com.dangdang.digital.model.CpPullChapter;


@Repository
public class CpPullChapterDaoImpl extends BaseDaoImpl<CpPullChapter> implements
		ICpPullChapterDao {

	@Override
	public String getLastChapterId(String cpMediaId) {
		Object obj = getSqlSessionTemplate().selectOne("CpPullChapterMapper.getLastChapterId");
		return obj == null ? null : obj.toString();
	}

	@Override
	public List<CpPullChapter> findByCpChapterIds(List<String> cpChapterIds) {
		return  (List<CpPullChapter>) this.getSqlSessionQueryTemplate().selectList("CpPullChapterMapper.selectByCpChapterIds", cpChapterIds);
	}

	@Override
	public void delCpPullChapterByMediaId(Long mediaId) {
		delete("CpPullChapterMapper.delCpPullChapterByMediaId", mediaId);
	}
	
}
