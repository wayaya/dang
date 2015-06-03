package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.DDCpCatetoryMap;

public interface IDDCpCatetoryMapService extends
		IBaseService<DDCpCatetoryMap, Long> {

	public List<DDCpCatetoryMap> findDDCpCategoryByCategoryIds(List<String> cpCategoryIds);
}
