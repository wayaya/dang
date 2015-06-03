package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.DDCpCatetoryMap;

public interface IDDCpCatetoryMapDao extends IBaseDao<DDCpCatetoryMap> {

	public List<DDCpCatetoryMap> findDDCpCategoryByCategoryIds(List<String> cpCategoryIds);
}
