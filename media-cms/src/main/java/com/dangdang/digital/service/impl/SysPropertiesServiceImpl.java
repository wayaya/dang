package com.dangdang.digital.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ISysPropertiesDao;
import com.dangdang.digital.model.SysProperties;
import com.dangdang.digital.service.ISysPropertiesService;
@Service
public class SysPropertiesServiceImpl extends BaseServiceImpl<SysProperties, Integer> implements
		ISysPropertiesService {
	@Resource
	private ISysPropertiesDao dao;

	@Override
	public IBaseDao<SysProperties> getBaseDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	
	public String getValue(String key){
		SysProperties sp = dao.selectOne("SysPropertiesMapper.selectByKeyName",key);
		if(null == sp){
			return null;
		}else{
			return sp.getKeyValue();
		}
	}
	
	
}
