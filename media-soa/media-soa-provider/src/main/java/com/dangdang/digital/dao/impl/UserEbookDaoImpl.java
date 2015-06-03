package com.dangdang.digital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IUserEbookDao;
import com.dangdang.digital.model.UserEbook;

/**
 * @author dangdang
 */
@Repository
public class UserEbookDaoImpl extends BaseDaoImpl<UserEbook> implements IUserEbookDao {
	
	public int batchInsert(final List<UserEbook> UserEbooks) throws DataAccessException {
	  return  insert("UserEbookMapper.insertBatch", UserEbooks);
	}
	
	public void saveOrUpdate(UserEbook entity) {
		if (entity.getId() == null) {
			insert("UserEbookMapper.insert", entity);
		} else {
			update("UserEbookMapper.update", entity);
		}
	}


	public List<String> findPids(Map map) {
		 return (List<String>)getSqlSessionTemplate().selectList("UserEbook.getProductIds", map);
	}
	
	public List<UserEbook> obtainUserEbookByOrderId(String orderId) {
    	Map filters = new HashMap<String, Object>();
		filters.put("orderId", orderId);
		return (List<UserEbook>)getSqlSessionTemplate().selectList("UserEbook.obtainUserEbookByOrderId", filters);
	}
}
