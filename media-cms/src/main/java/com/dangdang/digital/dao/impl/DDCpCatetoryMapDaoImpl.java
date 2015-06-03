package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IDDCpCatetoryMapDao;
import com.dangdang.digital.model.DDCpCatetoryMap;

@Repository
public class DDCpCatetoryMapDaoImpl extends BaseDaoImpl<DDCpCatetoryMap>
		implements IDDCpCatetoryMapDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<DDCpCatetoryMap> findDDCpCategoryByCategoryIds(List<String> cpCategoryIds) {
		return (List<DDCpCatetoryMap>) this.getSqlSessionQueryTemplate().selectList("DDCpCatetoryMapMapper.findDDCpCategoryByCategoryIds", cpCategoryIds);
	}
}
