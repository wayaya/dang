package com.dangdang.digital.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IBoughtDetailDao;
import com.dangdang.digital.model.BoughtDetail;

/**
 * 
 * Description: 已购详情dao实现
 * 
 * All Rights Reserved.
 * 
 * @version 1.0 2014年12月26日 上午10:47:57 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Repository
public class BoughtDetailDaoImpl extends BaseDaoImpl<BoughtDetail> implements IBoughtDetailDao {
	

	@Override
	public Integer getCount(Map<String, Object> paramMap) {
		try {
			Object result = getSqlSessionTemplate().selectOne("BoughtDetailMapper.pageCount", paramMap);
			return (Integer) result;
		} catch (Exception e) {
			return 0;
		}
	}

}
