package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IUserEbookDao;
import com.dangdang.digital.model.UserEbook;
import com.dangdang.digital.service.IUserEbookService;

@Service
public class UserEbookServiceImpl extends BaseServiceImpl<UserEbook, Long>  implements IUserEbookService {
	@Resource
	private IUserEbookDao userEbookDao;

	@Override
	public IBaseDao<UserEbook> getBaseDao() {
		return userEbookDao;
	}
}
