package com.dangdang.digital.service.impl;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IColumnContentDao;
import com.dangdang.digital.model.ColumnContent;
import com.dangdang.digital.service.IColumnContentService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
@Service
public class ColumnContentServiceImpl extends BaseServiceImpl<ColumnContent, Integer> implements IColumnContentService {
    @Resource
    IColumnContentDao dao;
	@Override
	public IBaseDao<ColumnContent> getBaseDao() {
		return dao;
	}
	@Override
	public List<ColumnContent> getAll() {
		return dao.getAll();
	}
	@Override
	public void insertBatch(List<ColumnContent> contents) {
		dao.insertBatch(contents);
	}
	public PageFinder<Map<?, ?>> getSingelSales(Object params, Query query){
		return dao.getSingelSales(params, query);
	}
	@Override
	public void auditByIds(Object objParam) {
		dao.batchAuditSaleContent(objParam);
	}
	@Override
	public void updateStatusByIds(Object objParam) {
		dao.batchUpdateStatus(objParam);
	}
	@Override
	public void updateEffectiveDate(Object objParam) {
		dao.batchUpdateEffectiveDate(objParam);
		
	}
	@Override
	public void updateStatus(Object objParam) {
		dao.batchUpdateStatus(objParam);
	}
	
	public void updateBatch(List<ColumnContent> contents) {
		for(ColumnContent cc:contents){
			update(cc);
		}
	}
}
