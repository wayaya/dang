package com.dangdang.digital.api;

import com.dangdang.digital.model.Message;

public interface IMessageApi {
	public void saveMessageInfo(Message messageInfo) throws Exception;
}
