package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IImportTaskDao;
import com.dangdang.digital.model.ImportTask;

@Repository(value="importTaskDao")
public class ImportTaskDaoImpl extends BaseDaoImpl<ImportTask> implements
		IImportTaskDao {

	@Override
	public void repeatImport(List<Long> list) {
		update("ImportTaskMapper.repeatImport", list);
	}
	
}
