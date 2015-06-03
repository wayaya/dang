package com.dangdang.digital.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IDigestAnthologyDao;
import com.dangdang.digital.model.DigestAnthology;

/**
 * Description: 文集和精品dao层实现
 * All Rights Reserved.
 * @version 1.0  2015年2月4日 下午6:10:18  by 代鹏（daipeng@dangdang.com）创建
 */
@Repository("digestAnthologyDao")
public class DigestAnthologyDaoImpl extends BaseDaoImpl<DigestAnthology> implements IDigestAnthologyDao {

	@Override
	public int batchInsert(List<DigestAnthology> records) {
		return this.insert("DigestAnthologyMapper.batchInsert", map("records", records));
	}

	@Override
	public int deleteByAnthologyId(Long anthologyId) {
		return this.delete("DigestAnthologyMapper.deleteByAnthologyId", anthologyId);
	}

	@Override
	public int deleteByDigestIdsAndAnthologyId(Long anthologyId, Collection<Long> digestIds) {
		return this.delete("DigestAnthologyMapper.deleteByDigestIdsAndAnthologyId", map("anthologyId", anthologyId, "digestIds", digestIds));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long, DigestAnthology> queryDigestAnthologyByAnthologyIdAndDigestIds(Long anthologyId, Collection<Long> digestIds) {
		return (Map<Long, DigestAnthology>) this.selectMap("DigestAnthologyMapper.queryDigestAnthologyByAnthologyIdAndDigestIds", map("digestIds", digestIds, "anthologyId", anthologyId), "digestId");
	}

	@Override
	public List<DigestAnthology> queryDigestsByAnthologyId(Map<String, Object> paramMap) {
		return this.selectList("DigestAnthologyMapper.queryDigestsByAnthologyId", paramMap);
	}
	
}
