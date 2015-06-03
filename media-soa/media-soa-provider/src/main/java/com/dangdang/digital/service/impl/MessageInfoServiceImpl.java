package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IMessageInfoDao;
import com.dangdang.digital.model.Message;
import com.dangdang.digital.service.IMessageInfoService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
@Service
public class MessageInfoServiceImpl extends BaseServiceImpl<Message, Integer> implements
		IMessageInfoService {
	@Resource
	private IMessageInfoDao messageInfoDao;
	public void saveMessageInfo(Message messageInfo) throws Exception{
		messageInfoDao.saveMessageInfo(messageInfo);
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public IBaseDao<Message> getBaseDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> findByIds(List<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByIds(List<Integer> ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByParamsObjs(Object params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByByParams(Object... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int save(Message target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int save(List<Message> listTarget) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Message target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Message> findListByParamsObjs(Object params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message findUniqueByParamsObjs(Object params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> findListByParams(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message findUniqueByParams(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageFinder<Message> findPageFinderObjs(Object params, Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message findMasterById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> findMasterByIds(List<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> findMasterListByParamsObjs(Object params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message findMasterUniqueByParamsObjs(Object params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> findMasterListByParams(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
