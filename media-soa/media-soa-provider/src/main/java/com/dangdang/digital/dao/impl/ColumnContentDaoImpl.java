package com.dangdang.digital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IColumnContentDao;
import com.dangdang.digital.model.ColumnContent;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
@Repository
public class ColumnContentDaoImpl extends BaseDaoImpl<ColumnContent> implements IColumnContentDao {

	public List<ColumnContent> getAll() {
		return this.selectList("ColumnContentMapper.getAll");
	}

	@Override
	public void insertBatch(List<ColumnContent> contents) {
		 insert("ColumnContentMapper.insertbatch", contents);
	}
	
	@Override
	public void updateBatch(List<ColumnContent> contents) {
		update("ColumnContentMapper.updateBatch", contents);
	}

	/**
	 * 查询分页（从库）
	 * 
	 * @param params
	 *            参数对象
	 * @param query
	 *            查询query
	 * @param countSql
	 *            总记录数的查询sqlid 返回结果是int类型
	 * @param dataSql
	 *            结果集的查询sqlid
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
	public PageFinder<Map<?, ?>> getSingelSales(Object params, Query query) {
			String countSql = "ColumnContentMapper.pageSingleSaleCount";
			String dataSql =  "ColumnContentMapper.getSingleSales";
     		SqlSessionTemplate sqlSessionQurey = getSqlSessionQueryTemplate();
			int count = (Integer) sqlSessionQurey.selectOne(countSql, params);
			List<Map<?, ?>> datas = (List<Map<?, ?>>) sqlSessionQurey.selectList(dataSql, params,new RowBounds(query.getOffset(), query.getPageSize()));
			PageFinder<Map<?, ?>> pageFinder = new PageFinder<Map<?, ?>>(query.getPage(),query.getPageSize(), count, datas);
			return pageFinder;
	}
  

	@Override
	public void batchAuditSaleContent(Object parameter) {
		update("ColumnContentMapper.auditByIds",parameter);
	}

	@Override
	public void batchUpdateEffectiveDate(Object parameter) {
		update("ColumnContentMapper.updateEffectiveDate",parameter);
	}

	@Override
	public void batchUpdateStatus(Object parameter) {
		update("ColumnContentMapper.updateStatus",parameter);
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> getSaleIdsByColumnCode(String columnCode){
		Map<String,Object> paramObj = new HashMap<String,Object>();
		paramObj.put("column_code", columnCode);
		return (List<Long>) getSqlSessionQueryTemplate().selectList("ColumnContentMapper.getSaleIdsByColumnCode", paramObj);
	}
}
