package com.dangdang.digital.api.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.IMessageApi;
import com.dangdang.digital.model.Message;
import com.dangdang.digital.service.IMessageInfoService;

@Component("messageApi")
public class MessageApiImpl implements IMessageApi {
	@Resource
	IMessageInfoService messageInfoService;
	@Override
	public void saveMessageInfo(Message messageInfo) throws Exception {
		messageInfoService.saveMessageInfo(messageInfo);
	}

}
