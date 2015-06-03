package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IIllegalMediaDao;
import com.dangdang.digital.model.IllegalMedia;
import com.dangdang.digital.service.IIllegalMediaService;
@Service(value="illegalMediaService")
public class IllegalMediaServiceImpl  extends BaseServiceImpl<IllegalMedia, Integer> implements IIllegalMediaService {
	@Resource
	private IIllegalMediaDao dao;
	public IBaseDao<IllegalMedia> getBaseDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	
	
	
}
