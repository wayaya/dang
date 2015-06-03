package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.INotificationDao;
import com.dangdang.digital.model.Notification;
import com.dangdang.digital.service.INotificationService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Service("notificationService")
public class NotificationServiceImpl extends BaseServiceImpl<Notification, Long> implements INotificationService{

	@Resource
	INotificationDao notificationDao; 
	
	@Override
	public IBaseDao<Notification> getBaseDao() {
		return notificationDao;
	}
	
	private String getPrefix(){
		return NotificationServiceImpl.class.getSimpleName()+"Mapper.";
	}

	@Override
	public List<Notification> getNotificationListByCustIdsAndDeviceType( Integer appId, List<Long> usingUserIds, List<Integer> deviceTypes) {
		
		return notificationDao.getNotificationListByCustIdsAndDeviceType(appId, usingUserIds, deviceTypes);
	}

	@Override
	public PageFinder<Notification> getPageFinderObjsByMutliChannel(Object params, Query query) {
		params = convertBeanToMap(params);
		return getBaseDao().getPageFinderObjs(params, query, "NotificationMapper.pageCountByMutliChannel", "NotificationMapper.pageDataByMutliChannel");
	}
	
}
