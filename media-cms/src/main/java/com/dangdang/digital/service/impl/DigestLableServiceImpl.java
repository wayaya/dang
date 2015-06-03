package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IBookAuthorDao;
import com.dangdang.digital.dao.IDigestLableDao;
import com.dangdang.digital.model.DigestLable;
import com.dangdang.digital.service.IDigestLableService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
@Service
public class DigestLableServiceImpl extends BaseServiceImpl<DigestLable, Long> implements
		IDigestLableService {

	@Resource
	private IDigestLableDao digestLableDao;
	@Override
	public IBaseDao<DigestLable> getBaseDao() {
		// TODO Auto-generated method stub
		return digestLableDao;
	}

	

}
