package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.ImportTask;

public interface IImportTaskDao extends IBaseDao<ImportTask> {
	public void repeatImport(List<Long> list);
}
