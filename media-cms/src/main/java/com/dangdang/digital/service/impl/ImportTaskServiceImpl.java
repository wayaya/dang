package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IImportTaskDao;
import com.dangdang.digital.model.ImportTask;
import com.dangdang.digital.service.IImportTaskService;

@Service(value="importTaskService")
public class ImportTaskServiceImpl extends BaseServiceImpl<ImportTask, Long>
		implements IImportTaskService {
	
	@Resource(name="importTaskDao")
	private IImportTaskDao importTaskDao;
	
	
	@Override
	public IBaseDao<ImportTask> getBaseDao() {
		return this.importTaskDao;
	}


	@Override
	public void repeatImport(List<Long> list) {
		importTaskDao.repeatImport(list);
	}
	
}
