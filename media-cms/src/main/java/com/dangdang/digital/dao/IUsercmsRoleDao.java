package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.UsercmsRole;

public interface IUsercmsRoleDao extends IBaseDao<UsercmsRole> {

	int insertList(List<UsercmsRole> list);
}