package com.dangdang.digital.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IDigestAnthologyDao;
import com.dangdang.digital.model.DigestAnthology;
import com.dangdang.digital.service.IDigestAnthologyService;

@Service("digestAnthologyService")
public class DigestAnthologyServiceImpl extends BaseServiceImpl<DigestAnthology, Long> implements IDigestAnthologyService {
	
	@Resource
	private IDigestAnthologyDao digestAnthologyDao; 
	
	@Override
	public IBaseDao<DigestAnthology> getBaseDao() {
		return digestAnthologyDao;
	}

	@Override
	public int batchInsert(List<DigestAnthology> record) {
		return digestAnthologyDao.batchInsert(record);
	}

	@Override
	public int deleteByAnthologyId(Long anthologyId) {
		return digestAnthologyDao.deleteByAnthologyId(anthologyId);
	}

	@Override
	public int deleteByDigestIdsAndAnthologyId(Long anthologyId, Collection<Long> digestIds) {
		return digestAnthologyDao.deleteByDigestIdsAndAnthologyId(anthologyId, digestIds);
	}

	@Override
	public Map<Long, DigestAnthology> queryDigestAnthologyByAnthologyIdAndDigestIds(Long anthologyId, Collection<Long> digestIds) {
		return digestAnthologyDao.queryDigestAnthologyByAnthologyIdAndDigestIds(anthologyId, digestIds);
	}

	@Override
	public List<DigestAnthology> queryDigestsByAnthologyId(Long anthologyId, Date lastDate, Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("anthologyId", anthologyId);
		paramMap.put("lastDate", lastDate);
		paramMap.put("pageSize", pageSize);
		return digestAnthologyDao.queryDigestsByAnthologyId(paramMap);
	}
	
}
