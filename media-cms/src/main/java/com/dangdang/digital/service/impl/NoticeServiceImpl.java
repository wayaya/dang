package com.dangdang.digital.service.impl;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IColumnDao;
import com.dangdang.digital.dao.INoticeDao;
import com.dangdang.digital.model.Column;
import com.dangdang.digital.model.Notice;
import com.dangdang.digital.service.IColumnService;
import com.dangdang.digital.service.INoticeService;


/**
 * Notice Manager.
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<Notice, Long> implements INoticeService {

	@Resource
	INoticeDao dao;
	
	public IBaseDao<Notice> getBaseDao() {
		return dao;
	}
	
}
