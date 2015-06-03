package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IMessageDao;
import com.dangdang.digital.model.Message;
import com.dangdang.digital.service.IMessageService;
@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, Long> implements IMessageService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);
	@Resource
	private IMessageDao messageDao;
	
	@Override
	public IBaseDao<Message> getBaseDao() {
		return messageDao;
	}
	
	@Override
	public List<Message> getMessageList(Long custId,String platformSource,String deviceType,int start,int end) throws Exception {
		List<Message> messageCacheList=new ArrayList<Message>();
		if(end<=49){
			messageCacheList=messageDao.getMessageCache(custId);
			if(messageCacheList==null||messageCacheList.size()==0){
				messageCacheList=messageDao.getMessageList(custId,platformSource,deviceType, 0,50);
				if(messageCacheList!=null && messageCacheList.size()>0){
					messageDao.setMessageCache(custId, messageCacheList);
				}
			}
			List<Message> result = new ArrayList<Message>();
			if(messageCacheList!=null && messageCacheList.size()>0&&messageCacheList.size()>start){
				if(end>messageCacheList.size()){
					end=messageCacheList.size()-1;
				}
				for(int i=start; i<=end;i++){
					result.add(messageCacheList.get(i));
				}
			}
			messageCacheList=result;
		}else{
			List<Message> messageList=messageDao.getMessageList(custId,platformSource,deviceType, start,10);
			messageCacheList=messageList;
		}
		return messageCacheList;
	}
	
	@Override
	public void updateIsRead(Long custId,String platformSource,String deviceType) {
		messageDao.updateIsRead(custId,platformSource,deviceType);
	}
	
	@Override
	public int getMessageNum(Long custId,String platformSource,String deviceType) throws Exception{
		return messageDao.getMessageNum(custId, platformSource,deviceType);
	}
	@Override
	public int getAllMassageNum(Long custId,String platformSource,String deviceType){
    	int allMessagesNum=messageDao.getAllMassageNum(custId, platformSource, deviceType);
		return allMessagesNum;
	}
}
