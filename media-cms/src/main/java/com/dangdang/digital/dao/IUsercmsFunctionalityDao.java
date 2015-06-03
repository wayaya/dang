package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.UsercmsFunctionality;

public interface IUsercmsFunctionalityDao extends IBaseDao<UsercmsFunctionality> {

	int insertList(List<UsercmsFunctionality> list);
}