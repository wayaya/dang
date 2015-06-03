package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.RoleFunctionality;

public interface IRoleFunctionalityDao extends IBaseDao<RoleFunctionality>{

	public int insertList(List<RoleFunctionality> list);
    
}