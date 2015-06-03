package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.Message;
import com.dangdang.digital.vo.ReturnMessageVo;

public interface IMessageService extends IBaseService<Message, Long>{
	
	List<Message> getMessageList(Long custId,String platformSource,String deviceType,int start,int count) throws Exception;
	
	public void updateIsRead(Long custId,String platformSource,String deviceType);
	
	int getMessageNum(Long custId,String platformSource,String deviceType) throws Exception;
    
	public int getAllMassageNum(Long custId,String platformSource,String deviceType);
}
