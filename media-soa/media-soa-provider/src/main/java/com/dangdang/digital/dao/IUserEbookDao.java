package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.dangdang.digital.model.UserEbook;

public interface IUserEbookDao extends IBaseDao<UserEbook> {
	
	public int batchInsert(final List<UserEbook> UserEbooks) throws DataAccessException;
	
	public void saveOrUpdate(UserEbook entity);
	
	public List<String> findPids(Map map);
	
	public List<UserEbook> obtainUserEbookByOrderId(String orderId);
}
