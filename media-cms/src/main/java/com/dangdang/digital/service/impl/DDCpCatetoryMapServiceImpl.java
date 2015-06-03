package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IDDCpCatetoryMapDao;
import com.dangdang.digital.model.DDCpCatetoryMap;
import com.dangdang.digital.service.IDDCpCatetoryMapService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Service
public class DDCpCatetoryMapServiceImpl extends
		BaseServiceImpl<DDCpCatetoryMap, Long> implements
		IDDCpCatetoryMapService {

	@Resource
	private IDDCpCatetoryMapDao dDcpCatetoryMapDao;
	
	
	@Override
	public IBaseDao<DDCpCatetoryMap> getBaseDao() {
		return dDcpCatetoryMapDao;
	}

	@Override
	public List<DDCpCatetoryMap> findDDCpCategoryByCategoryIds(List<String> cpCategoryIds) {
		return dDcpCatetoryMapDao.findDDCpCategoryByCategoryIds(cpCategoryIds);
	}
}
