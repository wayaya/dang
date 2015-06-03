package com.dangdang.digital.dao.impl;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IMessageInfoDao;
import com.dangdang.digital.model.Message;

@Repository
public class MessageInfoDaoImpl extends BaseDaoImpl<Message> implements IMessageInfoDao {

	@Override
	public void saveMessageInfo(Message messageInfo) throws Exception {

		this.getSqlSessionTemplate().insert("MessageMapper.insert", messageInfo);
		
	}

}
