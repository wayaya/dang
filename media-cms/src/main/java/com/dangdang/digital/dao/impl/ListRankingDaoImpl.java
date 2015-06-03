package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IListRankingDao;
import com.dangdang.digital.dao.impl.BaseDaoImpl;
import com.dangdang.digital.model.ListRanking;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
/**
 * 
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月29日 下午2:24:30  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Repository
public class ListRankingDaoImpl extends BaseDaoImpl<ListRanking> implements IListRankingDao{

	@Override
	public int updateListRankingStatus(Map<String,Object> paramObj) {
		return update("ListRankingMapper.updateListRankingStatus", paramObj);
	}
	public static void main(String[] args){
		System.out.println(Integer.MAX_VALUE);
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
	@SuppressWarnings("unchecked")
	public PageFinder<Map<?, ?>> getSingelSales(Object params, Query query) {
				String countSql = "ListRankingMapper.pageSingleSaleCount";
				String dataSql =  "ListRankingMapper.getSingleSales";
	     		SqlSessionTemplate sqlSessionQurey = getSqlSessionQueryTemplate();
				int count = (Integer) sqlSessionQurey.selectOne(countSql, params);
				List<Map<?, ?>> datas = (List<Map<?, ?>>) sqlSessionQurey.selectList(dataSql, params,new RowBounds(query.getOffset(), query.getPageSize()));
				PageFinder<Map<?, ?>> pageFinder = new PageFinder<Map<?, ?>>(query.getPage(),query.getPageSize(), count, datas);
				return pageFinder;
	}
	@Override
	public int insertBatch(Map<String,Object> paramObj) {
		return insert("ListRankingMapper.insertBatchBySaleIds", paramObj);
	}
	@Override
	public PageFinder<ListRanking> getCategoryListRanking(Object params,
			Query query) {
		String countSql = "ListRankingMapper.categoryListRankingCount";
		String dataSql =  "ListRankingMapper.categoryListRankingPage";
 		SqlSessionTemplate sqlSessionQurey = getSqlSessionQueryTemplate();
		int count = (Integer) sqlSessionQurey.selectOne(countSql, params);
		List<ListRanking> datas = (List<ListRanking>) sqlSessionQurey.selectList(dataSql, params,new RowBounds(query.getOffset(), query.getPageSize()));
		PageFinder<ListRanking> pageFinder = new PageFinder<ListRanking>(query.getPage(),query.getPageSize(), count, datas);
		return pageFinder;
	}
	
	
}