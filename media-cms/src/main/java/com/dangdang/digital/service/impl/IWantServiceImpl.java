package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IIWantDao;
import com.dangdang.digital.model.IWant;
import com.dangdang.digital.service.IIWantService;

/**
 * 
 * Description: 我想要service All Rights Reserved.
 * 
 * @version 1.0 2015年1月28日 下午8:50:29 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Service
public class IWantServiceImpl extends BaseServiceImpl<IWant, Long> implements IIWantService {

	@Resource
	IIWantDao dao;

	@Override
	public IBaseDao<IWant> getBaseDao() {

		return dao;
	}

}
