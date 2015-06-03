package com.dangdang.digital.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.INoticeTypeDao;
import com.dangdang.digital.model.NoticeType;
import com.dangdang.digital.service.INoticeTypeService;
/**
 * NoticeType Manager.
 */
@Service
public class NoticeTypeServiceImpl extends BaseServiceImpl<NoticeType, Integer> implements INoticeTypeService {

	@Resource
	INoticeTypeDao dao;
	
	public IBaseDao<NoticeType> getBaseDao() {
		return dao;
	}

	@Override
	public List<NoticeType> getAll() {
		return dao.selectList("NoticeTypeMapper.getAll");
	}
	
}
