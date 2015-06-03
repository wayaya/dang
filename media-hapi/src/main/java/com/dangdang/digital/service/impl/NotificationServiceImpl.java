package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.INotificationDao;
import com.dangdang.digital.model.Notification;
import com.dangdang.digital.service.INotificationService;

@Service
public class NotificationServiceImpl extends BaseServiceImpl<Notification, Long> implements INotificationService{

	@Resource
	INotificationDao notificationDao; 
	
	@Override
	public IBaseDao<Notification> getBaseDao() {
		return notificationDao;
	}
	
}
