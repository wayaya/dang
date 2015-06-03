package com.dangdang.digital.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IDiscoveryDao;
import com.dangdang.digital.model.Discovery;
import com.dangdang.digital.service.IDiscoveryService;


@Service
public class DiscoveryServiceImpl extends BaseServiceImpl<Discovery, Long>
		implements IDiscoveryService {

	@Resource
	private IDiscoveryDao discovertyDao;
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	@Override
	public IBaseDao<Discovery> getBaseDao() {
		return discovertyDao;
	}
	
	@Override
	public int updateListDiscovery(final List<Discovery> discoveries) {
		String updateOrderSql ="update media_discovery set top_order=?,operator=? where id=?";
		jdbcTemplate.batchUpdate(updateOrderSql, new BatchPreparedStatementSetter(){
			public void setValues(PreparedStatement ps, int i) throws SQLException{
				ps.setInt(1, discoveries.get(i).getTopOrder());
				ps.setString(2, discoveries.get(i).getOperator());
				ps.setLong(3, discoveries.get(i).getId());
			}
			public int getBatchSize(){
				return discoveries.size();
			}
		});
		return discoveries.size();
	}

}
