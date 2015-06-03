package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.Message;

public interface IMessageDao extends IBaseDao<Message>{

	List<Message> getMessageList(Long custId,String platformSource,String deviceType,int start,int end);
	
	void updateIsRead(Long custId,String platformSource,String deviceType);
	
	int getMessageNum(Long custId,String platformSource,String deviceType) throws Exception;
	
	public void setMessageCache(Long custId,List<Message> messageCacheList);
	
	public List<Message> getMessageCache(Long custId);
	
	public int getAllMassageNum(Long custId,String platformSource,String deviceType);
}
