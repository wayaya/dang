package com.dangdang.digital.dao;

import com.dangdang.digital.model.Message;

public interface IMessageInfoDao extends IBaseDao<Message> {

	void saveMessageInfo(Message messageInfo) throws Exception;

}
