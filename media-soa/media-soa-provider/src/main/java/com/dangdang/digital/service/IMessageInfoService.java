package com.dangdang.digital.service;

import com.dangdang.digital.model.Message;

public interface IMessageInfoService extends IBaseService<Message, Integer>{

	void saveMessageInfo(Message messageInfo) throws Exception;
}
