package com.dangdang.digital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.INotificationDao;
import com.dangdang.digital.model.Notification;

/**
 * 
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月10日 上午10:09:25  by 魏嵩（weisong@dangdang.com）创建
 */

@Repository
public class NotificationDaoImpl extends BaseDaoImpl<Notification> implements INotificationDao{

	@Override
	public List<Notification> getNotificationListByCustIdsAndDeviceType(Integer appId, List<Long> usingUserIds, List<Integer> deviceTypes) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appId", appId);
		map.put("custIds", usingUserIds);
		map.put("deviceTypes", deviceTypes);
		
		return selectList("NotificationMapper.getByCustIdsAndDeviceType", map);
	}

}
