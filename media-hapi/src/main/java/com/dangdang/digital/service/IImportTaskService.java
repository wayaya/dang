package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.ImportTask;

public interface IImportTaskService extends IBaseService<ImportTask, Long> {

	public void repeatImport(List<Long> list);
	
}
