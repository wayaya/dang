package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.INotificationApikeyDao;
import com.dangdang.digital.model.NotificationApikey;
import com.dangdang.digital.service.INotificationApikeyService;

@Service
public class NotificationApikeyServiceImpl extends BaseServiceImpl<NotificationApikey, Long> implements INotificationApikeyService{

	@Resource
	INotificationApikeyDao notificationApikeyDao; 
	
	
	@Override
	public IBaseDao<NotificationApikey> getBaseDao() {
		
		return notificationApikeyDao;
	}

}
