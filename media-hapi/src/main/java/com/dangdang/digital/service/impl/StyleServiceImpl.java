package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IStyleDao;
import com.dangdang.digital.model.Style;
import com.dangdang.digital.service.IStyleService;

@Service(value="styleService")
public class StyleServiceImpl extends BaseServiceImpl<Style, Integer> implements
		IStyleService {
	
	@Resource(name="styleDao")
	private IStyleDao styleDao;
	
	@Override
	public IBaseDao<Style> getBaseDao() {
		return this.styleDao;
	}
	
	
	
}
