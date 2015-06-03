package com.dangdang.digital.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IIllWordDao;
import com.dangdang.digital.model.IllWord;
import com.dangdang.digital.service.IIllWordService;
@Service
public class IIllWordServiceImpl extends BaseServiceImpl<IllWord, Integer> implements
		IIllWordService {
	@Resource 
	IIllWordDao illWordDao;
	@Override
	public IBaseDao<IllWord> getBaseDao() {
		// TODO Auto-generated method stub
		return illWordDao;
	}
	@Override
	public List<IllWord> getAll() {
		return illWordDao.getIllWordList();
	}
}
