package com.dangdang.digital.service.impl;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IListRankingDao;
import com.dangdang.digital.model.ListRanking;
import com.dangdang.digital.service.IListRankingService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;


/**
 * ListRanking Manager.
 */
@Service
public class ListRankingServiceImpl extends BaseServiceImpl<ListRanking, Long> implements IListRankingService {

	@Resource
	IListRankingDao dao;
	
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	public IBaseDao<ListRanking> getBaseDao() {
		return dao;
	}
	
	public PageFinder<Map<?, ?>> getSingelSales(Object params, Query query){
		return dao.getSingelSales(params, query);
	}
	
	@Override
	public int updateListRanking(final List<ListRanking> listRanking) {
		String updateOrderSql ="update media_list_ranking set appoint_order=?,operator=?,operate_time=now(),last_change_date=now() where ( appoint_order is  null or appoint_order!=? ) and list_id=?";
		jdbcTemplate.batchUpdate(updateOrderSql, new BatchPreparedStatementSetter(){
			public void setValues(PreparedStatement ps, int i) throws SQLException{
				ps.setInt(1, listRanking.get(i).getAppointOrder());
				ps.setString(2, listRanking.get(i).getOperator());
				ps.setInt(3, listRanking.get(i).getAppointOrder());
				ps.setLong(4, listRanking.get(i).getListId());
			}
			public int getBatchSize(){
				return listRanking.size();
			}
		});
		return listRanking.size();
	}


	@Override
	public int updateListRankingStatus(int stats,String listIds,String modifer) {
		Map<String,Object> paramObj = new HashMap<String,Object>();
		paramObj.put("status", stats);
		paramObj.put("listIds", listIds);
		paramObj.put("modifer", modifer);
		return dao.updateListRankingStatus(paramObj);
	}

	@Override
	public int insertBatch(Map<String,Object> paramObj) {
		return dao.insertBatch(paramObj);
	}
	
	public PageFinder<ListRanking> getCategoryListRanking(Object params, Query query){
		return dao.getCategoryListRanking(params, query);
	}
}
