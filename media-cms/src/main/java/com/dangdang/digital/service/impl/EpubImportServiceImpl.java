package com.dangdang.digital.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IEpubImportDao;
import com.dangdang.digital.model.EpubImport;
import com.dangdang.digital.service.IEpubImportService;

@Service(value="epubImportService")
public class EpubImportServiceImpl  extends BaseServiceImpl<EpubImport,Long>  implements IEpubImportService {
	@Resource 
	IEpubImportDao epubImportDao;
	
	@Override
	public IBaseDao<EpubImport> getBaseDao() {
		return epubImportDao;
	}
}
