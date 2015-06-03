package com.dangdang.digital.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IBoughtDao;
import com.dangdang.digital.model.Bought;

/**
 * 
 * Description: 已购信息dao实现 All Rights Reserved.
 * 
 * @version 1.0 2014年12月26日 上午10:47:23 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Repository
public class BoughtDaoImpl extends BaseDaoImpl<Bought> implements IBoughtDao {

	@Override
	public int updateIncremental(Bought bought) {
		return this.getSqlSessionTemplate().update("BoughtMapper.updateIncremental", bought);
	}

	@Override
	public Integer getCount(Map<String, Object> paramMap) {
		try {
			Object result = getSqlSessionTemplate().selectOne("BoughtMapper.pageCount", paramMap);
			return (Integer) result;
		} catch (Exception e) {
			return 0;
		}
	}

}
