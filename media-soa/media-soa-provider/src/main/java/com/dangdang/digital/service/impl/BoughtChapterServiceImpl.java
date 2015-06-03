package com.dangdang.digital.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IBoughtChapterDao;
import com.dangdang.digital.model.BoughtChapter;
import com.dangdang.digital.service.IBoughtChapterService;

@Service
public class BoughtChapterServiceImpl extends BaseServiceImpl<BoughtChapter, Long> implements IBoughtChapterService {

	@Resource
	private IBoughtChapterDao boughtChapterDao;

	@Override
	public IBaseDao<BoughtChapter> getBaseDao() {
		return boughtChapterDao;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<BoughtChapter> getBoughtChapterListByBoughtDetailId(Long boughtDetailId, Integer start, Integer count) {
		if (start == null) {
			start = 0;
		}
		if (count == null) {
			count = Integer.MAX_VALUE;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boughtDetailId", boughtDetailId);
		return (List<BoughtChapter>) boughtChapterDao.getSqlSessionQueryTemplate().selectList("BoughtChapterMapper.getAllOrderByIndex", paramMap,
				new RowBounds(start, count));
	}
	
	@Override
	public Integer getBoughtChapterCountByBoughtDetailId(Long boughtDetailId){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("boughtDetailId", boughtDetailId);
		return boughtChapterDao.getCount(paramMap);
	}
}
