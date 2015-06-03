package com.dangdang.digital.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IColumnDao;
import com.dangdang.digital.model.Column;
import com.dangdang.digital.service.IColumnService;
@Service
public class ColumnServiceImpl extends BaseServiceImpl<Column, Integer> implements IColumnService {
    @Resource
    IColumnDao dao;
	@Override
	public IBaseDao<Column> getBaseDao() {
		return dao;
	}
	@Override
	public List<Column> getAll() {
		return dao.getAll();
	}
}
