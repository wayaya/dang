package com.dangdang.digital.service.impl;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IColumnDao;
import com.dangdang.digital.dao.IContentProviderDao;
import com.dangdang.digital.model.Column;
import com.dangdang.digital.model.ContentProvider;
import com.dangdang.digital.service.IColumnService;
import com.dangdang.digital.service.IContentProviderService;


/**
 * ContentProvider Manager.
 */
@Service
public class ContentProviderServiceImpl extends BaseServiceImpl<ContentProvider, Integer> implements IContentProviderService {

	@Resource
	IContentProviderDao dao;
	
	public IBaseDao<ContentProvider> getBaseDao() {
		return dao;
	}
	
}
