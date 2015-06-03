package com.dangdang.digital.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IStyleDao;
import com.dangdang.digital.model.Style;

@Repository(value="styleDao")
public class StyleDaoImpl extends BaseDaoImpl<Style> implements IStyleDao {
	
}
